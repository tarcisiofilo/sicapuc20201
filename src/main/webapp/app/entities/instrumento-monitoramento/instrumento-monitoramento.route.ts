import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { InstrumentoMonitoramento } from 'app/shared/model/instrumento-monitoramento.model';
import { InstrumentoMonitoramentoService } from './instrumento-monitoramento.service';
import { InstrumentoMonitoramentoComponent } from './instrumento-monitoramento.component';
import { InstrumentoMonitoramentoDetailComponent } from './instrumento-monitoramento-detail.component';
import { InstrumentoMonitoramentoUpdateComponent } from './instrumento-monitoramento-update.component';
import { InstrumentoMonitoramentoDeletePopupComponent } from './instrumento-monitoramento-delete-dialog.component';
import { IInstrumentoMonitoramento } from 'app/shared/model/instrumento-monitoramento.model';

@Injectable({ providedIn: 'root' })
export class InstrumentoMonitoramentoResolve implements Resolve<IInstrumentoMonitoramento> {
  constructor(private service: InstrumentoMonitoramentoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInstrumentoMonitoramento> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<InstrumentoMonitoramento>) => response.ok),
        map((instrumentoMonitoramento: HttpResponse<InstrumentoMonitoramento>) => instrumentoMonitoramento.body)
      );
    }
    return of(new InstrumentoMonitoramento());
  }
}

export const instrumentoMonitoramentoRoute: Routes = [
  {
    path: '',
    component: InstrumentoMonitoramentoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.instrumentoMonitoramento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InstrumentoMonitoramentoDetailComponent,
    resolve: {
      instrumentoMonitoramento: InstrumentoMonitoramentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.instrumentoMonitoramento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InstrumentoMonitoramentoUpdateComponent,
    resolve: {
      instrumentoMonitoramento: InstrumentoMonitoramentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.instrumentoMonitoramento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InstrumentoMonitoramentoUpdateComponent,
    resolve: {
      instrumentoMonitoramento: InstrumentoMonitoramentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.instrumentoMonitoramento.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const instrumentoMonitoramentoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: InstrumentoMonitoramentoDeletePopupComponent,
    resolve: {
      instrumentoMonitoramento: InstrumentoMonitoramentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.instrumentoMonitoramento.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
