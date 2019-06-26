import { Component } from '@angular/core';
import {TransferDataService} from '../../../services/transfer-data.service';
import {Author} from '../../../models/author/author';
import {AuthorService} from '../../../services/author.service';

@Component({
  selector: 'app-update-author-form',
  templateUrl: './update-author-form.component.html',
  styleUrls: ['./update-author-form.component.css']
})
export class UpdateAuthorFormComponent {

  author = this.trData.getData() as Author;

  constructor(
    private trData: TransferDataService,
    private authorService: AuthorService
  ) { }

  onSubmit() {
    this.authorService.update(this.author, this.author.id).subscribe(() => this.authorService.gotoAuthorsList());
  }
}
