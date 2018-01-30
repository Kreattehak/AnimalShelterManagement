import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Subject} from 'rxjs/Subject';
import {Person} from './person';
import {Address} from '../addresses/address';
import {AddressService} from '../addresses/address.service';
import {ValidationService} from '../../shared/validation.service';

@Component({
  templateUrl: './person-address-detail.component.html'
})
export class PersonAddressDetailComponent implements OnInit, OnDestroy {

  public person: Person;
  public addresses: Address[];
  public activeAddress: Address;

  private ngUnsubscribe: Subject<any> = new Subject();

  constructor(private _addressService: AddressService, private _route: ActivatedRoute,
              private _router: Router) {
  }

  ngOnInit() {
    this.person = this._route.snapshot.data['person'];
    this.addresses = this._route.snapshot.data['addresses'];
  }

  markAsActive(activeRow: Address): void {
    if (this.activeAddress === activeRow) {
      this.activeAddress = null;
    } else {
      this.activeAddress = activeRow;
    }
  }

  onAddAddress(): void {
    this._router.navigate(['/admin', 'people', this.person.id, 'addresses', 'new']);
  }

  onEditAddress(): void {
    if (!this.activeAddress) {
      ValidationService.cannotProceed('Please select a row!');
    } else {
      this._router.navigate(['/admin', 'people', this.person.id, 'addresses', this.activeAddress.id]);
    }
  }

  onSetAsMainAddress(): void {
    if (!this.activeAddress) {
      ValidationService.cannotProceed('Please select a row!');
    } else if (this.activeAddress.id === this.person.mainAddress.id) {
      ValidationService.cannotProceed('This address is already main address!');
    } else {
      this.setAsMainAddress();
    }
  }

  onRemoveAddress(): void {
    if (!this.activeAddress) {
      ValidationService.cannotProceed('Please select a row!');
    } else if (this.person.mainAddress.id === this.activeAddress.id) {
      ValidationService.cannotProceed('Cannot remove main address, please change user main address!');
    } else {
      this.removeConfirm();
    }
  }

  onBack(): void {
    this._router.navigate(['/admin', 'people']);
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }

  private setAsMainAddress(): void {
    this._addressService.setAsMainAddress(this.person.id, this.activeAddress.id)
      .takeUntil(this.ngUnsubscribe)
      .subscribe(
        response => {
          this.person.mainAddress = this.activeAddress;
          this.activeAddress = null;
          console.log(response);
        },
        error => console.log(error)
      );
  }

  private removeConfirm(): void {
    if (confirm('Confirm deletion')) {
      this.removeAddress();
    }
  }

  private removeAddress(): void {
    this._addressService.deleteAddress(this.person.id, this.activeAddress.id)
      .takeUntil(this.ngUnsubscribe)
      .subscribe(
        response => {
          this.addresses = this.addresses.filter((element) => element !== this.activeAddress);
          this.activeAddress = null;
          console.log(response);
        }, error => console.log('Error! Address not deleted!' + error)
      );
  }
}
