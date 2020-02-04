import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { Sicapuc20201SharedModule } from 'app/shared';
import {
  VistoriaComponent,
  VistoriaDetailComponent,
  VistoriaUpdateComponent,
  VistoriaDeletePopupComponent,
  VistoriaDeleteDialogComponent,
  vistoriaRoute,
  vistoriaPopupRoute
} from './';

const ENTITY_STATES = [...vistoriaRoute, ...vistoriaPopupRoute];

@NgModule({
  imports: [Sicapuc20201SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    VistoriaComponent,
    VistoriaDetailComponent,
    VistoriaUpdateComponent,
    VistoriaDeleteDialogComponent,
    VistoriaDeletePopupComponent
  ],
  entryComponents: [VistoriaComponent, VistoriaUpdateComponent, VistoriaDeleteDialogComponent, VistoriaDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Sicapuc20201VistoriaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
