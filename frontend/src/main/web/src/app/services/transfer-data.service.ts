import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TransferDataService {

  private data = {};

  setData(option, value) {
    this.data[option] = value;
  }

  getData() {
    return this.data;
  }
}
