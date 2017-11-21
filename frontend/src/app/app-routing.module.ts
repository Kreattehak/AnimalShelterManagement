import {RouterModule} from '@angular/router';
import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AnimalDetailForVisitorComponent} from './visitor/animals/animal-detail-for-visitor.component';
import {AvailableAnimalsListComponent} from './visitor/animals/available-animals-list.component';
import {MainPageComponent} from './visitor/main-page/main-page.component';
import {LoginComponent} from './admin/login/login.component';
import {AdminComponent} from './admin/main-page/admin.component';
import {AnimalsListComponent} from './admin/animals/animals-list.component';
import {AnimalDetailComponent} from './admin/animals/animal-detail.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot([
      {path: '', component: MainPageComponent},
      {path: 'login', component: LoginComponent},
      {path: 'admin', component: AdminComponent},
      {path: 'admin/animals', component: AnimalsListComponent},
      {path: 'admin/animals/new', component: AnimalDetailComponent},
      {path: 'availableAnimals', component: AvailableAnimalsListComponent},
      {
        path: 'availableAnimals/:id/details',
        component: AnimalDetailForVisitorComponent
      },
      // {path: '**', redirectTo: '', pathMatch: 'full'},
    ]),
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {
}
