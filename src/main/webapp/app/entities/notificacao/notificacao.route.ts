import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Notificacao } from 'app/shared/model/notificacao.model';
import { NotificacaoService } from './notificacao.service';
import { NotificacaoComponent } from './notificacao.component';
import { NotificacaoDetailComponent } from './notificacao-detail.component';
import { NotificacaoUpdateComponent } from './notificacao-update.component';
import { NotificacaoDeletePopupComponent } from './notificacao-delete-dialog.component';
import { INotificacao } from 'app/shared/model/notificacao.model';

@Injectable({ providedIn: 'root' })
export class NotificacaoResolve implements Resolve<INotificacao> {
  constructor(private service: NotificacaoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<INotificacao> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Notificacao>) => response.ok),
        map((notificacao: HttpResponse<Notificacao>) => notificacao.body)
      );
    }
    return of(new Notificacao());
  }
}

export const notificacaoRoute: Routes = [
  {
    path: '',
    component: NotificacaoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.notificacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: NotificacaoDetailComponent,
    resolve: {
      notificacao: NotificacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.notificacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: NotificacaoUpdateComponent,
    resolve: {
      notificacao: NotificacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.notificacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: NotificacaoUpdateComponent,
    resolve: {
      notificacao: NotificacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.notificacao.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const notificacaoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: NotificacaoDeletePopupComponent,
    resolve: {
      notificacao: NotificacaoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.notificacao.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
