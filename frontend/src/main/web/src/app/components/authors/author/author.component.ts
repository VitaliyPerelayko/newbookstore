import { Component, OnInit } from '@angular/core';
import {Author} from '../../../models/author/author';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';
import {switchMap} from 'rxjs/operators';
import {AuthorService} from '../../../services/author.service';
import {TransferDataService} from '../../../services/transfer-data.service';

@Component({
  selector: 'app-author',
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.css']
})
export class AuthorComponent implements OnInit {

  private author: Author;

  constructor(
    private route: ActivatedRoute,
    private authorService: AuthorService,
    private router: Router,
    private trData: TransferDataService
  ) {
    this.author = new Author();
  }

  deleteAuthor() {
    this.authorService.delete(this.author.id).subscribe(response => {
      if (response.ok) {
        window.alert('author has been deleted');
        this.authorService.gotoAuthorsList();
      }
    });
  }

  updateAuthor() {
    this.setAuthor();
    this.router.navigate(['/updateauthor']);
  }

  setAuthor() {
    this.trData.setData('id', this.author.id);
    this.trData.setData('name', this.author.name);
    this.trData.setData('bio', this.author.bio);
    this.trData.setData('birthDate', this.author.birthDate);
  }

  ngOnInit() {
    this.route.paramMap.pipe(
      switchMap((param: ParamMap) =>
      this.authorService.findOne(param.get('id')))
    ).subscribe(data =>
    this.author = data);
  }

}
