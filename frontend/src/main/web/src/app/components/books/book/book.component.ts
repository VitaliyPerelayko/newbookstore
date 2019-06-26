import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {Book} from '../../../models/book/book';
import {BookServiceService} from '../../../services/book-service.service';
import {switchMap} from 'rxjs/operators';
import {TransferDataService} from '../../../services/transfer-data.service';

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent implements OnInit {

  private book: Book;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private bookService: BookServiceService,
    private trData: TransferDataService
  ) {
    this.book = new Book();
  }

  deleteBook() {
    this.bookService.delete(this.book.id).subscribe(response => {
      if (response.ok) {
        window.alert('book has been deleted');
        this.bookService.gotoBooksList();
      }
    });
  }

  updateBook() {
    this.setRequestBook();
    this.router.navigate(['/updatebook']);
  }

  setRequestBook() {
    this.trData.setData('id', this.book.id);
    this.trData.setData('name', this.book.name);
    this.trData.setData('authorsId', this.book.authors.map(value => value.id));
    this.trData.setData('publisherId', this.book.publisher.id);
    this.trData.setData('publishDate', this.book.publishDate);
    this.trData.setData('description', this.book.description);
    this.trData.setData('category', this.book.category.valueOf());
    this.trData.setData('price', this.book.price);
  }

  ngOnInit() {
    this.route.paramMap.pipe(
      switchMap((params: ParamMap) =>
        this.bookService.findOne(params.get('id')))
    ).subscribe(data =>
        this.book = data);
  }
}
