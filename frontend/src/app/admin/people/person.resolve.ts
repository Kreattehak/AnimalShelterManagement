import {Person} from './person';
import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {Injectable} from '@angular/core';
import {PersonService} from './person.service';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class PersonResolve implements Resolve<Person> {

  constructor(private _personService: PersonService) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<Person> {
    return this._personService.getPerson(+route.paramMap.get('id'));
  }
}
