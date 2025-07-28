import { Component, OnInit, ViewChild } from '@angular/core';
import { UserService } from '../../services/user.service';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AddEmployeeDialogComponent } from '../add-employee-dialog/add-employee-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';

@Component({
  selector: 'app-users',
  standalone: true,
  templateUrl: './users.component.html',
  styleUrl: './users.component.scss',
  imports: [
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule,
    MatTableModule,
    MatButtonModule,
    MatPaginatorModule,
    MatSortModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class UsersComponent implements OnInit {
  users: any[] = [];
  filteredUsers: any[] = [];
  searchQuery: string = '';
  selectedRole: string = 'KLIJENT'; // Defaultna vrednost u dropdown meniju
  displayedColumnsKlijenti: string[] = ['ime', 'prezime', 'uloga', 'brojLicneKarte', 'email', 'brojTelefona', 'akcije'];
  displayedColumnsZaposleni: string[] = ['ime', 'prezime', 'uloga', 'radnoMjesto', 'akcije'];
  dataSource = new MatTableDataSource<any>();  // Koristi MatTableDataSource za automatsko rukovanje paginacijom
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  pageSize = 5;
  pageIndex = 0;

  constructor(private userService: UserService, private dialog: MatDialog, private router: Router) { }

  ngOnInit(): void {
    this.loadUsers();
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;  // Povezivanje paginacije sa tabelom nakon inicijalizacije
  }

  loadUsers(): void {
    this.userService.getAllUsers().subscribe((data: any[]) => {
      this.users = data;
      this.applyFilter();
    });
  }

  // applyFilter(): void {
  //   if (this.selectedRole === 'KLIJENT') {
  //     this.filteredUsers = this.users.filter(user => user.uloga === 'KLIJENT');
  //   } else {
  //     this.filteredUsers = this.users.filter(user => user.uloga !== 'KLIJENT'); // Ovo su zaposleni
  //   }

  //   // Primeni pretragu po imenu nakon filtriranja
  //   if (this.searchQuery) {
  //     this.filteredUsers = this.filteredUsers.filter(user =>
  //       user.ime.toLowerCase().startsWith(this.searchQuery.toLowerCase())
  //     );
  //   }
  // }

  applyFilter(): void {
    if (this.selectedRole === 'KLIJENT') {
      this.dataSource.data = this.users.filter(user => user.uloga === 'KLIJENT');
    } else {
      this.dataSource.data = this.users.filter(user => user.uloga !== 'KLIJENT');
    }

    if (this.searchQuery) {
      this.dataSource.data = this.dataSource.data.filter(user =>
        user.ime.toLowerCase().startsWith(this.searchQuery.toLowerCase())
      );
    }

    this.dataSource.paginator = this.paginator;  // Ažuriranje paginacije nakon filtriranja
  }



  toggleBlock(user: any): void {
    this.userService.toggleBlockUser(user.id).subscribe(() => {
      user.blokiran = !user.blokiran; // Ažuriranje UI-ja nakon uspešne promene statusa
      this.loadUsers();
      // window.location.reload();
    });
  }

  editUser(user: any): void {
    const dialogRef = this.dialog.open(AddEmployeeDialogComponent, {
      width: '400px',
      data: user // Prosleđujemo postojeće podatke
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.userService.updateUser(user.id, result).subscribe(() => {
          this.loadUsers(); // Osvježavamo listu nakon izmena
        });
      }
    });
  }

  deleteUser(userId: number): void {
    if (confirm("Da li ste sigurni da želite obrisati ovog korisnika?")) {
      this.userService.deleteUser(userId).subscribe(() => {
        this.loadUsers(); // Osvježavanje liste korisnika
      });
    }
  }

  openAddEmployeeDialog(): void {
    const dialogRef = this.dialog.open(AddEmployeeDialogComponent, {
      width: '400px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.userService.createEmployee(result).subscribe(() => this.loadUsers());
      }
    });
  }


}
