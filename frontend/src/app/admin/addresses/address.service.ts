import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Address} from './address';

@Injectable()
export class AddressService {

  private headers = {headers: new HttpHeaders().set('Content-Type', 'application/json')};
  private _getAllPersonAddresses = '/api/person/{personId}/addresses';
  private _saveAddress = '/api/person/{personId}/addresses';
  private _updateAddress = '/api/person/{personId}/address/{addressId}';
  private _deleteAddress = '/api/person/{personId}/address/{addressId}';
  private _updateMainAddress = '/api/updateMainAddress';

  constructor(private _http: HttpClient) {
  }

  getAllPersonAddresses(id: number): Observable<Address[]> {
    return this._http.get<Address[]>(this._getAllPersonAddresses.replace('{personId}', id.toString()));
  }

  saveAddress(address: Address, personId: number): Observable<Address> {
    return this._http.post<Address>(this._saveAddress.replace('{personId}', personId.toString()),
      address, this.headers);
  }

  updateAddress(address: Address, personId: number): Observable<Address> {
    return this._http.put<Address>(this._updateAddress.replace('{personId}', personId.toString()),
      address, this.headers);
  }

  deleteAddress(personId: number, addressId: number): Observable<string> {
    return this._http.delete(this._deleteAddress.replace('{personId}', personId.toString())
      .replace('{addressId}', addressId.toString()), {responseType: 'text'});
  }

  setAsMainAddress(personId: number, addressId: number): Observable<string> {
    return this._http.put(this._updateMainAddress, {personId: personId, addressId: addressId}, {
      headers: new HttpHeaders().set('Content-Type', 'application/json'),
      responseType: 'text'
    });
  }
}
