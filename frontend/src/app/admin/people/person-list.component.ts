import {Component, OnDestroy, OnInit} from '@angular/core';
import {Person} from './person';
import {Subject} from 'rxjs/Subject';
import {PersonService} from './person.service';
import {Router} from '@angular/router';
import {AnimalService} from '../../shared/animal.service';
import {FilterService} from '../../shared/filter.service';

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
              private _filterService: FilterService, private _router: Router) {
  }

  ngOnInit() {
    this.generateTable();
  }

  get filter(): string {
    return this._filter;
  }

  set filter(value: string) {
    this._filter = value;
    if (this.filter) {
      const filterObject = this._filterService.performPersonFilter(this.filter, this.people);
      this.filteredPeople = filterObject.data;
      this.errorMessage = filterObject.message;
    } else {
      this.filteredPeople = this.people;
    }
  }

  markAsActive(activeRow: Person): void {
    if (this.activePerson === activeRow) {
      this.activePerson = null;
    } else {
      this.activePerson = activeRow;
    }
  }

  onInfoAddress(): void {
    if (!this.isFieldSelected()) {
      this._router.navigate(['/admin', 'people', this.activePerson.id, 'details', 'address']);
    }
  }

  onInfoAnimal(): void {
    if (!this.isFieldSelected()) {
      this._router.navigate(['/admin', 'people', this.activePerson.id, 'details', 'animal']);
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

  private removeConfirm(): void {
    if (confirm('Confirm deletion')) {
      this.removePerson();
    }
  }

  private removePerson(): void {
    this._personService.deletePerson(this.activePerson)
      .takeUntil(this.ngUnsubscribe)
      .subscribe(
        response => {
          const data = this.people.filter(person => person !== this.activePerson);
          this.people = data;
          this.filteredPeople = data;
          this.activePerson = null;
          this.checkArrayForPeople();
        }, error => console.log('Error! Person not deleted!' + error)
      );
  }

  private generateTable(): void {
    this._personService.getAllPeople()
      .takeUntil(this.ngUnsubscribe)
      .subscribe(
        people => {
          this.people = people;
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
