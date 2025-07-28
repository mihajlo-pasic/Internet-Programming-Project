import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from './services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class LoginGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) { }

  canActivate(): boolean {
    const user = this.authService.getUser();

    if (user) {
      // Ako je korisnik već prijavljen, preusmeravamo ga na dashboard
      // this.router.navigate(['/dashboard']);
      // return false;

      if (user.uloga === 'ADMINISTRATOR') {
        this.router.navigate(['/dashboard']);
        return false;
      }
      else if (user.uloga === 'OPERATER') {
        this.router.navigate(['/operator-dashboard']);
        return false;
      }
      // else if (user.uloga === 'MENADZER') {
      //   this.router.navigate(['/menadzer-dashboard']);
      //   return false;
      // }
    }



    // Ako korisnik nije prijavljen, dozvoljavamo pristup login stranici
    return true;
  }
}
