import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RentalService {
  private apiUrl = 'http://localhost:8080/api/iznajmljivanja';

  constructor(private http: HttpClient) { }

  getAllRentals(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
}
