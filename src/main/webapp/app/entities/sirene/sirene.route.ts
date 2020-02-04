import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Sirene } from 'app/shared/model/sirene.model';
import { SireneService } from './sirene.service';
import { SireneComponent } from './sirene.component';
import { SireneDetailComponent } from './sirene-detail.component';
import { SireneUpdateComponent } from './sirene-update.component';
import { SireneDeletePopupComponent } from './sirene-delete-dialog.component';
import { ISirene } from 'app/shared/model/sirene.model';

@Injectable({ providedIn: 'root' })
export class SireneResolve implements Resolve<ISirene> {
  constructor(private service: SireneService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISirene> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Sirene>) => response.ok),
        map((sirene: HttpResponse<Sirene>) => sirene.body)
      );
    }
    return of(new Sirene());
  }
}

export const sireneRoute: Routes = [
  {
    path: '',
    component: SireneComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.sirene.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SireneDetailComponent,
    resolve: {
      sirene: SireneResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.sirene.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SireneUpdateComponent,
    resolve: {
      sirene: SireneResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.sirene.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SireneUpdateComponent,
    resolve: {
      sirene: SireneResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.sirene.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const sirenePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SireneDeletePopupComponent,
    resolve: {
      sirene: SireneResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'sicapuc20201App.sirene.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
