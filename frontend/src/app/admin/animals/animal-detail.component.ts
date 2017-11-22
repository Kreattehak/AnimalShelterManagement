import {Component, OnDestroy, OnInit} from '@angular/core';
import {Animal} from '../../shared/animal';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Subject} from 'rxjs/Subject';
import {AnimalService} from '../../shared/animal.service';
import {ValidationService} from '../../shared/validation-and-locale-messages.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  templateUrl: './animal-detail.component.html'
})
export class AnimalDetailComponent implements OnInit, OnDestroy {

  public activeAnimal: Animal;
  public isNewAnimal: boolean;
  public animalForm: FormGroup;
  public submitted: boolean;
  public selectedValue: string;
  public formErrors = {
    'name': '',
    'type': '',
    'behaviorDescription': '',
    'availableForAdoption': '',
    'dateOfBirth': ''
  };

  private ngUnsubscribe: Subject<any> = new Subject();

  constructor(public _animalService: AnimalService,
              private _validationService: ValidationService,
              private _route: ActivatedRoute, private _router: Router) {
  }

  ngOnInit() {
    this.getFormData();

    this.setUpForm();

    this.animalForm.valueChanges
      .takeUntil(this.ngUnsubscribe)
      .subscribe(data => this._validationService.onValueChanged(
        this.animalForm, this.formErrors));

    this._validationService.onValueChanged(this.animalForm, this.formErrors);
  }

  onSubmit(id: number): void {
    this.submitted = true;

    if (this.isNewAnimal) {
      this.tryToSaveNewAnimal();
    } else {
      // if (this.checkForAnimalDataDuplication()) {
      //   return;
      // } else {
      //   this.tryToUpdateAnimal(id);
      // }
    }
  }

  onBack(): void {
    this._router.navigate(['/admin', 'animals']);
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  private tryToSaveNewAnimal(): void {
    this.activeAnimal = this.animalForm.value;
    this._animalService.saveNewAnimal(this.activeAnimal)
      .takeUntil(this.ngUnsubscribe)
      .subscribe(
        response => console.log('Animal saved successfully' + response),
        error => console.log('Error! Animal was not saved!' + error)
      );
  }


  // private tryToUpdateAnimal(id: number): void {
  //   this.activeAnimal = this.animalForm.value;
  //   this.activeAnimal.id = id;
  //   this._animalService.updateAnimal(this.activeAnimal)
  //     .takeUntil(this.ngUnsubscribe)
  //     .subscribe(
  //       response => this._toastr.success(response,
  //         this._validationService.getLocalizedMessages('successTitle')).then(
  //         () => this.onBack()),
  //       error => this._toastr.error(error,
  //         this._validationService.getLocalizedMessages('errorTitle')));
  // }

  // private checkForAnimalDataDuplication(): boolean {
  //   if (this.activeAnimal.lastName === this.animalForm.value.lastName
  //     && this.activeAnimal.firstName === this.animalForm.value.firstName) {
  //     this._toastr.error(this._validationService.getLocalizedMessages('animalExists'),
  //       this._validationService.getLocalizedMessages('errorTitle'));
  //     this.submitted = false;
  //     return true;
  //   }
  //   return false;
  // }

  private getFormData(): void {
    if (!this._route.snapshot.data['animal']) {
      this.activeAnimal = new Animal();
      this.isNewAnimal = true;
    } else {
      this.activeAnimal = this._route.snapshot.data['animal'];
      this.isNewAnimal = false;
    }
  }

  private setUpForm(): void {
    const id = new FormControl();
    const name = new FormControl(this.activeAnimal.name,
      [Validators.required, Validators.minLength(3)]);
    const type = new FormControl(this.activeAnimal.type, [Validators.required]);
    const subType = new FormControl(this.activeAnimal.subType, [Validators.required]);
    const behaviorDescription = new FormControl(this.activeAnimal.behaviorDescription
      || 'No behavior description provided.', [Validators.minLength(10)]);
    const availableForAdoption = new FormControl(this.activeAnimal.availableForAdoption,
      [Validators.required]);
    const animalIdentifier = new FormControl();
    const dateOfBirth = new FormControl(this.activeAnimal.animalIdentifier,
      [Validators.pattern(/\d{4}-\d{2}-\d{2}/i)]);

    this.animalForm = new FormGroup({
      id: id,
      name: name,
      type: type,
      subType: subType,
      behaviorDescription: behaviorDescription,
      availableForAdoption: availableForAdoption,
      animalIdentifier: animalIdentifier,
      dateOfBirth: dateOfBirth
    });
  }
}
