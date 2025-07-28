import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VehicleService {
  private apiUrl = 'http://localhost:8080/api/prevozna-sredstva';

  constructor(private http: HttpClient) { }

  getAllVehicles(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  getVehiclesByType(type: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/tip/${type}`);
  }

  addVehicle(vehicle: any): Observable<any> {
    return this.http.post(this.apiUrl, vehicle);
  }

  deleteVehicle(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

  uploadCSV(file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(`${this.apiUrl}/upload-csv`, formData);
  }

  getManufacturers(): Observable<any[]> {
    return this.http.get<any[]>('http://localhost:8080/api/proizvodjaci');
  }

  getFaultsByVehicleId(vehicleId: number): Observable<any[]> {
    return this.http.get<any[]>(`http://localhost:8080/api/kvarovi/prevozno-sredstvo/${vehicleId}`);
  }

  deleteFault(faultId: number): Observable<any> {
    return this.http.delete(`http://localhost:8080/api/kvarovi/${faultId}`);
  }

  getRentalsByVehicleId(vehicleId: number): Observable<any[]> {
    return this.http.get<any[]>(`http://localhost:8080/api/iznajmljivanja/prevozno-sredstvo/${vehicleId}`);
  }

  deleteRental(rentalId: number): Observable<any> {
    return this.http.delete(`http://localhost:8080/api/iznajmljivanja/${rentalId}`);
  }

  addFault(fault: any): Observable<any> {
    return this.http.post('http://localhost:8080/api/kvarovi', fault);
  }

  updateVehicleStatus(vehicleId: number, status: string): Observable<any> {
    return this.http.put(`http://localhost:8080/api/prevozna-sredstva/${vehicleId}`, { status: status });
  }



}