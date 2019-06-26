import {Component} from '@angular/core';
import {Author} from '../../../models/author/author';
import {AuthorService} from '../../../services/author.service';

@Component({
  selector: 'app-author-form',
  templateUrl: './author-form.component.html',
  styleUrls: ['./author-form.component.css']
})
export class AuthorFormComponent {

  private author: Author;

  constructor(
    private authorService: AuthorService,
  ) {
    this.author = new Author();
  }

  onSubmit() {
    this.authorService.save(this.author).subscribe(() => this.authorService.gotoAuthorsList());
  }
}
