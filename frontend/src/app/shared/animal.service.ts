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

  constructor(private _http: HttpClient) {
  }

  getAnimalsCount(): Observable<number> {
    return this._http.get<number>(this._getAnimalsCount);
  }

  getAllAnimals(): Observable<Animal[]> {
    return this._http.get<Animal[]>(this._getAllAnimals);
  }

  saveNewAnimal(animal: Animal): Observable<Animal> {
    return this._http.post<Animal>(this._saveNewAnimal[animal.type.toLowerCase()], animal, this.requestBearer());
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
