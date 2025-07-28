import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperatorAddFaultComponent } from './operator-add-fault.component';

describe('OperatorAddFaultComponent', () => {
  let component: OperatorAddFaultComponent;
  let fixture: ComponentFixture<OperatorAddFaultComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OperatorAddFaultComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OperatorAddFaultComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
