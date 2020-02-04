import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AreaSusceptivel } from 'app/shared/model/area-susceptivel.model';
import { AreaSusceptivelService } from './area-susceptivel.service';
import { AreaSusceptivelComponent } from './area-susceptivel.component';
import { AreaSusceptivelDetailComponent } from './area-susceptivel-detail.component';
import { AreaSusceptivelUpdateComponent } from './area-susceptivel-update.component';
import { AreaSusceptivelDeletePopupComponent } from './area-susceptivel-delete-dialog.component';
import { IAreaSusceptivel } from 'app/shared/model/area-susceptivel.model';

@Injectable({ providedIn: 'root' })
export class AreaSusceptivelResolve implements Resolve<IAreaSusceptivel> {
  constructor(private service: AreaSusceptivelService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAreaSusceptivel> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AreaSusceptivel>) => response.ok),
        map((areaSusceptivel: HttpResponse<AreaSusceptivel>) => areaSusceptivel.body)
      );
    }
    return of(new AreaSusceptivel());
  }
}

export const areaSusceptivelRoute: Routes = [
  {
    path: '',
    component: AreaSusceptivelComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.areaSusceptivel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AreaSusceptivelDetailComponent,
    resolve: {
      areaSusceptivel: AreaSusceptivelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.areaSusceptivel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AreaSusceptivelUpdateComponent,
    resolve: {
      areaSusceptivel: AreaSusceptivelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.areaSusceptivel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AreaSusceptivelUpdateComponent,
    resolve: {
      areaSusceptivel: AreaSusceptivelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.areaSusceptivel.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const areaSusceptivelPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AreaSusceptivelDeletePopupComponent,
    resolve: {
      areaSusceptivel: AreaSusceptivelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.areaSusceptivel.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
