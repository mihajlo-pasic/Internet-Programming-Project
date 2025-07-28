import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-unauthorized',
  standalone: true,
  imports: [RouterModule, MatButtonModule],
  template: `
    <div class="unauthorized-container">
      <h2>Neautorizovan pristup!</h2>
      <p>Nemate dozvolu da pristupite ovoj stranici.</p>
      <button mat-raised-button color="primary" routerLink="/login">Vrati se nazad</button>
    </div>
  `,
  styleUrls: ['./unauthorized.component.scss']
})
export class UnauthorizedComponent { }
