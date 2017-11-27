import {Injectable} from '@angular/core';

@Injectable()
export class AuthenticationService {

  private isLoggedIn: boolean;

  constructor() {
  }

  checkIfIsLoggedIn(): boolean {
    return this.isLoggedIn;
  }

  login(username: string, password: string): void {
    if (username === 'admin' && password === 'admin') {
      this.isLoggedIn = true;
    }
  }
}
