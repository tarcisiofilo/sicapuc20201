import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { Sicapuc20201SharedModule } from 'app/shared';
import {
  MedicaoInstrumentoComponent,
  MedicaoInstrumentoDetailComponent,
  MedicaoInstrumentoUpdateComponent,
  MedicaoInstrumentoDeletePopupComponent,
  MedicaoInstrumentoDeleteDialogComponent,
  medicaoInstrumentoRoute,
  medicaoInstrumentoPopupRoute
} from './';

const ENTITY_STATES = [...medicaoInstrumentoRoute, ...medicaoInstrumentoPopupRoute];

@NgModule({
  imports: [Sicapuc20201SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    MedicaoInstrumentoComponent,
    MedicaoInstrumentoDetailComponent,
    MedicaoInstrumentoUpdateComponent,
    MedicaoInstrumentoDeleteDialogComponent,
    MedicaoInstrumentoDeletePopupComponent
  ],
  entryComponents: [
    MedicaoInstrumentoComponent,
    MedicaoInstrumentoUpdateComponent,
    MedicaoInstrumentoDeleteDialogComponent,
    MedicaoInstrumentoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Sicapuc20201MedicaoInstrumentoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
