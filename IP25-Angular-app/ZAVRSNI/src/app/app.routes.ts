import { Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { LoginGuard } from './login.guard';
import { UnauthorizedComponent } from './unauthorized/unauthorized.component';
import { ManufacturersComponent } from './manufacturers/manufacturers.component';
import { AuthGuard } from './auth.guard';
import { DashboardComponent } from './admin/dashboard/dashboard.component';
import { UsersComponent } from './users/users/users.component';
import { VehiclesComponent } from './vehicles/vehicles.component';
import { OperatorDashboardComponent } from './operator-dashboard/operator-dashboard.component';
import { RentalsComponent } from './rentals/rentals.component';
import { OperaterClientsComponent } from './operater-clients/operater-clients.component';
import { OperatorAddFaultComponent } from './operator-add-fault/operator-add-fault.component';

export const routes: Routes = [
    {
        path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard], data: { roles: ['ADMINISTRATOR'] },
        children: [
            { path: 'manufacturers', component: ManufacturersComponent, data: { roles: ['ADMINISTRATOR'] } },
            { path: 'users', component: UsersComponent, data: { roles: ['ADMINISTRATOR'] } },
            { path: 'vehicles', component: VehiclesComponent, data: { roles: ['ADMINISTRATOR'] } },
        ]
    },
    {
        path: 'operator-dashboard', component: OperatorDashboardComponent, canActivate: [AuthGuard], data: { roles: ['OPERATER'] },
        children: [
            { path: 'rentals', component: RentalsComponent, data: { roles: ['OPERATER'] } },
            { path: 'clients', component: OperaterClientsComponent, data: { roles: ['OPERATER'] } },
            { path: 'add-fault', component: OperatorAddFaultComponent, data: { roles: ['OPERATER'] } },
            { path: 'vehicles-map', component: OperaterClientsComponent, data: { roles: ['OPERATER'] } }, //TODO PROMIJENITI!! kad napravim
            // Dodaj ostale stranice za operatera ovdje kasnije
        ]
    },
    {
        path: 'manufacturers', component: ManufacturersComponent, canActivate: [AuthGuard], data: { roles: ['ADMINISTRATOR'] }
    },
    {
        path: 'unauthorized', component: UnauthorizedComponent
    },
    {
        path: 'login', component: LoginComponent, canActivate: [LoginGuard]
    },
    {
        path: '', redirectTo: 'login', pathMatch: 'full'
    },
];
