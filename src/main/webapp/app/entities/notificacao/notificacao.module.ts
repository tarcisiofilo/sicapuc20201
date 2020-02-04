import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { Sicapuc20201SharedModule } from 'app/shared';
import {
  NotificacaoComponent,
  NotificacaoDetailComponent,
  NotificacaoUpdateComponent,
  NotificacaoDeletePopupComponent,
  NotificacaoDeleteDialogComponent,
  notificacaoRoute,
  notificacaoPopupRoute
} from './';

const ENTITY_STATES = [...notificacaoRoute, ...notificacaoPopupRoute];

@NgModule({
  imports: [Sicapuc20201SharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    NotificacaoComponent,
    NotificacaoDetailComponent,
    NotificacaoUpdateComponent,
    NotificacaoDeleteDialogComponent,
    NotificacaoDeletePopupComponent
  ],
  entryComponents: [NotificacaoComponent, NotificacaoUpdateComponent, NotificacaoDeleteDialogComponent, NotificacaoDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Sicapuc20201NotificacaoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
