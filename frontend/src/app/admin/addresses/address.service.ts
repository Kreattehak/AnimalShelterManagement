import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {Address} from './address';

@Injectable()
export class AddressService {

  private headers = {headers: new HttpHeaders().set('Content-Type', 'application/json')};
  private _getAllPersonAddresses = '/api/person/{personId}/addresses';
  private _updateMainAddress = '/api/updateMainAddress';
  private _saveNewPerson = '/api/people';
  private _updatePerson = '/api/person/';
  private _deleteAddress = '/api/person/{personId}/address/{addressId}';

  constructor(private _http: HttpClient) {
  }

  getAllPersonAddresses(id: number): Observable<Address[]> {
    return this._http.get<Address[]>(this._getAllPersonAddresses.replace('{personId}', id.toString()));
  }

  setAsMainAddress(personId: number, addressId: number): Observable<string> {
    return this._http.put(this._updateMainAddress, {personId: personId, addressId: addressId}, {
      headers: new HttpHeaders().set('Content-Type', 'application/json'),
      responseType: 'text'
    });
  }

  deleteAddress(personId: number, addressId: number): Observable<string> {
    return this._http.delete(this._deleteAddress.replace('{personId}', personId.toString())
      .replace('{addressId}', addressId.toString()), {responseType: 'text'});
  }
}
