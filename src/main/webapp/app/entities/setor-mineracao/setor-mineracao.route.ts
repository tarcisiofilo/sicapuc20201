import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SetorMineracao } from 'app/shared/model/setor-mineracao.model';
import { SetorMineracaoService } from './setor-mineracao.service';
import { SetorMineracaoComponent } from './setor-mineracao.component';
import { SetorMineracaoDetailComponent } from './setor-mineracao-detail.component';
import { SetorMineracaoUpdateComponent } from './setor-mineracao-update.component';
import { SetorMineracaoDeletePopupComponent } from './setor-mineracao-delete-dialog.component';
import { ISetorMineracao } from 'app/shared/model/setor-mineracao.model';

@Injectable({ providedIn: 'root' })
export class SetorMineracaoResolve implements Resolve<ISetorMineracao> {
  constructor(private service: SetorMineracaoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISetorMineracao> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SetorMineracao>) => response.ok),
        map((setorMineracao: HttpResponse<SetorMineracao>) => setorMineracao.body)
      );
    }
    return of(new SetorMineracao());
  }
}

export const setorMineracaoRoute: Routes = [
  {
    path: '',
    component: SetorMineracaoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.setorMineracao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SetorMineracaoDetailComponent,
    resolve: {
      setorMineracao: SetorMineracaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.setorMineracao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SetorMineracaoUpdateComponent,
    resolve: {
      setorMineracao: SetorMineracaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.setorMineracao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SetorMineracaoUpdateComponent,
    resolve: {
      setorMineracao: SetorMineracaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.setorMineracao.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const setorMineracaoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SetorMineracaoDeletePopupComponent,
    resolve: {
      setorMineracao: SetorMineracaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.setorMineracao.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
