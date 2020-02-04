import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MedicaoInstrumento } from 'app/shared/model/medicao-instrumento.model';
import { MedicaoInstrumentoService } from './medicao-instrumento.service';
import { MedicaoInstrumentoComponent } from './medicao-instrumento.component';
import { MedicaoInstrumentoDetailComponent } from './medicao-instrumento-detail.component';
import { MedicaoInstrumentoUpdateComponent } from './medicao-instrumento-update.component';
import { MedicaoInstrumentoDeletePopupComponent } from './medicao-instrumento-delete-dialog.component';
import { IMedicaoInstrumento } from 'app/shared/model/medicao-instrumento.model';

@Injectable({ providedIn: 'root' })
export class MedicaoInstrumentoResolve implements Resolve<IMedicaoInstrumento> {
  constructor(private service: MedicaoInstrumentoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMedicaoInstrumento> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<MedicaoInstrumento>) => response.ok),
        map((medicaoInstrumento: HttpResponse<MedicaoInstrumento>) => medicaoInstrumento.body)
      );
    }
    return of(new MedicaoInstrumento());
  }
}

export const medicaoInstrumentoRoute: Routes = [
  {
    path: '',
    component: MedicaoInstrumentoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.medicaoInstrumento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MedicaoInstrumentoDetailComponent,
    resolve: {
      medicaoInstrumento: MedicaoInstrumentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.medicaoInstrumento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MedicaoInstrumentoUpdateComponent,
    resolve: {
      medicaoInstrumento: MedicaoInstrumentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.medicaoInstrumento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MedicaoInstrumentoUpdateComponent,
    resolve: {
      medicaoInstrumento: MedicaoInstrumentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.medicaoInstrumento.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const medicaoInstrumentoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: MedicaoInstrumentoDeletePopupComponent,
    resolve: {
      medicaoInstrumento: MedicaoInstrumentoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.medicaoInstrumento.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
