import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { Sicapuc20201SharedModule } from 'app/shared';
import {
  SetorMineracaoComponent,
  SetorMineracaoDetailComponent,
  SetorMineracaoUpdateComponent,
  SetorMineracaoDeletePopupComponent,
  SetorMineracaoDeleteDialogComponent,
  setorMineracaoRoute,
  setorMineracaoPopupRoute
} from './';

const ENTITY_STATES = [...setorMineracaoRoute, ...setorMineracaoPopupRoute];

@NgModule({
  imports: [Sicapuc20201SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SetorMineracaoComponent,
    SetorMineracaoDetailComponent,
    SetorMineracaoUpdateComponent,
    SetorMineracaoDeleteDialogComponent,
    SetorMineracaoDeletePopupComponent
  ],
  entryComponents: [
    SetorMineracaoComponent,
    SetorMineracaoUpdateComponent,
    SetorMineracaoDeleteDialogComponent,
    SetorMineracaoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Sicapuc20201SetorMineracaoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
