import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { NivelIncidente } from 'app/shared/model/nivel-incidente.model';
import { NivelIncidenteService } from './nivel-incidente.service';
import { NivelIncidenteComponent } from './nivel-incidente.component';
import { NivelIncidenteDetailComponent } from './nivel-incidente-detail.component';
import { NivelIncidenteUpdateComponent } from './nivel-incidente-update.component';
import { NivelIncidenteDeletePopupComponent } from './nivel-incidente-delete-dialog.component';
import { INivelIncidente } from 'app/shared/model/nivel-incidente.model';

@Injectable({ providedIn: 'root' })
export class NivelIncidenteResolve implements Resolve<INivelIncidente> {
  constructor(private service: NivelIncidenteService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<INivelIncidente> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<NivelIncidente>) => response.ok),
        map((nivelIncidente: HttpResponse<NivelIncidente>) => nivelIncidente.body)
      );
    }
    return of(new NivelIncidente());
  }
}

export const nivelIncidenteRoute: Routes = [
  {
    path: '',
    component: NivelIncidenteComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.nivelIncidente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NivelIncidenteDetailComponent,
    resolve: {
      nivelIncidente: NivelIncidenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.nivelIncidente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NivelIncidenteUpdateComponent,
    resolve: {
      nivelIncidente: NivelIncidenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.nivelIncidente.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NivelIncidenteUpdateComponent,
    resolve: {
      nivelIncidente: NivelIncidenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.nivelIncidente.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const nivelIncidentePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: NivelIncidenteDeletePopupComponent,
    resolve: {
      nivelIncidente: NivelIncidenteResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.nivelIncidente.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
