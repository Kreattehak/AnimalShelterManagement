import {Component, OnDestroy, OnInit} from '@angular/core';
import {Animal} from './animal';
import {Subject} from 'rxjs/Subject';
import {AnimalService} from './animal.service';
import 'rxjs/add/operator/takeUntil';

@Component({
  selector: 'app-animal-list',
  templateUrl: './animal-list.component.html',
  styleUrls: ['./animal-list.component.css']
})
export class AnimalListComponent implements OnInit, OnDestroy {

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
