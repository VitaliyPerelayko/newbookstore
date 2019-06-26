import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {BookListComponent} from "./components/books/book-list/book-list.component";
import {BookComponent} from "./components/books/book/book.component";
import {BookFormComponent} from "./components/books/book-form/book-form.component";
import {UpdateBookFormComponent} from "./components/books/update-book-form/update-book-form.component";
import {AuthorListComponent} from "./components/authors/author-list/author-list.component";
import {AuthorComponent} from "./components/authors/author/author.component";
import {AuthorFormComponent} from "./components/authors/author-form/author-form.component";
import {UpdateAuthorFormComponent} from "./components/authors/update-author-form/update-author-form.component";
import {PublisherListComponent} from "./components/publishers/publisher-list/publisher-list.component";
import {PublisherFormComponent} from "./components/publishers/publisher-form/publisher-form.component";
import {UpdatePublisherFormComponent} from "./components/publishers/update-publisher-form/update-publisher-form.component";
import {HttpClientModule} from "@angular/common/http";
import {RouterModule} from "@angular/router";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    BookListComponent,
    BookComponent,
    BookFormComponent,
    UpdateBookFormComponent,
    AuthorListComponent,
    AuthorComponent,
    AuthorFormComponent,
    UpdateAuthorFormComponent,
    PublisherListComponent,
    PublisherFormComponent,
    UpdatePublisherFormComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    RouterModule.forRoot([
      {path: 'books/:id', component: BookComponent},
      {path: 'authors/:id', component: AuthorComponent},
    ]),
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
