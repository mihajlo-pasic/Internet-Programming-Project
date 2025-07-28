import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProizvodjacDialogComponent } from './proizvodjac-dialog.component';

describe('ProizvodjacDialogComponent', () => {
  let component: ProizvodjacDialogComponent;
  let fixture: ComponentFixture<ProizvodjacDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProizvodjacDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProizvodjacDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
