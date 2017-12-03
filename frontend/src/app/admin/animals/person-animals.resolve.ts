import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Animal} from '../../shared/animal';
import {AnimalService} from '../../shared/animal.service';

@Injectable()
export class PersonAnimalsResolve implements Resolve<Animal[]> {

  constructor(private _animalService: AnimalService) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<Animal[]> {
    return this._animalService.getAnimalsOwnedByPerson(+route.paramMap.get('id'));
  }
}
