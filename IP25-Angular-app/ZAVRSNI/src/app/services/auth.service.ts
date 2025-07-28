import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private apiUrl = 'http://localhost:8080/api/auth/login';

  constructor(private http: HttpClient, private router: Router) { }

  login(username: string, password: string): Observable<any> {
    return this.http.post<any>(this.apiUrl, { username, password }).pipe(
      tap(response => {
        if (response.success) {
          localStorage.setItem('user', JSON.stringify(response.user));
        }
      })
    );
  }

  getUser() {
    return JSON.parse(localStorage.getItem('user') || 'null');
  }

  getUserRole(): string {
    const user = this.getUser();
    return user ? user.uloga : '';
  }

  logout() {
    localStorage.removeItem('user');
    this.router.navigate(['/login']);
  }
}
