import {RouterModule} from '@angular/router';
import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AnimalDetailComponent} from './animal/animal-detail.component';
import {AnimalListComponent} from './animal/animal-list.component';
import {MainPageComponent} from './main-page/main-page.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot([
      {path: '', component: MainPageComponent},
      {path: 'animals', component: AnimalListComponent},
      {
        path: 'animals/:id/details',
        component: AnimalDetailComponent
      },
      {path: '**', redirectTo: '', pathMatch: 'full'},
    ]),
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {
}
