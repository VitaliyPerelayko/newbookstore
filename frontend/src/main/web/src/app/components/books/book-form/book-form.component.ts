import {Component} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {BookServiceService} from '../../../services/book-service.service';
import {BookRequest} from '../../../models/book/book-request';
import {FormArray, FormBuilder} from '@angular/forms';

@Component({
  selector: 'app-book-form',
  templateUrl: './book-form.component.html',
  styleUrls: ['./book-form.component.css']
})
export class BookFormComponent {

  bookRequest: BookRequest;

  form = this.fb.group({
    nameControl: [''],
    // , Validators.required]
    descriptionControl: [''],
    authorControl: this.fb.array([
      this.fb.control('')
    ]),
    // Validators.required, Validators.min(1)],
    publishDateControl: [''],
    // Validators.required],
    publisherIdControl: [''],
    // Validators.required, Validators.min(1)],
    priceControl: [''],
    // Validators.required, Validators.min(0)],
    categoryControl: ['']
    // , Validators.required, Validators.min(1)],
  });


  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private bookService: BookServiceService,
  ) {
  }

  onSubmit() {
    this.setBook();
    this.bookService.save(this.bookRequest).subscribe(() => this.bookService.gotoBooksList());
  }

  setBook() {
    this.bookRequest.name = this.form.controls.nameControl.value as string;
    this.bookRequest.description = this.form.controls.descriptionControl.value as string;
    this.bookRequest.publishDate = this.form.controls.publishDateControl.value as string;
    this.bookRequest.publisherId = this.form.controls.publisherIdControl.value as bigint;
    this.bookRequest.category = this.form.controls.categoryControl.value as number;
    this.bookRequest.price = this.form.controls.priceControl.value as number;
  }

  get authors(): FormArray {
    return this.form.get('authorControl') as FormArray;
  }

  addAuthor(i) {
    const id = this.authors.at(i).value as bigint;
    if (!this.bookRequest.authorsId.includes(id)) {
      this.bookRequest.authorsId.push(id);
      this.authors.push(this.fb.control(''));
    }
  }
}
