import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Incidente } from 'app/shared/model/incidente.model';
import { IncidenteService } from './incidente.service';
import { IncidenteComponent } from './incidente.component';
import { IncidenteDetailComponent } from './incidente-detail.component';
import { IncidenteUpdateComponent } from './incidente-update.component';
import { IncidenteDeletePopupComponent } from './incidente-delete-dialog.component';
import { IIncidente } from 'app/shared/model/incidente.model';

@Injectable({ providedIn: 'root' })
export class IncidenteResolve implements Resolve<IIncidente> {
  constructor(private service: IncidenteService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IIncidente> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Incidente>) => response.ok),
        map((incidente: HttpResponse<Incidente>) => incidente.body)
      );
    }
    return of(new Incidente());
  }
}

export const incidenteRoute: Routes = [
  {
    path: '',
    component: IncidenteComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.incidente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: IncidenteDetailComponent,
    resolve: {
      incidente: IncidenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.incidente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: IncidenteUpdateComponent,
    resolve: {
      incidente: IncidenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.incidente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: IncidenteUpdateComponent,
    resolve: {
      incidente: IncidenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.incidente.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const incidentePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: IncidenteDeletePopupComponent,
    resolve: {
      incidente: IncidenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.incidente.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
