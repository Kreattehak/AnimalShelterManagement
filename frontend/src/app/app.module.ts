import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {AvailableAnimalsListComponent} from './visitor/animals/available-animals-list.component';
import {AnimalService} from './shared/animal.service';
import {HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from './app-routing.module';
import {MainPageComponent} from './visitor/main-page/main-page.component';
import {LoginComponent} from './admin/login/login.component';
import {AdminComponent} from './admin/main-page/admin.component';
import {CanActivateAuthGuard} from './admin/login/can-activate-auth.guard';
import {ValidationService} from './shared/validation-and-locale-messages.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AuthenticationService} from './admin/login/authentication.service';
import {AnimalListComponent} from './admin/animals/animal-list.component';
import {AnimalFormComponent} from './admin/animals/animal-form.component';
import {PersonFormComponent} from './admin/people/person-form.component';
import {AddressFormComponent} from './admin/addresses/address-form.component';
import {PersonService} from './admin/people/person.service';
import {AnimalDetailComponent} from './visitor/animals/animal-detail.component';
import {PersonDetailComponent} from './admin/people/person-detail.component';
import {PersonListComponent} from './admin/people/person-list.component';
import {ComplexAnimalDetailComponent} from './admin/animals/complex-animal-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    AvailableAnimalsListComponent,
    AnimalDetailComponent,
    MainPageComponent,
    LoginComponent,
    AdminComponent,
    AnimalListComponent,
    AnimalFormComponent,
    PersonFormComponent,
    AddressFormComponent,
    PersonDetailComponent,
    PersonListComponent,
    ComplexAnimalDetailComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [
    AnimalService,
    PersonService,
    CanActivateAuthGuard,
    ValidationService,
    AuthenticationService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
