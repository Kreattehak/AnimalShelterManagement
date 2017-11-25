import {Component, OnDestroy, OnInit} from '@angular/core';
import {ValidationService} from '../../shared/validation-and-locale-messages.service';
import {ActivatedRoute} from '@angular/router';
import {Subject} from 'rxjs/Subject';
import {Person} from './person';
import {Animal} from '../../shared/animal';
import {AnimalService} from '../../shared/animal.service';

@Component({
  templateUrl: './person-detail.component.html'
})
export class PersonDetailComponent implements OnInit, OnDestroy {

  public person: Person;
  public animals: Animal[];

  private ngUnsubscribe: Subject<string> = new Subject();

  constructor(private _route: ActivatedRoute) {
  }

  ngOnInit() {
    this.person = this._route.snapshot.data['person'];
    this.animals = this._route.snapshot.data['animals'];
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

}
