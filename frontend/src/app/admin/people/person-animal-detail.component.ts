import {Component, OnDestroy, OnInit} from '@angular/core';
import {Person} from './person';
import {Animal} from '../../shared/animal';
import {Subject} from 'rxjs/Subject';
import {AnimalService} from '../../shared/animal.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ValidationService} from '../../shared/validation.service';

@Component({
  templateUrl: './person-animal-detail.component.html',
})
export class PersonAnimalDetailComponent implements OnInit, OnDestroy {

  public person: Person;
  public animals: Animal[];
  public activeAnimal: Animal;

  private ngUnsubscribe: Subject<any> = new Subject();

  constructor(private _animalService: AnimalService, private _route: ActivatedRoute,
              private _router: Router) {
  }

  ngOnInit() {
    this.person = this._route.snapshot.data['person'];
    this.animals = this._route.snapshot.data['animals'];
    console.log(this.animals);
  }

  markAsActive(activeRow: Animal): void {
    if (this.activeAnimal === activeRow) {
      this.activeAnimal = null;
    } else {
      this.activeAnimal = activeRow;
    }
  }

  onAddAnimal(): void {
    this._router.navigate(['/admin', 'people', this.person.id, 'animals', 'new']);
  }

  onEditAnimal(): void {
    if (!this.activeAnimal) {
      ValidationService.cannotProceed('Please select a row!');
    } else {
      this._router.navigate(['/admin', 'people', this.person.id, 'animals', this.activeAnimal.id]);
    }
  }

  onRemoveAnimal(): void {
    if (!this.activeAnimal) {
      ValidationService.cannotProceed('Please select a row!');
    } else {
      this.removeConfirm();
    }
  }

  onBack(): void {
    this._router.navigate(['/admin', 'people']);
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  private removeConfirm(): void {
    if (confirm('Confirm deletion')) {
      this.removeAddress();
    }
  }

  private removeAddress(): void {
    this._animalService.deleteOwnedAnimal(this.person.id, this.activeAnimal.id)
      .takeUntil(this.ngUnsubscribe)
      .subscribe(
        response => {
          this.animals = this.animals.filter((element) => element !== this.activeAnimal);
          this.activeAnimal = null;
          console.log(response);
        }, error => console.log('Error! Animal not deleted!' + error)
      );
  }
}
