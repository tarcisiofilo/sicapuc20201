import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { Sicapuc20201SharedLibsModule, Sicapuc20201SharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [Sicapuc20201SharedLibsModule, Sicapuc20201SharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [Sicapuc20201SharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Sicapuc20201SharedModule {
  static forRoot() {
    return {
      ngModule: Sicapuc20201SharedModule
    };
  }
}
