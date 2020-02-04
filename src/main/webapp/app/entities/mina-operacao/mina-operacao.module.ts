import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { Sicapuc20201SharedModule } from 'app/shared';
import {
  MinaOperacaoComponent,
  MinaOperacaoDetailComponent,
  MinaOperacaoUpdateComponent,
  MinaOperacaoDeletePopupComponent,
  MinaOperacaoDeleteDialogComponent,
  minaOperacaoRoute,
  minaOperacaoPopupRoute
} from './';

const ENTITY_STATES = [...minaOperacaoRoute, ...minaOperacaoPopupRoute];

@NgModule({
  imports: [Sicapuc20201SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    MinaOperacaoComponent,
    MinaOperacaoDetailComponent,
    MinaOperacaoUpdateComponent,
    MinaOperacaoDeleteDialogComponent,
    MinaOperacaoDeletePopupComponent
  ],
  entryComponents: [
    MinaOperacaoComponent,
    MinaOperacaoUpdateComponent,
    MinaOperacaoDeleteDialogComponent,
    MinaOperacaoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Sicapuc20201MinaOperacaoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
