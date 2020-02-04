import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { Sicapuc20201SharedModule } from 'app/shared';
import {
  NivelIncidenteComponent,
  NivelIncidenteDetailComponent,
  NivelIncidenteUpdateComponent,
  NivelIncidenteDeletePopupComponent,
  NivelIncidenteDeleteDialogComponent,
  nivelIncidenteRoute,
  nivelIncidentePopupRoute
} from './';

const ENTITY_STATES = [...nivelIncidenteRoute, ...nivelIncidentePopupRoute];

@NgModule({
  imports: [Sicapuc20201SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    NivelIncidenteComponent,
    NivelIncidenteDetailComponent,
    NivelIncidenteUpdateComponent,
    NivelIncidenteDeleteDialogComponent,
    NivelIncidenteDeletePopupComponent
  ],
  entryComponents: [
    NivelIncidenteComponent,
    NivelIncidenteUpdateComponent,
    NivelIncidenteDeleteDialogComponent,
    NivelIncidenteDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Sicapuc20201NivelIncidenteModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
