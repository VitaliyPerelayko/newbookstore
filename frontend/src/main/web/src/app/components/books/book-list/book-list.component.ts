import {Component, OnInit} from '@angular/core';
import {BookServiceService} from '../../../services/book-service.service';
import {BookShort} from '../../../models/book/book-short';

@Component({
  selector: 'app-book-list',
  templateUrl: './book-list.component.html',
  styleUrls: ['./book-list.component.css']
})
export class BookListComponent implements OnInit {

  books: BookShort[];

  constructor(
    private bookService: BookServiceService
  ) {
  }

  ngOnInit() {
    this.bookService.findAll().subscribe(data => {
      this.books = data;
    });
  }

  trackById(book: BookShort) {
    return book.id;
  }

}
