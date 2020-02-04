import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MinaOperacao } from 'app/shared/model/mina-operacao.model';
import { MinaOperacaoService } from './mina-operacao.service';
import { MinaOperacaoComponent } from './mina-operacao.component';
import { MinaOperacaoDetailComponent } from './mina-operacao-detail.component';
import { MinaOperacaoUpdateComponent } from './mina-operacao-update.component';
import { MinaOperacaoDeletePopupComponent } from './mina-operacao-delete-dialog.component';
import { IMinaOperacao } from 'app/shared/model/mina-operacao.model';

@Injectable({ providedIn: 'root' })
export class MinaOperacaoResolve implements Resolve<IMinaOperacao> {
  constructor(private service: MinaOperacaoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMinaOperacao> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<MinaOperacao>) => response.ok),
        map((minaOperacao: HttpResponse<MinaOperacao>) => minaOperacao.body)
      );
    }
    return of(new MinaOperacao());
  }
}

export const minaOperacaoRoute: Routes = [
  {
    path: '',
    component: MinaOperacaoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.minaOperacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MinaOperacaoDetailComponent,
    resolve: {
      minaOperacao: MinaOperacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.minaOperacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MinaOperacaoUpdateComponent,
    resolve: {
      minaOperacao: MinaOperacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.minaOperacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MinaOperacaoUpdateComponent,
    resolve: {
      minaOperacao: MinaOperacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.minaOperacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const minaOperacaoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: MinaOperacaoDeletePopupComponent,
    resolve: {
      minaOperacao: MinaOperacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.minaOperacao.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
