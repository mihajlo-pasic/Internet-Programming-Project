import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule, MatDialogTitle, MatDialogContent, MatDialogActions } from '@angular/material/dialog';


@Component({
  selector: 'app-proizvodjac-dialog',
  standalone: true,
  imports: [
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatDialogModule,
    MatDialogTitle,
    MatDialogContent,
    MatDialogActions,
    FormsModule,
    ReactiveFormsModule,
    MatDialogActions,
    MatDialogTitle,
    MatDialogContent
  ],
  templateUrl: './proizvodjac-dialog.component.html',
  styleUrl: './proizvodjac-dialog.component.scss'
})
export class ProizvodjacDialogComponent {
  proizvodjacForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<ProizvodjacDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
    this.proizvodjacForm = this.fb.group({
      naziv: [data?.naziv || '', Validators.required],
      drzava: [data?.drzava || '', Validators.required],
      adresa: [data?.adresa || '', Validators.required],
      kontaktTelefon: [data?.kontaktTelefon || '', Validators.required],
      kontaktFax: [data?.kontaktFax || '', Validators.required],
      kontaktEmail: [data?.kontaktEmail || '', [Validators.required, Validators.email]]
    });
  }

  save() {
    if (this.proizvodjacForm.valid) {
      this.dialogRef.close(this.proizvodjacForm.value);
    }
  }

  close() {
    this.dialogRef.close();
  }
}
