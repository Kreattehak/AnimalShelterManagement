import {Component, OnDestroy, OnInit} from '@angular/core';
import {Animal} from '../../shared/animal';
import {Subject} from 'rxjs/Subject';
import {AnimalService} from '../../shared/animal.service';
import 'rxjs/add/operator/takeUntil';

@Component({
  templateUrl: './available-animals-list.component.html',
  styleUrls: ['./available-animals-list.component.css']
})
export class AvailableAnimalsListComponent implements OnInit, OnDestroy {

  private animals: Animal[];
  private filteredAnimals: Animal[];
  private ngUnsubscribe: Subject<Animal[]> = new Subject();

  constructor(private _animalService: AnimalService) {
  }

  ngOnInit() {
    this._animalService.getAnimalsAvailableForAdoption()
      .takeUntil(this.ngUnsubscribe)
      .subscribe(animals => {
        this.animals = animals;
        this.filteredAnimals = this.animals;
      });
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }
}
