import {Component, OnInit} from '@angular/core';
import {PublisherService} from '../../../services/publisher.service';
import {Publisher} from '../../../models/publisher/publisher';
import {Router} from '@angular/router';

@Component({
  selector: 'app-publisher-list',
  templateUrl: './publisher-list.component.html',
  styleUrls: ['./publisher-list.component.css']
})
export class PublisherListComponent implements OnInit {

  private publishers: Publisher[];

  constructor(
    private router: Router,
    private publisherService: PublisherService
  ) {
  }

  ngOnInit() {
    this.publisherService.findAll().subscribe(data =>
      this.publishers = data);
  }

  trackById(publisher: Publisher) {
    return publisher.id;
  }

  deletePublisher(id: bigint) {
    this.publisherService.delete(id).subscribe(response => {
      if (response.ok) {
        window.alert('publisher has been deleted');
        this.publisherService.gotoPublisherList();
      }
    });
  }

  updatePublisher(id: bigint) {
    this.router.navigate(['/updatepublisher']);
  }
}
