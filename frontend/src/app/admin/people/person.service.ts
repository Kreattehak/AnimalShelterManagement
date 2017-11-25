import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Person} from './person';

@Injectable()
export class PersonService {

  private _getAllPeople = '/api/people';
  private _saveNewPerson = '/api/people';

  constructor(private _http: HttpClient) {
  }

  getAllPeople(): Observable<Person[]> {
    return this._http.get<Person[]>(this._getAllPeople);
  }

  saveNewPerson(person: Person): Observable<Person> {
    return this._http.post<Person>(this._saveNewPerson, person, this.requestBearer());
  }

  private requestBearer(): { headers: HttpHeaders } {
    return {
      headers: new HttpHeaders().set('Content-Type', 'application/json')
    };
  }
}
