import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {AnimalListComponent} from './animal/animal-list.component';
import {AnimalService} from './animal/animal.service';
import {HttpClientModule} from '@angular/common/http';
import {AnimalDetailComponent} from './animal/animal-detail.component';
import {AppRoutingModule} from './app-routing.module';
import { MainPageComponent } from './main-page/main-page.component';

@NgModule({
  declarations: [
    AppComponent,
    AnimalListComponent,
    AnimalDetailComponent,
    MainPageComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
  ],
  providers: [AnimalService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
