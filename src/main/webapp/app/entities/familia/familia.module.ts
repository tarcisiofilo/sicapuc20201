import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { Sicapuc20201SharedModule } from 'app/shared';
import {
  FamiliaComponent,
  FamiliaDetailComponent,
  FamiliaUpdateComponent,
  FamiliaDeletePopupComponent,
  FamiliaDeleteDialogComponent,
  familiaRoute,
  familiaPopupRoute
} from './';

const ENTITY_STATES = [...familiaRoute, ...familiaPopupRoute];

@NgModule({
  imports: [Sicapuc20201SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FamiliaComponent,
    FamiliaDetailComponent,
    FamiliaUpdateComponent,
    FamiliaDeleteDialogComponent,
    FamiliaDeletePopupComponent
  ],
  entryComponents: [FamiliaComponent, FamiliaUpdateComponent, FamiliaDeleteDialogComponent, FamiliaDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Sicapuc20201FamiliaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
