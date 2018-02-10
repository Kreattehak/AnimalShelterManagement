import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Animal} from '../../shared/animal';
import {AnimalService} from '../../shared/animal.service';

@Injectable()
export class AnimalResolve implements Resolve<Animal> {

  constructor(private _animalService: AnimalService) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<Animal> {
    console.log(route.paramMap);
    return this._animalService.getAnimal(route.paramMap.get('animalType'), +route.paramMap.get('id'));
  }
}
