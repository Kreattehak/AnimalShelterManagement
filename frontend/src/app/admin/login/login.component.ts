import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Subject} from 'rxjs/Subject';
import {ValidationService} from '../../shared/validation-and-locale-messages.service';
import {AuthenticationService} from './authentication.service';
import {Router} from '@angular/router';

@Component({
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit, OnDestroy {

  public userForm: FormGroup;
  public formErrors = {
    'username': '',
    'password': '',
  };

  private ngUnsubscribe: Subject<any> = new Subject();

  constructor(private _router: Router, private _authenticationService: AuthenticationService,
              private _validationService: ValidationService) {
  }

  ngOnInit() {
    const username = new FormControl('', [Validators.required, Validators.minLength(3)]);
    const password = new FormControl('', [Validators.required]);

    this.userForm = new FormGroup({
      username: username,
      password: password
    });

    this.userForm.valueChanges
      .takeUntil(this.ngUnsubscribe)
      .subscribe(data => this._validationService.onValueChanged(
        this.userForm, this.formErrors));

    this._validationService.onValueChanged(this.userForm, this.formErrors);
  }

  login(): void {
    this._authenticationService.login(this.userForm.value.username, this.userForm.value.password);
    this._router.navigate(['/admin']);
  }

  ngOnDestroy() {
    this.ngUnsubscribe.next();
    this.ngUnsubscribe.complete();
  }
}
