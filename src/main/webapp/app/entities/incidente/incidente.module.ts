import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { Sicapuc20201SharedModule } from 'app/shared';
import {
  IncidenteComponent,
  IncidenteDetailComponent,
  IncidenteUpdateComponent,
  IncidenteDeletePopupComponent,
  IncidenteDeleteDialogComponent,
  incidenteRoute,
  incidentePopupRoute
} from './';

const ENTITY_STATES = [...incidenteRoute, ...incidentePopupRoute];

@NgModule({
  imports: [Sicapuc20201SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    IncidenteComponent,
    IncidenteDetailComponent,
    IncidenteUpdateComponent,
    IncidenteDeleteDialogComponent,
    IncidenteDeletePopupComponent
  ],
  entryComponents: [IncidenteComponent, IncidenteUpdateComponent, IncidenteDeleteDialogComponent, IncidenteDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Sicapuc20201IncidenteModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
