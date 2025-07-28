import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogActions, MatDialogContent, MatDialogRef } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { ReactiveFormsModule } from '@angular/forms';
import { MatOption } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
import { MatDialogModule } from '@angular/material/dialog';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Inject } from '@angular/core';
import { VehicleService } from '../../services/vehicle.service';  // Importuj servis

@Component({
  selector: 'app-add-vehicle-dialog',
  templateUrl: './add-vehicle-dialog.component.html',
  styleUrls: ['./add-vehicle-dialog.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    ReactiveFormsModule,
    MatDialogContent,
    MatDialogActions,
    MatOption,
    MatSelectModule,
    MatDialogModule,
    MatSelectModule
  ]
})
export class AddVehicleDialogComponent {
  vehicleForm: FormGroup;
  manufacturers: any[] = [];

  constructor(
    private fb: FormBuilder,
    private vehicleService: VehicleService,
    public dialogRef: MatDialogRef<AddVehicleDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    // this.vehicleForm = this.fb.group({
    //   identifikator: ['', Validators.required],
    //   tip: ['', Validators.required],
    //   model: ['', Validators.required],
    //   cijenaNabavke: ['', [Validators.required, Validators.min(0)]]
    // });
    // this.vehicleForm = this.createForm(data.tip);
    this.vehicleForm = this.createForm(data?.tip || 'AUTOMOBIL');  // Postavi tip iz CSV-a ili default na AUTOMOBIL

    this.loadManufacturers();  // Učitaj proizvođače

    if (data) {
      this.vehicleForm.patchValue(data);  // Popuni formu sa CSV podacima
    }
  }

  createForm(tip: string): FormGroup {
    const baseForm = {
      identifikator: ['', Validators.required],
      tip: [tip, Validators.required],
      proizvodjacId: ['', Validators.required],
      model: ['', Validators.required],
      cijenaNabavke: ['', [Validators.required, Validators.min(0)]],
      status: ['SLOBODNO', Validators.required],
      slika: ['', Validators.required]
    };

    this.loadManufacturers();

    if (tip === 'AUTOMOBIL') {
      return this.fb.group({
        ...baseForm,
        datumNabavke: ['', Validators.required],
        opis: ['', Validators.required]
      });
    } else if (tip === 'BICIKL') {
      return this.fb.group({
        ...baseForm,
        autonomija: ['', [Validators.required, Validators.min(0)]]
      });
    } else if (tip === 'TROTINET') {
      return this.fb.group({
        ...baseForm,
        maksimalnaBrzina: ['', [Validators.required, Validators.min(0)]]
      });
    } else {
      return this.fb.group(baseForm);
    }
  }

  loadManufacturers(): void {
    this.vehicleService.getManufacturers().subscribe(data => {
      this.manufacturers = data;

      // Ako je proizvođač naveden po nazivu u CSV-u, pokušaj da ga automatski selektuješ
      if (this.data?.proizvodjac) {
        const selectedManufacturer = this.manufacturers.find(m => m.naziv.toLowerCase() === this.data.proizvodjac.toLowerCase());
        if (selectedManufacturer) {
          this.vehicleForm.patchValue({ proizvodjacId: selectedManufacturer.id });
        }
      }
    });
  }

  // save(): void {
  //   if (this.vehicleForm.valid) {
  //     this.dialogRef.close(this.vehicleForm.value);
  //   }
  // }

  save(): void {
    if (this.vehicleForm.valid) {
      const formValue = this.vehicleForm.value;

      // Pronađi objekat proizvođača na osnovu ID-ja
      const selectedManufacturer = this.manufacturers.find(m => m.id === formValue.proizvodjacId);

      // Zamenite proizvodjacId sa objektom proizvodjac
      const vehicleData = {
        ...formValue,
        proizvodjac: selectedManufacturer  // Backend očekuje objekat, ne ID
      };

      delete vehicleData.proizvodjacId;  // Ukloni ID jer sada imamo objekat

      this.dialogRef.close(vehicleData);
    }
  }



  close(): void {
    this.dialogRef.close();
  }
}
