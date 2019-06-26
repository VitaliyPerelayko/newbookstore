import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Author} from '../models/author/author';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthorService {

  private readonly authorURL: string;

  constructor(
    private http: HttpClient,
    private route: Router
    ) {
    this.authorURL = 'http://localhost:8080/bookstore/authors';
  }

  findAll(): Observable<Author[]> {
    return this.http.get<Author[]>(this.authorURL);
  }

  findOne(id): Observable<Author> {
    return this.http.get<Author>(this.authorURL + '/' + id);
  }

  save(author: Author) {
    return this.http.post<Author>(this.authorURL, author);
  }

  update(author: Author, id: bigint) {
    return this.http.put<Author>(this.authorURL + '/' + id, author);
  }

  delete(id: bigint) {
    return  this.http.delete(this.authorURL + '/' + id, {observe: 'response'});
  }

  gotoAuthorsList() {
    this.route.navigate(['/authors']);
  }
}
