import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { Sicapuc20201SharedModule } from 'app/shared';
import {
  SireneComponent,
  SireneDetailComponent,
  SireneUpdateComponent,
  SireneDeletePopupComponent,
  SireneDeleteDialogComponent,
  sireneRoute,
  sirenePopupRoute
} from './';

const ENTITY_STATES = [...sireneRoute, ...sirenePopupRoute];

@NgModule({
  imports: [Sicapuc20201SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [SireneComponent, SireneDetailComponent, SireneUpdateComponent, SireneDeleteDialogComponent, SireneDeletePopupComponent],
  entryComponents: [SireneComponent, SireneUpdateComponent, SireneDeleteDialogComponent, SireneDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Sicapuc20201SireneModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
