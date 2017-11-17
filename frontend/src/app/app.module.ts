import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {AnimalListComponent} from './animal/animal-list.component';
import {AnimalService} from './animal/animal.service';
import {HttpClientModule} from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    AnimalListComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
  ],
  providers: [AnimalService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
