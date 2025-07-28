import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProizvodjaciService {
  private apiUrl = 'http://localhost:8080/api/proizvodjaci';

  constructor(private http: HttpClient) { }

  getProizvodjaci(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  addProizvodjac(proizvodjac: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, proizvodjac);
  }

  updateProizvodjac(id: number, proizvodjac: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, proizvodjac);
  }

  deleteProizvodjac(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
