import {ActivatedRouteSnapshot, Resolve} from '@angular/router';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {AddressService} from './address.service';
import {Address} from './address';

@Injectable()
export class PersonAddressesResolve implements Resolve<Address[]> {

  constructor(private _addressService: AddressService) {
  }

  resolve(route: ActivatedRouteSnapshot): Observable<Address[]> {
    return this._addressService.getAllPersonAddresses(+route.paramMap.get('id'));
  }
}
