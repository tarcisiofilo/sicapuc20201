import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFamilia } from 'app/shared/model/familia.model';
import { AccountService } from 'app/core';
import { FamiliaService } from './familia.service';

@Component({
  selector: 'jhi-familia',
  templateUrl: './familia.component.html'
})
export class FamiliaComponent implements OnInit, OnDestroy {
  familias: IFamilia[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected familiaService: FamiliaService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.familiaService
      .query()
      .pipe(
        filter((res: HttpResponse<IFamilia[]>) => res.ok),
        map((res: HttpResponse<IFamilia[]>) => res.body)
      )
      .subscribe(
        (res: IFamilia[]) => {
          this.familias = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInFamilias();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFamilia) {
    return item.id;
  }

  registerChangeInFamilias() {
    this.eventSubscriber = this.eventManager.subscribe('familiaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
