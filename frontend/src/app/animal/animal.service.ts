import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient} from '@angular/common/http';
import {Animal} from './animal';

@Injectable()
export class AnimalService {

  private _getAnimalsAvailableForAdoption = '/api/animals/availableForAdoption';

  constructor(private _http: HttpClient) {
  }

  getAnimalsAvailableForAdoption(): Observable<Animal[]> {
    return this._http.get<Animal[]>(this._getAnimalsAvailableForAdoption);
  }
}
