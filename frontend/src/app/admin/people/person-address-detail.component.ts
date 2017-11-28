import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Subject} from 'rxjs/Subject';
import {Person} from './person';
import {Animal} from '../../shared/animal';
import {Address} from '../addresses/address';

@Component({
  templateUrl: './person-address-detail.component.html'
})
export class PersonAddressDetailComponent implements OnInit, OnDestroy {

  public person: Person;
  public animals: Animal[];
  public addresses: Address[];
  public activeAddress: Address;

  private ngUnsubscribe: Subject<string> = new Subject();

  constructor(private _route: ActivatedRoute, private _router: Router) {
  }

  ngOnInit() {
    this.person = this._route.snapshot.data['person'];
    this.animals = this._route.snapshot.data['animals'];
  }

  markAsActive(activeRow: Address): void {
    if (this.activeAddress === activeRow) {
      this.activeAddress = null;
    } else {
      this.activeAddress = activeRow;
    }
  }

  // onAddAddress(): boolean {
  //   this._router.navigate(['/people', this.person.id, 'newAddress']);
  //   return true;
  // }
  //
  // onEditAddresses(): boolean {
  //   if (!this.activeAddress) {
  //     return this.cannotProceed(this._validationService.getLocalizedMessages('rowNotSelected'));
  //   } else {
  //     this._router.navigate(['/people', this.person.id, 'address', this.activeAddress.id]);
  //     return true;
  //   }
  // }
  //
  // onSetAsMainAddress(): boolean {
  //   if (!this.activeAddress) {
  //     return this.cannotProceed(this._validationService.getLocalizedMessages('rowNotSelected'));
  //   } else if (this.activeAddress.id === this.person.mainAddress.id) {
  //     return this.cannotProceed(this._validationService.getLocalizedMessages('alreadyMainAddress'));
  //   } else {
  //     this.setAsMainAddress();
  //   }
  // }
  //
  // onRemoveAddress(): boolean {
  //   if (!this.activeAddress) {
  //     return this.cannotProceed(this._validationService.getLocalizedMessages('rowNotSelected'));
  //   } else if (this.person.mainAddress.id === this.activeAddress.id) {
  //     return this.cannotProceed(this._validationService.getLocalizedMessages('cannotDeleteMainAddress'),
  //       'medium');
  //   } else {
  //     this.removeConfirm();
  //   }
  // }
  //
  // onBack(): void {
  //   this._router.navigate(['/people']);
  // }
  //
  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }
  //
  // private setAsMainAddress(): void {
  //   this._addressService.setAsMainAddress(this.activeAddress.id, this.person.id)
  //     .takeUntil(this.ngUnsubscribe)
  //     .subscribe(
  //       response => {
  //         this.person.mainAddress = this.activeAddress;
  //         this.activeAddress = null;
  //         this._toastr.success(response, this._validationService.getLocalizedMessages('successTitle'));
  //       },
  //       error => this._toastr.error(error, this._validationService.getLocalizedMessages('errorTitle')));
  // }
  //
  // private removeConfirm(): void {
  //   bootbox.confirm({
  //     title: this._validationService.getLocalizedMessages('removeAddressConfirmTitle'),
  //     message: this._validationService.getLocalizedMessages('removeAddressConfirmMessage'),
  //     buttons: {
  //       cancel: {
  //         label: '<i class="fa fa-times"></i> ' + this._validationService.getLocalizedMessages('cancelAction')
  //       },
  //       confirm: {
  //         label: '<i class="fa fa-check"></i> ' + this._validationService.getLocalizedMessages('confirmAction')
  //       }
  //     },
  //     callback: this.removeAddress
  //   });
  // }
  //
  // private removeAddress = (result) => {
  //   if (result) {
  //     this._addressService.deleteAddress(this.activeAddress.id, this.person.id)
  //       .takeUntil(this.ngUnsubscribe)
  //       .subscribe(
  //         response => {
  //           this.addresses = this.addresses.filter((element) => element !== this.activeAddress);
  //           this.activeAddress = null;
  //           this._toastr.success(response, this._validationService.getLocalizedMessages('successTitle'));
  //           this._router.navigate(['/people', this.person.id, 'details']);
  //         }, error => this._toastr.error(error, this._validationService.getLocalizedMessages('errorTitle')));
  //     return true;
  //   } else {
  //     return;
  //   }
  // };
  //
  // private cannotProceed(message: string, size: string = 'small'): boolean {
  //   bootbox.alert({
  //     message: message,
  //     size: size,
  //     backdrop: true
  //   });
  //   return false;
  // }
}
