import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Animal} from './animal';

@Injectable()
export class AnimalService {

  private headers = {headers: new HttpHeaders().set('Content-Type', 'application/json')};
  private _getAnimalsCount = '/api/animals/count';
  private _getAnimalsCountForPeople = '/api/animals/countForPeople';
  private _getAllAnimals = '/api/animals/availableForAdoption';
  private _saveOrDeleteAnimal = {
    cat: '/api/cats',
    dog: '/api/dogs',
    bird: '/api/birds'
  };
  private _deleteAnimalFromOwner = '/api/person/{personId}/animals/{animalId}';
  private _getAnimalsOwnedByPerson = '/api/person/{personId}/animals';
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

  getAnimalsCountForPeople(): Observable<number[]> {
    return this._http.get<number[]>(this._getAnimalsCountForPeople);
  }

  getAllAnimals(): Observable<Animal[]> {
    return this._http.get<Animal[]>(this._getAllAnimals);
  }

  getAnimalsOwnedByPerson(personId: number): Observable<Animal[]> {
    return this._http.get<Animal[]>(this._getAnimalsOwnedByPerson.replace('{personId}', personId.toString()));
  }

  getAnimalsAvailableForAdoption(): Observable<Animal[]> {
    return this._http.get<Animal[]>(this._getAnimalsAvailableForAdoption);
  }

  saveNewAnimal(animal: Animal): Observable<Animal> {
    return this._http.post<Animal>(this.getRequestUrlByAnimalType(animal.type), animal, this.headers);
  }

  // only removes OneToOne relationship between person and animal, animal is still in animal table
  deleteOwnedAnimal(personId: number, animalId: number): Observable<string> {
    return this._http.delete(this._deleteAnimalFromOwner.replace('{personId}', personId.toString())
      .replace('{animalId}', animalId.toString()), {responseType: 'text'});
  }

  private getRequestUrlByAnimalType(animalType: string) {
    return this._saveOrDeleteAnimal[animalType.toLowerCase()];
  }
}
