import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subject} from 'rxjs/Subject';
import {AnimalService} from '../../shared/animal.service';
import {Animal} from '../../shared/animal';
import {ActivatedRoute, Router} from '@angular/router';
import {Person} from './person';

@Component({
  templateUrl: './person-and-animal.component.html'
})
export class PersonAndAnimalComponent implements OnInit, OnDestroy {

  public filterAnimalName: string;
  public filterAnimalIdentifier: string;
  public animalType = 'Select animal type';
  public filteredAnimals: Animal[];
  public activeAnimal: Animal;
  public person: Person;

  private ngUnsubscribe: Subject<any> = new Subject();

  constructor(private _animalService: AnimalService, private _route: ActivatedRoute,
              private _router: Router) {
  }

  ngOnInit() {
    this._animalService.getAnimalsWithLongestWaitingTime().subscribe(
      response => {
        this.filteredAnimals = response.content;
        console.log(this.filteredAnimals);
      },
      error => console.log(error)
    );
    this.person = this._route.snapshot.data['person'];
  }

  markAsActive(activeRow: Animal): void {
    if (this.activeAnimal === activeRow) {
      this.activeAnimal = null;
    } else {
      this.activeAnimal = activeRow;
    }
  }

  onAddAnimal(): void {
    this._animalService.addAnimalToPerson(this.person.id, this.activeAnimal.id).subscribe(
      response => console.log(response),
      error => console.log(error)
    );
    this.filteredAnimals = this.filteredAnimals.filter((element) => element !== this.activeAnimal);
  }

  onBack(): void {
    this._router.navigate(['admin', 'people', this.person.id, 'animals']);
  }

  getAnimalByName() {
    if (this.isAnimalTypeSelected()) {
      this._animalService.getAnimalsAvailableForAdoptionByName(this.animalType, this.filterAnimalName).subscribe(
        response => this.filteredAnimals = response,
        error => console.log(error)
      );
    } else {
      alert('Please select animal type');
    }
  }

  getAnimalByIdentifier() {
    if (this.isAnimalTypeSelected()) {
      this._animalService.getAnimalAvailableForAdoptionByIdentifier(this.animalType, this.filterAnimalIdentifier).subscribe(
        response => this.filteredAnimals = response,
        error => console.log(error)
      );
    } else {
      alert('Please select animal type');
    }
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  private isAnimalTypeSelected(): boolean {
    return this.animalType !== 'Select animal type';
  }
}
