import {Component} from '@angular/core';
import {FormArray, FormBuilder} from '@angular/forms';
import {BookServiceService} from '../../../services/book-service.service';
import {TransferDataService} from '../../../services/transfer-data.service';
import {BookRequest} from '../../../models/book/book-request';

@Component({
  selector: 'app-update-book-form',
  templateUrl: './update-book-form.component.html',
  styleUrls: ['./update-book-form.component.css']
})
export class UpdateBookFormComponent {

  bookRequest = this.trData.getData() as BookRequest;

  form = this.fb.group({
    nameControl: [this.bookRequest.name],
    // , Validators.required]
    descriptionControl: [this.bookRequest.description],
    authorControl: this.fb.array(this.bookRequest.authorsId),
    // Validators.required, Validators.min(1)],
    publishDateControl: [this.bookRequest.publishDate],
    // Validators.required],
    publisherIdControl: [this.bookRequest.publisherId],
    // Validators.required, Validators.min(1)],
    priceControl: [this.bookRequest.price],
    // Validators.required, Validators.min(0)],
    categoryControl: [this.bookRequest.category]
    // , Validators.required, Validators.min(1)],
  });

  constructor(
    private fb: FormBuilder,
    private bookService: BookServiceService,
    private trData: TransferDataService
  ) {
  }

  onSubmit() {
    this.setBook();
    this.bookService.update(this.bookRequest, this.bookRequest.id).subscribe(() => this.bookService.gotoBooksList());
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

  deleteAuthor(i) {
    this.bookRequest.authorsId.splice(i, 1);
  }


}
