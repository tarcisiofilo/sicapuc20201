import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { Sicapuc20201SharedModule } from 'app/shared';
import {
  InstrumentoMonitoramentoComponent,
  InstrumentoMonitoramentoDetailComponent,
  InstrumentoMonitoramentoUpdateComponent,
  InstrumentoMonitoramentoDeletePopupComponent,
  InstrumentoMonitoramentoDeleteDialogComponent,
  instrumentoMonitoramentoRoute,
  instrumentoMonitoramentoPopupRoute
} from './';

const ENTITY_STATES = [...instrumentoMonitoramentoRoute, ...instrumentoMonitoramentoPopupRoute];

@NgModule({
  imports: [Sicapuc20201SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    InstrumentoMonitoramentoComponent,
    InstrumentoMonitoramentoDetailComponent,
    InstrumentoMonitoramentoUpdateComponent,
    InstrumentoMonitoramentoDeleteDialogComponent,
    InstrumentoMonitoramentoDeletePopupComponent
  ],
  entryComponents: [
    InstrumentoMonitoramentoComponent,
    InstrumentoMonitoramentoUpdateComponent,
    InstrumentoMonitoramentoDeleteDialogComponent,
    InstrumentoMonitoramentoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Sicapuc20201InstrumentoMonitoramentoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
