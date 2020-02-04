import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Vistoria } from 'app/shared/model/vistoria.model';
import { VistoriaService } from './vistoria.service';
import { VistoriaComponent } from './vistoria.component';
import { VistoriaDetailComponent } from './vistoria-detail.component';
import { VistoriaUpdateComponent } from './vistoria-update.component';
import { VistoriaDeletePopupComponent } from './vistoria-delete-dialog.component';
import { IVistoria } from 'app/shared/model/vistoria.model';

@Injectable({ providedIn: 'root' })
export class VistoriaResolve implements Resolve<IVistoria> {
  constructor(private service: VistoriaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IVistoria> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Vistoria>) => response.ok),
        map((vistoria: HttpResponse<Vistoria>) => vistoria.body)
      );
    }
    return of(new Vistoria());
  }
}

export const vistoriaRoute: Routes = [
  {
    path: '',
    component: VistoriaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.vistoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: VistoriaDetailComponent,
    resolve: {
      vistoria: VistoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.vistoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: VistoriaUpdateComponent,
    resolve: {
      vistoria: VistoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.vistoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: VistoriaUpdateComponent,
    resolve: {
      vistoria: VistoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.vistoria.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const vistoriaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: VistoriaDeletePopupComponent,
    resolve: {
      vistoria: VistoriaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.vistoria.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
