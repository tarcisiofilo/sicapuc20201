import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { Sicapuc20201SharedModule } from 'app/shared';
import {
  AtivoComponent,
  AtivoDetailComponent,
  AtivoUpdateComponent,
  AtivoDeletePopupComponent,
  AtivoDeleteDialogComponent,
  ativoRoute,
  ativoPopupRoute
} from './';

const ENTITY_STATES = [...ativoRoute, ...ativoPopupRoute];

@NgModule({
  imports: [Sicapuc20201SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [AtivoComponent, AtivoDetailComponent, AtivoUpdateComponent, AtivoDeleteDialogComponent, AtivoDeletePopupComponent],
  entryComponents: [AtivoComponent, AtivoUpdateComponent, AtivoDeleteDialogComponent, AtivoDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Sicapuc20201AtivoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
