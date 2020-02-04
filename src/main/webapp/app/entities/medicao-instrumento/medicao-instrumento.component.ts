import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMedicaoInstrumento } from 'app/shared/model/medicao-instrumento.model';
import { AccountService } from 'app/core';
import { MedicaoInstrumentoService } from './medicao-instrumento.service';

@Component({
  selector: 'jhi-medicao-instrumento',
  templateUrl: './medicao-instrumento.component.html'
})
export class MedicaoInstrumentoComponent implements OnInit, OnDestroy {
  medicaoInstrumentos: IMedicaoInstrumento[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected medicaoInstrumentoService: MedicaoInstrumentoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.medicaoInstrumentoService
      .query()
      .pipe(
        filter((res: HttpResponse<IMedicaoInstrumento[]>) => res.ok),
        map((res: HttpResponse<IMedicaoInstrumento[]>) => res.body)
      )
      .subscribe(
        (res: IMedicaoInstrumento[]) => {
          this.medicaoInstrumentos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInMedicaoInstrumentos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMedicaoInstrumento) {
    return item.id;
  }

  registerChangeInMedicaoInstrumentos() {
    this.eventSubscriber = this.eventManager.subscribe('medicaoInstrumentoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
