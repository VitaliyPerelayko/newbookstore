import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Publisher} from '../models/publisher/publisher';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class PublisherService {

  private readonly publisherURL: string;

  constructor(
    private http: HttpClient,
    private roter: Router
  ) {
    this.publisherURL = 'http://localhost:8080/bookstore_war/publishers';
  }

  findAll(): Observable<Publisher[]> {
    return this.http.get<Publisher[]>(this.publisherURL);
  }

  findOne(id: bigint): Observable<Publisher> {
    return this.http.get<Publisher>(this.publisherURL + '/' + id);
  }

  save(publisher: Publisher) {
    return this.http.post<Publisher>(this.publisherURL, publisher);
  }

  update(publisher: Publisher, id: bigint) {
    return this.http.put<Publisher>(this.publisherURL + '/' + id, publisher);
  }

  delete(id: bigint) {
    return this.http.delete(this.publisherURL + '/' + id, {observe: 'response'});
  }

  gotoPublisherList() {
    this.roter.navigate(['/publishers']);
  }
}
