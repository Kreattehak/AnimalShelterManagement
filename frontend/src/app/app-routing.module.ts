import {RouterModule} from '@angular/router';
import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AvailableAnimalsListComponent} from './visitor/animals/available-animals-list.component';
import {MainPageComponent} from './visitor/main-page/main-page.component';
import {LoginComponent} from './admin/login/login.component';
import {AdminComponent} from './admin/main-page/admin.component';
import {AnimalListComponent} from './admin/animals/animal-list.component';
import {AnimalFormComponent} from './admin/animals/animal-form.component';
import {PersonFormComponent} from './admin/people/person-form.component';
import {AnimalDetailComponent} from './visitor/animals/animal-detail.component';
import {PersonListComponent} from './admin/people/person-list.component';
import {ComplexAnimalDetailComponent} from './admin/animals/complex-animal-detail.component';
import {PersonResolve} from './admin/people/person.resolve';
import {PersonAddressDetailComponent} from './admin/people/person-address-detail.component';
import {PersonAnimalDetailComponent} from './admin/people/person-animal-detail.component';
import {AddressFormComponent} from './admin/addresses/address-form.component';
import {PersonAddressesResolve} from './admin/addresses/person-addresses.resolve';
import {PersonAnimalsResolve} from './admin/people/person-animals.resolve';
import {PersonAndAnimalComponent} from './admin/people/person-and-animal.component';
import {AnimalResolve} from './admin/addresses/animal.resolve';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot([
      {path: '', component: MainPageComponent},
      {path: 'login', component: LoginComponent},
      {path: 'admin', component: AdminComponent},
      {path: 'admin/people', component: PersonListComponent},
      {path: 'admin/people/new', component: PersonFormComponent},
      {
        path: 'admin/people/:id/addresses', component: PersonAddressDetailComponent,
        resolve: {person: PersonResolve, addresses: PersonAddressesResolve}
      },
      {
        path: 'admin/people/:id/addresses/new', component: AddressFormComponent,
        resolve: {person: PersonResolve}
      },
      {
        path: 'admin/people/:id/addresses/:addressId', component: AddressFormComponent,
        resolve: {person: PersonResolve, addresses: PersonAddressesResolve}
      },
      {
        path: 'admin/people/:id/animals', component: PersonAnimalDetailComponent,
        resolve: {person: PersonResolve, animals: PersonAnimalsResolve},
      },
      { // add animal with previous owner
        path: 'admin/people/:id/animals/new', component: PersonAndAnimalComponent,
        resolve: {person: PersonResolve}
      },
      {
        path: 'admin/people/:id/animals/:animalId', component: AnimalFormComponent,
        resolve: {animal: AnimalResolve}
      },
      {
        path: 'admin/people/:id', component: PersonFormComponent,
        resolve: {person: PersonResolve}
      },
      {path: 'admin/animals', component: AnimalListComponent},
      {path: 'admin/animals/new', component: AnimalFormComponent},
      {path: 'admin/animals/:id/details', component: ComplexAnimalDetailComponent},
      {
        path: 'admin/animals/:id', component: AnimalFormComponent,
        resolve: {animal: AnimalResolve}
      },
      {path: 'availableAnimals', component: AvailableAnimalsListComponent},
      {path: 'availableAnimals/:id/details', component: AnimalDetailComponent},
      {path: '**', redirectTo: '', pathMatch: 'full'}
    ]),
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {
}
