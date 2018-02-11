import {Component, OnInit} from '@angular/core';
import {AnimalService} from '../../shared/animal.service';
import {Animal} from '../../shared/animal';
import {Person} from '../people/person';
import {PersonService} from '../people/person.service';

@Component({
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {

  animalsCount: number;
  animals: Animal[];
  people: Person[];

  constructor(private _animalService: AnimalService, private _personService: PersonService) {
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

  getAllPeople(): void {
    this._personService.getAllPeople().subscribe(
      people => this.people = people
    );
  }
}
