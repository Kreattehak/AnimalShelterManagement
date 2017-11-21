import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {AvailableAnimalsListComponent} from './visitor/animals/available-animals-list.component';
import {AnimalService} from './shared/animal.service';
import {HttpClientModule} from '@angular/common/http';
import {AnimalDetailForVisitorComponent} from './visitor/animals/animal-detail-for-visitor.component';
import {AppRoutingModule} from './app-routing.module';
import {MainPageComponent} from './visitor/main-page/main-page.component';
import {LoginComponent} from './admin/login/login.component';
import {AdminComponent} from './admin/main-page/admin.component';
import {CanActivateAuthGuard} from './admin/login/can-activate-auth.guard';
import {ValidationService} from './shared/validation-and-locale-messages.service';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AuthenticationService} from './admin/login/authentication.service';
import { AnimalsListComponent } from './admin/animals/animals-list.component';
import {AnimalDetailComponent} from './admin/animals/animal-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    AvailableAnimalsListComponent,
    AnimalDetailForVisitorComponent,
    MainPageComponent,
    LoginComponent,
    AdminComponent,
    AnimalsListComponent,
    AnimalDetailComponent
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
    CanActivateAuthGuard,
    ValidationService,
    AuthenticationService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
