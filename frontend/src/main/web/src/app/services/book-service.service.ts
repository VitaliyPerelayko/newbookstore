import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Book} from '../models/book/book';
import {BookRequest} from '../models/book/book-request';
import {Router} from '@angular/router';
import {BookShort} from '../models/book/book-short';

@Injectable({
  providedIn: 'root'
})
export class BookServiceService {

  private readonly booksURL: string;

  constructor(
    private http: HttpClient,
    private router: Router
) {
    this.booksURL = 'http://localhost:8080/bookstore/books';
  }

  public findAll(): Observable<BookShort[]> {
    return this.http.get<Book[]>(this.booksURL);
  }

  public findOne(id): Observable<Book> {
    return this.http.get<Book>(this.booksURL + '/' + id);
  }

  public save(book: BookRequest) {
    return this.http.post<Book>(this.booksURL, book);
  }

  public update(book: BookRequest, id: number) {
    return this.http.put<Book>(this.booksURL + '/' + id, book);
  }

  public delete(id: bigint) {
    return  this.http.delete(this.booksURL + '/' + id, {observe: 'response'});
  }

  gotoBooksList() {
    this.router.navigate(['/books']);
  }
}
