import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {BookListComponent} from "./components/books/book-list/book-list.component";
import {BookFormComponent} from "./components/books/book-form/book-form.component";
import {BookComponent} from "./components/books/book/book.component";
import {AuthorListComponent} from "./components/authors/author-list/author-list.component";
import {AuthorFormComponent} from "./components/authors/author-form/author-form.component";
import {UpdateBookFormComponent} from "./components/books/update-book-form/update-book-form.component";
import {UpdateAuthorFormComponent} from "./components/authors/update-author-form/update-author-form.component";
import {AuthorComponent} from "./components/authors/author/author.component";
import {PublisherListComponent} from "./components/publishers/publisher-list/publisher-list.component";
import {PublisherFormComponent} from "./components/publishers/publisher-form/publisher-form.component";
import {UpdatePublisherFormComponent} from "./components/publishers/update-publisher-form/update-publisher-form.component";

const routes: Routes = [
  // books
  {path: 'books', component: BookListComponent},
  {path: 'addbook', component: BookFormComponent},
  {path: 'books/books/:id', component: BookComponent},
  {path: 'updatebook', component: UpdateBookFormComponent},
  // authors
  {path: 'authors', component: AuthorListComponent},
  {path: 'addauthor', component: AuthorFormComponent},
  {path: 'authors/author/:id', component: AuthorComponent},
  {path: 'updateauthor', component: UpdateAuthorFormComponent},
  // publishers
  {path: 'publishers', component: PublisherListComponent},
  {path: 'addpublisher', component: PublisherFormComponent},
  {path: 'updatepublisher', component: UpdatePublisherFormComponent},
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
