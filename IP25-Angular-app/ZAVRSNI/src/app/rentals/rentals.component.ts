import { Component, Inject, OnInit } from '@angular/core';
import { RentalService } from '../services/rental.service';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { PageEvent } from '@angular/material/paginator';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-rentals',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatPaginatorModule],
  templateUrl: './rentals.component.html',
  styleUrls: ['./rentals.component.scss']
})
export class RentalsComponent implements OnInit {
  rentals: any[] = [];
  pagedRentals: any[] = [];
  pageSize = 5;
  pageIndex = 0;

  displayedColumns: string[] = [
    'identifikator', 'korisnik', 'datumPocetka', 'datumZavrsetka', 'lokacijaPreuzimanja',
    'lokacijaVracanja', 'trajanje', 'identifikacioniDokument', 'vozackaDozvola'
  ];

  constructor(private rentalService: RentalService) { }

  ngOnInit(): void {
    this.loadRentals();
  }

  loadRentals(): void {
    this.rentalService.getAllRentals().subscribe(data => {
      this.rentals = data.sort((a, b) => new Date(b.datumPocetka).getTime() - new Date(a.datumPocetka).getTime());
      this.updatePagedRentals();
    });
  }

  updatePagedRentals(): void {
    const start = this.pageIndex * this.pageSize;
    const end = start + this.pageSize;
    this.pagedRentals = this.rentals.slice(start, end);
  }

  onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.updatePagedRentals();
  }
}