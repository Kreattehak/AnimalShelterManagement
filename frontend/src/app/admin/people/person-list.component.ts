import {Component, OnDestroy, OnInit} from '@angular/core';
import {Person} from './person';
import {Subject} from 'rxjs/Subject';
import {PersonService} from './person.service';
import {Router} from '@angular/router';
import {AnimalService} from '../../shared/animal.service';

@Component({
  templateUrl: './person-list.component.html'
})
export class PersonListComponent implements OnInit, OnDestroy {

  public animalsCount: number[];
  public filteredPeople: Person[];
  public activePerson: Person;
  public errorMessage = 'Data is being resolved';

  private _filter: string;
  private people: Person[];
  private ngUnsubscribe: Subject<any> = new Subject();

  constructor(private _personService: PersonService, private _animalService: AnimalService,
              private _router: Router) {
  }

  ngOnInit() {
    this.generateTable();
  }

  get filter(): string {
    return this._filter;
  }

  set filter(value: string) {
    this._filter = value;
    this.filteredPeople = this.filter ? this.performFilter(this.filter) : this.people;
  }

  private performFilter(filter: string): Person[] {
    filter = filter.toLocaleLowerCase();
    const filterBy: string[] = filter.split(/\s/);
    let filteredPersons: Person[] = [];

    if (filterBy.length === 2) {
      filteredPersons = this.filterFirstAndLastNameSeparately(filterBy);
    } else if (filterBy.length === 1) {
      filteredPersons = this.filterFirstAndLastNameSimultaneously(filter);
    }
    if (filteredPersons.length === 0) {
      this.errorMessage = 'Data not found';
      return null;
    } else {
      return filteredPersons;
    }
  }

  markAsActive(activeRow: Person): void {
    if (this.activePerson === activeRow) {
      this.activePerson = null;
    } else {
      this.activePerson = activeRow;
    }
  }

  onInfo(): boolean {
    if (!this.isFieldSelected()) {
      return false;
    } else {
      this._router.navigate(['/admin', 'people', this.activePerson.id, 'details']);
      return true;
    }
  }

  onEditPerson(): boolean {
    if (!this.isFieldSelected()) {
      return false;
    } else {
      this._router.navigate(['/admin', 'people', this.activePerson.id]);
      return true;
    }
  }

  onRemove(): boolean {
    if (!this.isFieldSelected()) {
      return false;
    } else {
      this.removeConfirm();
    }
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  private filterFirstAndLastNameSeparately(filterBy: string[]): Person[] {
    return this.people.filter((person: Person) => {
      return person.firstName.toLocaleLowerCase().indexOf(filterBy[0]) !== -1
        && person.lastName.toLocaleLowerCase().indexOf(filterBy[1]) !== -1;
    });
  }

  private filterFirstAndLastNameSimultaneously(filter: string): Person[] {
    return this.people.filter((person: Person) => {
      return person.firstName.toLocaleLowerCase().indexOf(filter) !== -1
        || person.lastName.toLocaleLowerCase().indexOf(filter) !== -1;
    });
  }

  private removeConfirm(): void {
    prompt('Confirm deletion');
  }

  private removePerson = (result) => {
    // to be implemented
  }

  private generateTable(): void {
    this._personService.getAllPeople()
      .takeUntil(this.ngUnsubscribe)
      .subscribe(
        persons => {
          this.people = persons;
          this.filteredPeople = this.people;
          this.checkArrayForPeople();
        }, error => this.errorMessage = 'Server offline'
      );
    this._animalService.getAnimalsCountForPeople()
      .takeUntil(this.ngUnsubscribe)
      .subscribe(
        response => this.animalsCount = response,
        error => console.log('Error! Animals count not returned!' + error));
  }

  private checkArrayForPeople(): void {
    if (this.people.length === 0) {
      this.errorMessage = 'empty database';
    }
  }

  private isFieldSelected(): boolean {
    if (!this.activePerson) {
      alert('Row not selected');
      return false;
    }
    return true;
  }
}
