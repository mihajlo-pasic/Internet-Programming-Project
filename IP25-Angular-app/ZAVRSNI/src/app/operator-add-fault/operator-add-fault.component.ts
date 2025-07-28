import { Component, OnInit, ViewChild } from '@angular/core';
// import { VehicleService } from '../services/vehicle.service';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { AddFaultDialogComponent } from '../vehicles/add-fault-dialog/add-fault-dialog.component';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { VehicleService } from '../services/vehicle.service';


@Component({
  selector: 'app-operator-add-fault',
  templateUrl: './operator-add-fault.component.html',
  styleUrls: ['./operator-add-fault.component.scss'],
  standalone: true,
  imports: [
    MatPaginator,
    MatDialogModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormField,
    MatLabel,
    MatNativeDateModule,
    MatDatepickerModule,
    MatTableModule,
    MatFormFieldModule,
    MatInputModule
  ]
})
export class OperatorAddFaultComponent implements OnInit {
  vehicles: any[] = [];
  dataSource = new MatTableDataSource<any>();
  searchQuery: string = '';
  displayedColumns: string[] = ['identifikator', 'tip', 'status', 'akcije'];

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  faultForm: FormGroup;
  constructor(private fb: FormBuilder, private vehicleService: VehicleService, private dialog: MatDialog) {
    this.faultForm = this.fb.group({
      opis: ['', Validators.required],
      datumVrijeme: [new Date(), Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadVehicles();
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }

  onSubmit(): void {
    if (this.faultForm.valid) {
      const faultData = this.faultForm.value;
      this.vehicleService.addFault(faultData).subscribe(() => {
        this.loadVehicles();
      });
    }
  }

  onCancel(): void {
    // Očisti formu ili zatvori dijalog ako koristiš u dijalogu
    this.faultForm.reset();
  }


  loadVehicles(): void {
    this.vehicleService.getAllVehicles().subscribe((data: any[]) => {
      this.vehicles = data;
      this.applyFilter();
    });
  }

  applyFilter(): void {
    if (this.searchQuery) {
      this.dataSource.data = this.vehicles.filter(vehicle =>
        vehicle.identifikator.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    } else {
      this.dataSource.data = this.vehicles;
    }
  }

  openAddFaultDialog(vehicle: any): void {
    const dialogRef = this.dialog.open(AddFaultDialogComponent, {
      width: '400px',
      data: { vehicle: vehicle }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.vehicleService.addFault(result).subscribe(() => {
          this.vehicleService.updateVehicleStatus(vehicle.id, 'POKVARENO').subscribe(() => {
            this.loadVehicles();
          });
        });
      }
    });
  }
}
