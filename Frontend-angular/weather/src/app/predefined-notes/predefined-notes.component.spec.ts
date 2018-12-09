import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PredefinedNotesComponent } from './predefined-notes.component';

describe('PredefinedNotesComponent', () => {
  let component: PredefinedNotesComponent;
  let fixture: ComponentFixture<PredefinedNotesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PredefinedNotesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PredefinedNotesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
