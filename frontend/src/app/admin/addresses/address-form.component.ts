import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subject} from 'rxjs/Subject';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Person} from '../people/person';
import {Address} from './address';
import {ValidationService} from '../../shared/validation.service';
import {ActivatedRoute, Router} from '@angular/router';
import {AddressService} from './address.service';

@Component({
  templateUrl: './address-form.component.html'
})
export class AddressFormComponent implements OnInit, OnDestroy {

  public activeAddress: Address;
  public activePerson: Person;
  public isNewAddress: boolean;
  public addressForm: FormGroup;
  public submitted: boolean;
  public formErrors = {
    'cityName': '',
    'streetName': '',
    'zipCode': ''
  };

  private personId: number;
  private ngUnsubscribe: Subject<any> = new Subject();

  constructor(private _addressService: AddressService, private _validationService: ValidationService,
              private _router: Router, private _route: ActivatedRoute) {
  }

  ngOnInit() {
    this.getFormData();

    this.setUpForm();

    this.addressForm.valueChanges
      .takeUntil(this.ngUnsubscribe)
      .subscribe(data => this._validationService.onValueChanged(
        this.addressForm, this.formErrors));

    this._validationService.onValueChanged(this.addressForm, this.formErrors);
  }

  onSubmit(id: number): void {
    this.submitted = true;

    if (this.isNewAddress) {
      this.tryToSaveNewAddress();
    } else {
      if (this.checkForAddressDataDuplication()) {
        return;
      } else {
        this.tryToUpdateAddress(id);
      }
    }
  }

  onBack(): void {
    this._router.navigate(['/admin', 'people', this.personId, 'addresses']);
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  private tryToSaveNewAddress(): void {
    this.activeAddress = this.addressForm.value;
    this._addressService.saveAddress(this.activeAddress, this.personId)
      .takeUntil(this.ngUnsubscribe)
      .subscribe(
        response => console.log('Address saved successfully ' + response),
        error => console.log('Error! Address was not saved! ' + error)
      );
  }

  private tryToUpdateAddress(id: number): void {
    this.activeAddress = this.addressForm.value;
    this.activeAddress.id = id;
    this._addressService.updateAddress(this.activeAddress, this.personId)
      .takeUntil(this.ngUnsubscribe)
      .subscribe(
        response => console.log('Address updated successfully ' + response),
        error => console.log('Error! Address was not updated! ' + error)
      );
  }

  private checkForAddressDataDuplication(): boolean {
    if (this.activeAddress.streetName === this.addressForm.value.streetName
      && this.activeAddress.zipCode === this.addressForm.value.zipCode
      && this.activeAddress.cityName === this.addressForm.value.cityName) {
      this.submitted = false;
      ValidationService.cannotProceed('You try to update to the same address data!');
      return true;
    }
    return false;
  }

  private getFormData(): void {
    this.personId = +this._route.snapshot.paramMap.get('id');
    if (!this._route.snapshot.data['addresses']) {
      this.activeAddress = new Address();
      this.isNewAddress = true;
    } else {
      const data = this._route.snapshot.data['addresses'];
      const addressId = +this._route.snapshot.paramMap.get('addressId');
      this.activeAddress = data.find((element) => element.id === addressId);
      this.isNewAddress = false;
    }
    this.activePerson = this._route.snapshot.data['person'];
  }

  private setUpForm(): void {
    const id = new FormControl('');
    const streetName = new FormControl(this.activeAddress.streetName,
      [Validators.required, Validators.minLength(3)]);
    const cityName = new FormControl(this.activeAddress.cityName,
      [Validators.required, Validators.minLength(3)]);
    const zipCode = new FormControl(this.activeAddress.zipCode,
      [Validators.required, Validators.minLength(6), Validators.maxLength(6),
        Validators.pattern(/\d{2}-\d{3}/i)]);

    this.addressForm = new FormGroup({
      id: id,
      streetName: streetName,
      cityName: cityName,
      zipCode: zipCode
    });
  }
}
