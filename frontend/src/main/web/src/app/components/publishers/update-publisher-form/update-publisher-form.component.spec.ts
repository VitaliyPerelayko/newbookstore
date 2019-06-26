import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdatePublisherFormComponent } from './update-publisher-form.component';

describe('UpdatePublisherFormComponent', () => {
  let component: UpdatePublisherFormComponent;
  let fixture: ComponentFixture<UpdatePublisherFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UpdatePublisherFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdatePublisherFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
