import { CanActivateFn, GuardResult, MaybeAsync } from '@angular/router';
import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthService } from './services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const user = this.authService.getUser();
    console.log("Checking user in guard:", user); // Debug log

    if (!user) {
      console.log("No user found, redirecting to login");
      this.router.navigate(['/login']);
      return false;
    }

    const allowedRoles = route.data['roles'] as Array<string>;
    console.log("Allowed roles for this route:", allowedRoles);

    if (allowedRoles && !allowedRoles.includes(user.uloga)) {
      console.log("User role not allowed, redirecting to unauthorized");
      this.router.navigate(['/unauthorized']);
      return false;
    }

    console.log("Access granted");
    return true;
  }
}

// export const authGuard: CanActivateFn = (route, state) => {
//   return true;
// };
