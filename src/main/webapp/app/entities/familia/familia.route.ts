import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Familia } from 'app/shared/model/familia.model';
import { FamiliaService } from './familia.service';
import { FamiliaComponent } from './familia.component';
import { FamiliaDetailComponent } from './familia-detail.component';
import { FamiliaUpdateComponent } from './familia-update.component';
import { FamiliaDeletePopupComponent } from './familia-delete-dialog.component';
import { IFamilia } from 'app/shared/model/familia.model';

@Injectable({ providedIn: 'root' })
export class FamiliaResolve implements Resolve<IFamilia> {
  constructor(private service: FamiliaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFamilia> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Familia>) => response.ok),
        map((familia: HttpResponse<Familia>) => familia.body)
      );
    }
    return of(new Familia());
  }
}

export const familiaRoute: Routes = [
  {
    path: '',
    component: FamiliaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.familia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FamiliaDetailComponent,
    resolve: {
      familia: FamiliaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.familia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FamiliaUpdateComponent,
    resolve: {
      familia: FamiliaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.familia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FamiliaUpdateComponent,
    resolve: {
      familia: FamiliaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.familia.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const familiaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FamiliaDeletePopupComponent,
    resolve: {
      familia: FamiliaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.familia.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
