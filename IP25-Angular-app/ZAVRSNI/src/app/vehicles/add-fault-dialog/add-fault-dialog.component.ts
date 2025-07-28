import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogModule } from '@angular/material/dialog';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormField, MatLabel } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-add-fault-dialog',
  templateUrl: './add-fault-dialog.component.html',
  styleUrls: ['./add-fault-dialog.component.scss'],
  standalone: true,
  imports: [
    MatDialogModule,
    FormsModule,
    ReactiveFormsModule,
    MatLabel,
    MatFormField,
    MatInput,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,  // Omogućava rad sa native datumima
    MatButtonModule
  ]
})
export class AddFaultDialogComponent {
  faultForm: FormGroup;

  constructor(
    public dialogRef: MatDialogRef<AddFaultDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private fb: FormBuilder
  ) {
    this.faultForm = this.fb.group({
      opis: ['', Validators.required],
      datumVrijeme: [new Date(), Validators.required]
    });
  }

  onSubmit(): void {
    if (this.faultForm.valid) {
      const faultData = { ...this.faultForm.value, prevoznoSredstvo: this.data.vehicle };
      this.dialogRef.close(faultData);
    }
  }

  onCancel(): void {
    this.dialogRef.close();
  }
}
