import { Component } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  username: string = '';
  password: string = '';

  constructor(private authService: AuthService, private router: Router) { }

  login() {
    this.authService.login(this.username, this.password).subscribe(
      response => {
        if (response.success) {
          console.log('Uspješna prijava:', response);
          const user = response.user;

          if (user.uloga === 'ADMINISTRATOR') {
            this.router.navigate(['/dashboard']);
          }
          else if (user.uloga === 'OPERATER') {
            this.router.navigate(['/operator-dashboard']);
          }
          // else if (user.uloga === 'MENADZER') {
          //   this.router.navigate(['/menadzer-dashboard']);
          // }
          else {
            this.router.navigate(['/login']);
          }
        }
      },
      error => {
        console.error('Greška prilikom prijave:', error);
        alert('Neispravno korisničko ime ili lozinka!');
      }
    );
  }
}
