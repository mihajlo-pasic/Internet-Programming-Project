import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OperaterClientsComponent } from './operater-clients.component';

describe('OperaterClientsComponent', () => {
  let component: OperaterClientsComponent;
  let fixture: ComponentFixture<OperaterClientsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OperaterClientsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OperaterClientsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
