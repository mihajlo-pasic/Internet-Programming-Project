import { Component, OnInit, ViewChild } from '@angular/core';
// import { UserService } from '../services/user.service';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-operater-clients',
  standalone: true,
  templateUrl: './operater-clients.component.html',
  styleUrl: './operater-clients.component.scss',
  imports: [
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    FormsModule,
    MatPaginator,
    MatTableModule
  ]
})
export class OperaterClientsComponent implements OnInit {
  clients: any[] = [];
  dataSource = new MatTableDataSource<any>();
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  searchQuery: string = '';

  displayedColumns: string[] = ['ime', 'prezime', 'email', 'brojTelefona', 'blokiran', 'akcije'];

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.loadClients();
  }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }

  loadClients(): void {
    this.userService.getAllUsers().subscribe(data => {
      this.clients = data.filter(user => user.uloga === 'KLIJENT');
      this.dataSource.data = this.clients;
    });
  }

  applyFilter(): void {
    const filteredData = this.clients.filter(client =>
      client.ime.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
      client.prezime.toLowerCase().includes(this.searchQuery.toLowerCase())
    );
    this.dataSource.data = filteredData;
  }

  toggleBlock(client: any): void {
    this.userService.toggleBlockUser(client.id).subscribe(() => {
      client.blokiran = !client.blokiran;
      this.loadClients();
    });
  }
}