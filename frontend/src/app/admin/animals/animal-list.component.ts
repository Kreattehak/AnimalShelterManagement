import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subject} from 'rxjs/Subject';
import {AnimalService} from '../../shared/animal.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Animal} from '../../shared/animal';

@Component({
  templateUrl: './animal-list.component.html'
})
export class AnimalListComponent implements OnInit, OnDestroy {

  public filterAnimalName: string;
  public filterAnimalIdentifier: string;
  public animalType = 'Select animal type';
  public filteredAnimals: Animal[];
  public activeAnimal: Animal;

  private ngUnsubscribe: Subject<any> = new Subject();

  constructor(private _animalService: AnimalService, private _route: ActivatedRoute,
              private _router: Router) {

  }

  ngOnInit() {
    this._animalService.getRecentlyAddedAnimals().subscribe(
      response => {
        this.filteredAnimals = response.content;
      },
      error => console.log(error)
    );
  }

  markAsActive(activeRow: Animal): void {
    if (this.activeAnimal === activeRow) {
      this.activeAnimal = null;
    } else {
      this.activeAnimal = activeRow;
    }
  }

  onAddAnimal(): void {
    this._router.navigate(['admin', 'animals', 'new']);
  }

  onEditAnimal(): void {
    this._router.navigate(['admin', 'animals', this.activeAnimal.id,  {animalType: this.activeAnimal.type}]);
  }

  onBack(): void {
    this._router.navigate(['admin']);
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
