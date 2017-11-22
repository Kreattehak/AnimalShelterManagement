import {Injectable} from '@angular/core';
import {FormGroup} from '@angular/forms';

@Injectable()
export class ValidationService {

  public validationMessages = {
    'firstName': {
      'required': 'First name is required.',
      'minlength': 'First name must be at least 3 characters long.',
    },
    'lastName': {
      'required': 'Last name is required.',
      'minlength': 'Last name must be at least 3 characters long.',
    },
    'streetName': {
      'required': 'Street name is required.',
      'minlength': 'Street name must be at least 3 characters long.',
    },
    'cityName': {
      'required': 'City name is required.',
      'minlength': 'City name must be at least 3 characters long.',
    },
    'zipCode': {
      'required': 'Zipcode is required.',
      'minlength': 'Zipcode can\'t have less values than 6.',
      'maxlength': 'Zipcode can\'t have more values than 6.',
      'pattern': 'ZipCode value inserted is not valid',
    },
    'username': {
      'required': 'Username is required.',
      'minlength': 'Username must be at least 3 characters long.',
    },
    'password': {
      'required': 'Password is required.',
    },
    'name': {
      'required': 'Animal name is required.',
      'minlength': 'Animal name must be at least 3 characters long.',
    },
    'type': {
      'required': 'Animal type is required.',
    },
    'subType': {
      'required': 'Animal subtype is required.',
    },
    'behaviorDescription': {
      'minlength': 'Description must be at least 10 characters long.',
    },
    'availabilityType': {
      'required': 'Animal must has specified availability for adoption.',
    },
    'dateOfBirth': {
      'pattern': 'Please provide date of birth in the following way Year-Month-Day',
    },
  };

  constructor() {
  }

  public onValueChanged(form: FormGroup, formErrors: {}) {
    if (!form) {
      return;
    }
    for (const field in formErrors) {
      if (formErrors.hasOwnProperty(field)) {
        this.validateFormField(formErrors, field, form);
      }
    }
  }

  private validateFormField(formErrors: {}, field, form: FormGroup) {
    formErrors[field] = '';
    const control = form.get(field);
    if (control && control.dirty && !control.valid) {
      for (const key in control.errors) {
        if (control.errors.hasOwnProperty(key)) {
          formErrors[field] += this.validationMessages[field][key] + ' ';
        }
      }
    }
  }
}
