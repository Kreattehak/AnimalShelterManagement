import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Animal} from './animal';

@Injectable()
export class AnimalService {

  private _getAnimalsCount = '/api/animals/count';
  private _getAllAnimals = '/api/animals/availableForAdoption';
  private _saveNewAnimal = {
    cat: '/api/cats',
    dog: '/api/dogs',
    bird: '/api/birds'
  };
  private _getAnimalsAvailableForAdoption = '/api/animals/availableForAdoption';

  public animalTypes: {} = [
    {value: 'DOG', option: 'Dog'},
    {value: 'CAT', option: 'Cat'},
    {value: 'BIRD', option: 'Bird'}
  ];
  public availabilityTypes: {} = [
    {value: 'AVAILABLE', option: 'Available'},
    {value: 'UNDER_VETERINARY_CARE', option: 'Under veterinary care'},
    {value: 'ADOPTED', option: 'Adopted'},
    {value: 'BEFORE_VACCINATION', option: 'Before vaccination'},
  ];
  public dogRaces: {} = [
    {value: 'HUSKY', option: 'Husky'},
    {value: 'GERMAN_SHEPERD', option: 'German Sheperd'},
    {value: 'CROSSBREAD', option: 'Crossbread'},
    {value: 'ENGLISH_COCKER_SPANIEL', option: 'English Cocker Spaniel'}
  ];
  public catRaces: {} = [
    {value: 'PERSIAN', option: 'Persian'},
    {value: 'ROOFLANDER', option: 'Rooflander'},
  ];
  public birdSpecies: {} = [
    {value: 'PARROT,', option: 'Parrot'},
    {value: 'CANARY,', option: 'Canary'},
    {value: 'COCKATIEL', option: 'Cockatiel'},
    {value: 'AFRICAN_GREY', option: 'African Grey'}
  ];

  constructor(private _http: HttpClient) {
  }

  getAnimalsCount(): Observable<number> {
    return this._http.get<number>(this._getAnimalsCount);
  }

  getAllAnimals(): Observable<Animal[]> {
    return this._http.get<Animal[]>(this._getAllAnimals);
  }

  saveNewAnimal(animal: Animal): Observable<Animal> {
    const aimalUrlByType = this._saveNewAnimal[animal.type.toLowerCase()];
    return this._http.post<Animal>(aimalUrlByType, animal, this.requestBearer());
  }

  getAnimalsAvailableForAdoption(): Observable<Animal[]> {
    return this._http.get<Animal[]>(this._getAnimalsAvailableForAdoption);
  }

  private requestBearer(): { headers: HttpHeaders } {
    return {
      headers: new HttpHeaders().set('Content-Type', 'application/json')
    };
  }
}
