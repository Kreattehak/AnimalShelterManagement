import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {AvailableAnimalsListComponent} from './visitor/animals/available-animals-list.component';
import {HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from './app-routing.module';
import {MainPageComponent} from './visitor/main-page/main-page.component';
import {LoginComponent} from './admin/login/login.component';
import {AdminComponent} from './admin/main-page/admin.component';
import {CanActivateAuthGuard} from './admin/login/can-activate-auth.guard';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AuthenticationService} from './admin/login/authentication.service';
import {AnimalListComponent} from './admin/animals/animal-list.component';
import {AnimalFormComponent} from './admin/animals/animal-form.component';
import {PersonFormComponent} from './admin/people/person-form.component';
import {AddressFormComponent} from './admin/addresses/address-form.component';
import {PersonService} from './admin/people/person.service';
import {AnimalDetailComponent} from './visitor/animals/animal-detail.component';
import {PersonListComponent} from './admin/people/person-list.component';
import {ComplexAnimalDetailComponent} from './admin/animals/complex-animal-detail.component';
import {FilterService} from './shared/filter.service';
import {PersonResolve} from './admin/people/person.resolve';
import {PersonAddressDetailComponent} from './admin/people/person-address-detail.component';
import {PersonAnimalDetailComponent} from './admin/people/person-animal-detail.component';
import {AddressService} from './admin/addresses/address.service';
import {ValidationService} from './shared/validation.service';
import {PersonAddressesResolve} from './admin/addresses/person-addresses.resolve';
import {PersonAnimalsResolve} from './admin/people/person-animals.resolve';
import {AnimalService} from './shared/animal.service';
import {PersonAndAnimalComponent} from './admin/people/person-and-animal.component';
import {AnimalResolve} from "./admin/addresses/animal.resolve";

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
    PersonAddressDetailComponent,
    PersonAnimalDetailComponent,
    PersonListComponent,
    ComplexAnimalDetailComponent,
    PersonAnimalDetailComponent,
    PersonAndAnimalComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [
    AddressService,
    AnimalService,
    PersonService,
    CanActivateAuthGuard,
    ValidationService,
    AuthenticationService,
    FilterService,
    PersonResolve,
    AnimalResolve,
    PersonAddressesResolve,
    PersonAnimalsResolve
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
