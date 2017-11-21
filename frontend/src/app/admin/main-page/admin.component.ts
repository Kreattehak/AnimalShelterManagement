import {Component, OnInit} from '@angular/core';
import {AnimalService} from '../../shared/animal.service';
import {Animal} from '../../shared/animal';

@Component({
  templateUrl: './admin.component.html'
})
export class AdminComponent implements OnInit {

  animalsCount: number;
  animals: Animal[];

  constructor(private _animalService: AnimalService) {
  }

  ngOnInit() {
    this._animalService.getAnimalsCount().subscribe(
      count => this.animalsCount = count
    );
  }

  getAllAnimals(): void {
    this._animalService.getAllAnimals().subscribe(
      animals => this.animals = animals
    );
  }
}
