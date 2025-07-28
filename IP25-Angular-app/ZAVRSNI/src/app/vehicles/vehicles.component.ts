import { Component, OnInit } from '@angular/core';
import { VehicleService } from '../services/vehicle.service';
import { MatTableDataSource } from '@angular/material/table';
import { PageEvent } from '@angular/material/paginator';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatOptionModule } from '@angular/material/core';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatDialog } from '@angular/material/dialog'; // Dodaj za dijalog
import { AddVehicleDialogComponent } from './add-vehicle-dialog/add-vehicle-dialog.component';
import { AddFaultDialogComponent } from './add-fault-dialog/add-fault-dialog.component';

@Component({
  selector: 'app-vehicles',
  templateUrl: './vehicles.component.html',
  styleUrls: ['./vehicles.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatOptionModule,
    MatTableModule,
    MatPaginatorModule,
    MatButtonModule,
    FormsModule
  ]
})
export class VehiclesComponent implements OnInit {
  displayedColumns: string[] = ['identifikator', 'tip', 'model', 'cijenaNabavke', 'akcije'];
  vehicles: any[] = []; // Svi prevozna sredstva
  filteredVehicles: any[] = []; // Filtrirana prevozna sredstva
  pagedVehicles: any[] = []; // Paginirana prevozna sredstva
  selectedType: string = '';
  searchQuery: string = '';
  pageSize = 5;
  pageIndex = 0;
  csvData: any = null;
  selectedVehicle: any = null;  // Čuvanje izabranog vozila

  displayedColumnsRental: string[] = [
    'korisnik',
    'datumPocetka',
    'datumZavrsetka',
    'lokacijaPreuzimanja',
    'lokacijaVracanja',
    'trajanje',
    'identifikacioniDokument',
    'vozackaDozvola',
  ];

  constructor(private vehicleService: VehicleService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.selectedType = 'AUTOMOBIL';
    this.setDisplayedColumns();
    this.loadVehicles();
  }

  // Postavljanje kolona na osnovu tipa vozila
  setDisplayedColumns(): void {
    switch (this.selectedType) {
      case 'AUTOMOBIL':
        this.displayedColumns = ['identifikator', 'tip', 'proizvodjac', 'model', 'cijenaNabavke', 'datumNabavke', 'opis', 'status', 'slika', 'akcije'];
        break;
      case 'BICIKL':
        this.displayedColumns = ['identifikator', 'tip', 'proizvodjac', 'model', 'cijenaNabavke', 'status', 'slika', 'autonomija', 'akcije'];
        break;
      case 'TROTINET':
        this.displayedColumns = ['identifikator', 'tip', 'proizvodjac', 'model', 'cijenaNabavke', 'status', 'slika', 'maksimalnaBrzina', 'akcije'];
        break;
    }
  }

  // Učitavanje vozila po tipu
  loadVehicles(): void {

    this.setDisplayedColumns();
    this.vehicleService.getVehiclesByType(this.selectedType).subscribe(data => {
      console.log('Podaci za odabrani tip:', data);
      this.vehicles = data;
      this.filteredVehicles = data;  // Prvi put filtrirana vozila su sva učitana vozila
      this.updatePagedVehicles();    // Ažuriranje stranica
    });
  }

  // Filtriranje samo po modelu
  applyFilter(): void {
    this.filteredVehicles = this.vehicles.filter(vehicle =>
      this.searchQuery === '' || vehicle.model.toLowerCase().startsWith(this.searchQuery.toLowerCase())
    );
    this.updatePagedVehicles();  // Ažuriraj prikaz paginacije nakon filtriranja
  }

  // Ažuriranje prikaza za paginaciju
  updatePagedVehicles(): void {
    const startIndex = this.pageIndex * this.pageSize;
    const endIndex = startIndex + this.pageSize;
    this.pagedVehicles = this.filteredVehicles.slice(startIndex, endIndex);
  }

  // Promena stranice (paginacija)
  onPageChange(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.updatePagedVehicles();
  }

  // Otvaranje dijaloga za dodavanje novog prevoznog sredstva
  openDialog(): void {
    const dialogRef = this.dialog.open(AddVehicleDialogComponent, {
      width: '400px',
      data: { tip: this.selectedType }  // Prosleđivanje tipa vozila
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.vehicleService.addVehicle(result).subscribe(() => this.loadVehicles());
      }
    });
  }


  // Brisanje prevoznog sredstva
  deleteVehicle(id: number): void {
    if (confirm('Da li ste sigurni da želite obrisati prevozno sredstvo?')) {
      this.vehicleService.deleteVehicle(id).subscribe(() => this.loadVehicles());
    }
  }

  // Učitavanje CSV fajla
  uploadCSV(event: any): void {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        const csvContent = e.target.result;
        this.csvData = this.parseCSV(csvContent)[0];  // Čuvamo samo jedan red jer je za jedno vozilo
      };
      reader.readAsText(file);
    }
  }

  // Parsiranje CSV fajla
  parseCSV(csv: string): any[] {
    const lines = csv.trim().split('\n');
    const headers = lines[0].split(',');

    return lines.slice(1).map(line => {
      const data = line.split(',');
      const obj: any = {};
      headers.forEach((header, index) => {
        obj[header.trim()] = data[index]?.trim();
      });
      return obj;
    });
  }

  // Obrada CSV fajla na klik dugmeta
  processCSV(): void {
    if (this.csvData) {
      this.openDialogWithCSVData(this.csvData);  // Otvori dijalog sa podacima
    } else {
      alert("Molimo vas da prvo izaberete CSV fajl.");
    }
  }

  // Otvaranje dijaloga sa podacima iz CSV fajla
  openDialogWithCSVData(vehicleData: any): void {
    const dialogRef = this.dialog.open(AddVehicleDialogComponent, {
      width: '400px',
      data: vehicleData  // Prosleđujemo podatke iz CSV fajla
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.vehicleService.addVehicle(result).subscribe(() => this.loadVehicles());
      }
    });
  }

  showDetails(vehicle: any): void {
    this.selectedVehicle = vehicle;
    this.loadFaults(vehicle.id);
    this.loadRentals(vehicle.id);
  }

  faults: any[] = [];
  faultPageIndex = 0;
  faultPageSize = 5;
  pagedFaults: any[] = [];

  loadFaults(vehicleId: number): void {
    this.vehicleService.getFaultsByVehicleId(vehicleId).subscribe(data => {
      this.faults = data;
      this.updatePagedFaults();
    });
  }

  updatePagedFaults(): void {
    const start = this.faultPageIndex * this.faultPageSize;
    const end = start + this.faultPageSize;
    this.pagedFaults = this.faults.slice(start, end);
  }

  deleteFault(faultId: number): void {
    if (confirm('Da li ste sigurni da želite obrisati kvar?')) {
      this.vehicleService.deleteFault(faultId).subscribe(() => {
        this.loadFaults(this.selectedVehicle.id);
        this.checkAndUpdateVehicleStatus();

      });
    }
  }

  checkAndUpdateVehicleStatus(): void {
    if (this.faults.length === 0) {
      this.vehicleService.updateVehicleStatus(this.selectedVehicle.id, 'SLOBODNO').subscribe(() => {
        this.loadVehicles();  // Osvježavanje liste vozila
      });

    } else {
      this.vehicleService.updateVehicleStatus(this.selectedVehicle.id, 'POKVARENO').subscribe(() => {
        this.loadVehicles();  // Osvježavanje liste vozila
      });

    }
  }

  onFaultPageChange(event: PageEvent): void {
    this.faultPageIndex = event.pageIndex;
    this.faultPageSize = event.pageSize;
    this.updatePagedFaults();
  }

  rentals: any[] = [];
  rentalPageIndex = 0;
  rentalPageSize = 5;
  pagedRentals: any[] = [];

  // loadRentals(vehicleId: number): void {
  //   this.vehicleService.getRentalsByVehicleId(vehicleId).subscribe(data => {
  //     this.rentals = data;
  //     this.updatePagedRentals();
  //   });
  // }

  loadRentals(vehicleId: number): void {
    this.vehicleService.getRentalsByVehicleId(vehicleId).subscribe(data => {
      console.log('Učitana iznajmljivanja:', data);  // Dodaj ovu liniju za debagovanje
      this.rentals = data;
      this.updatePagedRentals();
    }, error => {
      console.error('Greška prilikom učitavanja iznajmljivanja:', error);
    });
  }


  updatePagedRentals(): void {
    const start = this.rentalPageIndex * this.rentalPageSize;
    const end = start + this.rentalPageSize;
    this.pagedRentals = this.rentals.slice(start, end);
  }

  deleteRental(rentalId: number): void {
    if (confirm('Da li ste sigurni da želite obrisati iznajmljivanje?')) {
      this.vehicleService.deleteRental(rentalId).subscribe(() => this.loadRentals(this.selectedVehicle.id));
    }
  }

  onRentalPageChange(event: PageEvent): void {
    this.rentalPageIndex = event.pageIndex;
    this.rentalPageSize = event.pageSize;
    this.updatePagedRentals();
  }

  openAddFaultDialog(): void {
    const dialogRef = this.dialog.open(AddFaultDialogComponent, {
      width: '400px',
      data: { vehicle: this.selectedVehicle }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.vehicleService.addFault(result).subscribe(() => {
          this.loadFaults(this.selectedVehicle.id);
          this.vehicleService.updateVehicleStatus(this.selectedVehicle.id, 'POKVARENO').subscribe(() => {
            this.loadVehicles();  // Osvježavanje liste vozila
          });

        });
      }
    });
  }






}
