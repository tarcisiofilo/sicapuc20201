import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Ativo } from 'app/shared/model/ativo.model';
import { AtivoService } from './ativo.service';
import { AtivoComponent } from './ativo.component';
import { AtivoDetailComponent } from './ativo-detail.component';
import { AtivoUpdateComponent } from './ativo-update.component';
import { AtivoDeletePopupComponent } from './ativo-delete-dialog.component';
import { IAtivo } from 'app/shared/model/ativo.model';

@Injectable({ providedIn: 'root' })
export class AtivoResolve implements Resolve<IAtivo> {
  constructor(private service: AtivoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAtivo> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Ativo>) => response.ok),
        map((ativo: HttpResponse<Ativo>) => ativo.body)
      );
    }
    return of(new Ativo());
  }
}

export const ativoRoute: Routes = [
  {
    path: '',
    component: AtivoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.ativo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AtivoDetailComponent,
    resolve: {
      ativo: AtivoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.ativo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AtivoUpdateComponent,
    resolve: {
      ativo: AtivoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.ativo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AtivoUpdateComponent,
    resolve: {
      ativo: AtivoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.ativo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const ativoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AtivoDeletePopupComponent,
    resolve: {
      ativo: AtivoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.ativo.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
