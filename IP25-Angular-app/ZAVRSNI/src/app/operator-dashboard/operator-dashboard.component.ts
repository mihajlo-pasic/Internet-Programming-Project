import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { AuthService } from '../services/auth.service';
// import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-operator-dashboard',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './operator-dashboard.component.html',
  styleUrls: ['./operator-dashboard.component.scss']
})
export class OperatorDashboardComponent {
  constructor(private authService: AuthService) { }

  logout() {
    this.authService.logout();
  }
}