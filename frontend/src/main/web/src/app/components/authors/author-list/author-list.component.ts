import { Component, OnInit } from '@angular/core';
import {AuthorService} from '../../../services/author.service';
import {Author} from '../../../models/author/author';

@Component({
  selector: 'app-author-list',
  templateUrl: './author-list.component.html',
  styleUrls: ['./author-list.component.css']
})
export class AuthorListComponent implements OnInit {

  private authors: Author[];

  constructor(
    private authorService: AuthorService
  ) { }

  ngOnInit() {
    this.authorService.findAll().subscribe(data =>
      this.authors = data);
  }

  trackById(author: Author) {
    return author.id;
  }

}
