import { Component } from '@angular/core';
import {Publisher} from '../../../models/publisher/publisher';
import {PublisherService} from '../../../services/publisher.service';

@Component({
  selector: 'app-publisher-form',
  templateUrl: './publisher-form.component.html',
  styleUrls: ['./publisher-form.component.css']
})
export class PublisherFormComponent {

  private publisher: Publisher;

  constructor(
    private publisherService: PublisherService
  ) {
    this.publisher = new Publisher();
  }

  onSubmit() {
    this.publisherService.save(this.publisher).subscribe(() => this.publisherService.gotoPublisherList());
  }
}
