import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
// import { ProizvodjaciService } from '../services/proizvodjaci.service';
import { ProizvodjacDialogComponent } from './proizvodjac-dialog/proizvodjac-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { ProizvodjaciService } from '../services/proizvodjaci.service';

@Component({
  selector: 'app-manufacturers',
  standalone: true,
  imports: [FormsModule, MatTableModule, MatPaginatorModule, MatFormFieldModule, MatInputModule, MatButtonModule],
  templateUrl: './manufacturers.component.html',
  styleUrl: './manufacturers.component.scss'
})
export class ManufacturersComponent implements OnInit {
  displayedColumns: string[] = ['naziv', 'drzava', 'adresa', 'kontakt_telefon', 'kontakt_fax', 'kontakt_email', 'akcije'];
  proizvodjaci: any[] = [];
  searchQuery: string = '';
  pageSize = 5;
  pageIndex = 0;

  constructor(private dialog: MatDialog, private proizvodjaciService: ProizvodjaciService) { }

  ngOnInit() {
    this.fetchProizvodjaci();
  }

  fetchProizvodjaci() {
    this.proizvodjaciService.getProizvodjaci().subscribe(data => {
      this.proizvodjaci = data;
    });
  }

  applyFilter() {
    return this.proizvodjaci.filter(proizvodjac =>
      proizvodjac.naziv.toLowerCase().startsWith(this.searchQuery.toLowerCase())
    );
  }

  openDialog(proizvodjac?: any) {
    const dialogRef = this.dialog.open(ProizvodjacDialogComponent, {
      width: '400px',
      data: proizvodjac
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        if (proizvodjac) {
          this.proizvodjaciService.updateProizvodjac(proizvodjac.id, result).subscribe(() => this.fetchProizvodjaci());
        } else {
          this.proizvodjaciService.addProizvodjac(result).subscribe(() => this.fetchProizvodjaci());
        }
      }
    });
  }

  deleteProizvodjac(id: number) {
    if (confirm('Da li ste sigurni da želite obrisati proizvođača?')) {
      this.proizvodjaciService.deleteProizvodjac(id).subscribe(() => this.fetchProizvodjaci());
    }
  }


  onPageChange(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
  }
}
