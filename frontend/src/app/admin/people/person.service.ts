import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Person} from './person';

@Injectable()
export class PersonService {

  private headers = {headers: new HttpHeaders().set('Content-Type', 'application/json')};
  private _getAllPeople = '/api/people';
  private _getPerson = '/api/person/';
  private _savePerson = '/api/people';
  private _updatePerson = '/api/person/';
  private _deletePerson = '/api/person/';

  constructor(private _http: HttpClient) {
  }

  getAllPeople(): Observable<Person[]> {
    return this._http.get<Person[]>(this._getAllPeople);
  }

  getPerson(id: number): Observable<Person> {
    return this._http.get<Person>(this._getPerson + id);
  }

  savePerson(person: Person): Observable<Person> {
    return this._http.post<Person>(this._savePerson, person, this.headers);
  }

  updatePerson(person: Person): Observable<Person> {
    return this._http.put<Person>(this._updatePerson + person.id, person, this.headers);
  }

  deletePerson(person: Person): Observable<string> {
    return this._http.delete(this._deletePerson + person.id, {responseType: 'text'});
  }
}
