import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subject} from 'rxjs/Subject';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Person} from './person';
import {ActivatedRoute, Router} from '@angular/router';
import {PersonService} from './person.service';
import {ValidationService} from '../../shared/validation.service';

@Component({
  templateUrl: './person-form.component.html'
})
export class PersonFormComponent implements OnInit, OnDestroy {

  public activePerson: Person;
  public isNewPerson: boolean;
  public shouldRedirectToAddressForm: boolean;
  public personForm: FormGroup;
  public submitted: boolean;
  public formErrors = {
    'firstName': '',
    'lastName': ''
  };

  private ngUnsubscribe: Subject<any> = new Subject();

  constructor(private _personService: PersonService, private _validationService: ValidationService,
              private _route: ActivatedRoute, private _router: Router) {
  }

  ngOnInit() {
    this.getFormData();

    this.setUpForm();

    this.personForm.valueChanges
      .takeUntil(this.ngUnsubscribe)
      .subscribe(data => this._validationService.onValueChanged(
        this.personForm, this.formErrors));

    this._validationService.onValueChanged(this.personForm, this.formErrors);
  }

  onSubmit(id: number): void {
    this.submitted = true;

    if (this.isNewPerson) {
      this.tryToSaveNewPerson();
    } else {
      if (this.checkForPersonDataDuplication()) {
        return;
      } else {
        this.tryToUpdatePerson(id);
      }
    }
  }

  onBack(): void {
    this._router.navigate(['/admin', 'people']);
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  private tryToSaveNewPerson(): void {
    this.activePerson = this.personForm.value;
    this._personService.savePerson(this.activePerson)
      .takeUntil(this.ngUnsubscribe)
      .subscribe(
        response => {
          if (this.shouldRedirectToAddressForm) {
            this._router.navigate(['/admin', 'people', response.id, 'addresses', 'new']);
          }
          console.log('Person saved successfully' + response);
        }, error => console.log('Error! Person was not saved!' + error)
      );
  }

  private tryToUpdatePerson(id: number): void {
    this.activePerson = this.personForm.value;
    this.activePerson.id = id;
    this._personService.updatePerson(this.activePerson)
      .takeUntil(this.ngUnsubscribe)
      .subscribe(
        response => console.log('Person updated successfully' + response),
        error => console.log('Error! Person was not updated!' + error)
      );
  }

  private checkForPersonDataDuplication(): boolean {
    if (this.activePerson.lastName === this.personForm.value.lastName
      && this.activePerson.firstName === this.personForm.value.firstName) {
      console.log('Provided user data is the same as it was.');
      this.submitted = false;
      return true;
    }
    return false;
  }

  private getFormData(): void {
    if (!this._route.snapshot.data['person']) {
      this.activePerson = new Person();
      this.isNewPerson = true;
    } else {
      this.activePerson = this._route.snapshot.data['person'];
      this.isNewPerson = false;
    }
  }

  private setUpForm(): void {
    const id = new FormControl();
    const firstName = new FormControl(this.activePerson.firstName,
      [Validators.required, Validators.minLength(3)]);
    const lastName = new FormControl(this.activePerson.lastName,
      [Validators.required, Validators.minLength(3)]);

    this.personForm = new FormGroup({
      id: id,
      firstName: firstName,
      lastName: lastName
    });
  }
}
