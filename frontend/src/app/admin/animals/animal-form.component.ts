import {Component, OnDestroy, OnInit} from '@angular/core';
import {Location} from '@angular/common';
import {Animal} from '../../shared/animal';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Subject} from 'rxjs/Subject';
import {AnimalService} from '../../shared/animal.service';
import {ActivatedRoute} from '@angular/router';
import {ValidationService} from '../../shared/validation.service';

@Component({
  templateUrl: './animal-form.component.html'
})
export class AnimalFormComponent implements OnInit, OnDestroy {

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

  constructor(public _animalService: AnimalService, private _validationService: ValidationService,
              private _route: ActivatedRoute, private _location: Location) {
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
      this.tryToUpdateAnimal(id);
    }
  }

  onBack(): void {
    this._location.back();
  }

  resetSubTypeOnTypeChange(): void {
    this.animalForm.controls.subType.reset('');
    if (this.animalForm.value.type === this.activeAnimal.type) {
      this.animalForm.controls.subType.setValue(this.activeAnimal.subType);
    }
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

  private tryToUpdateAnimal(id: number): void {
    const animalData: Animal =  this.animalForm.value;
    animalData.id = id;
    this._animalService.updateAnimal(animalData)
      .takeUntil(this.ngUnsubscribe)
      .subscribe(
        response => console.log('Animal updated successfully' + response),
        error => console.log('Error! Animal was not updated!' + error)
      );
  }

  private getFormData(): void {
    if (!this._route.snapshot.data['animal']) {
      this.activeAnimal = new Animal();
      this.isNewAnimal = true;
    } else {
      this.activeAnimal = this._route.snapshot.data['animal'];
      this.selectedValue = this.activeAnimal.type;
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
    const dateOfBirth = new FormControl(this.activeAnimal.dateOfBirth,
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
