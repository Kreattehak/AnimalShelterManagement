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
import {PersonDetailComponent} from './admin/people/person-detail.component';
import {ComplexAnimalDetailComponent} from './admin/animals/complex-animal-detail.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot([
      {path: '', component: MainPageComponent},
      {path: 'login', component: LoginComponent},
      {path: 'admin', component: AdminComponent},
      {path: 'admin/people', component: PersonListComponent},
      {path: 'admin/people/new', component: PersonFormComponent},
      {path: 'admin/people/:id/details', component: PersonDetailComponent},
      {path: 'admin/people/:id', component: PersonFormComponent},
      {path: 'admin/animals', component: AnimalListComponent},
      {path: 'admin/animals/new', component: AnimalFormComponent},
      {path: 'admin/animals/:id/details', component: ComplexAnimalDetailComponent},
      {path: 'admin/animals/:id', component: AnimalFormComponent},
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
