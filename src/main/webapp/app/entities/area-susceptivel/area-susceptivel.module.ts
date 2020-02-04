import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { Sicapuc20201SharedModule } from 'app/shared';
import {
  AreaSusceptivelComponent,
  AreaSusceptivelDetailComponent,
  AreaSusceptivelUpdateComponent,
  AreaSusceptivelDeletePopupComponent,
  AreaSusceptivelDeleteDialogComponent,
  areaSusceptivelRoute,
  areaSusceptivelPopupRoute
} from './';

const ENTITY_STATES = [...areaSusceptivelRoute, ...areaSusceptivelPopupRoute];

@NgModule({
  imports: [Sicapuc20201SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AreaSusceptivelComponent,
    AreaSusceptivelDetailComponent,
    AreaSusceptivelUpdateComponent,
    AreaSusceptivelDeleteDialogComponent,
    AreaSusceptivelDeletePopupComponent
  ],
  entryComponents: [
    AreaSusceptivelComponent,
    AreaSusceptivelUpdateComponent,
    AreaSusceptivelDeleteDialogComponent,
    AreaSusceptivelDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Sicapuc20201AreaSusceptivelModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
