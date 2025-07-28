import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddFaultDialogComponent } from './add-fault-dialog.component';

describe('AddFaultDialogComponent', () => {
  let component: AddFaultDialogComponent;
  let fixture: ComponentFixture<AddFaultDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddFaultDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddFaultDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
