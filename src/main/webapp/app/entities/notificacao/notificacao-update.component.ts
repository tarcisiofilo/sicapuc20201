import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { INotificacao, Notificacao } from 'app/shared/model/notificacao.model';
import { NotificacaoService } from './notificacao.service';
import { IIncidente } from 'app/shared/model/incidente.model';
import { IncidenteService } from 'app/entities/incidente';

@Component({
  selector: 'jhi-notificacao-update',
  templateUrl: './notificacao-update.component.html'
})
export class NotificacaoUpdateComponent implements OnInit {
  notificacao: INotificacao;
  isSaving: boolean;

  incidentes: IIncidente[];

  editForm = this.fb.group({
    id: [],
    dataNotifacao: [null, [Validators.required]],
    tipoNotificacao: [null, [Validators.required]],
    incidente: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected notificacaoService: NotificacaoService,
    protected incidenteService: IncidenteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ notificacao }) => {
      this.updateForm(notificacao);
      this.notificacao = notificacao;
    });
    this.incidenteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IIncidente[]>) => mayBeOk.ok),
        map((response: HttpResponse<IIncidente[]>) => response.body)
      )
      .subscribe((res: IIncidente[]) => (this.incidentes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(notificacao: INotificacao) {
    this.editForm.patchValue({
      id: notificacao.id,
      dataNotifacao: notificacao.dataNotifacao != null ? notificacao.dataNotifacao.format(DATE_TIME_FORMAT) : null,
      tipoNotificacao: notificacao.tipoNotificacao,
      incidente: notificacao.incidente
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const notificacao = this.createFromForm();
    if (notificacao.id !== undefined) {
      this.subscribeToSaveResponse(this.notificacaoService.update(notificacao));
    } else {
      this.subscribeToSaveResponse(this.notificacaoService.create(notificacao));
    }
  }

  private createFromForm(): INotificacao {
    const entity = {
      ...new Notificacao(),
      id: this.editForm.get(['id']).value,
      dataNotifacao:
        this.editForm.get(['dataNotifacao']).value != null
          ? moment(this.editForm.get(['dataNotifacao']).value, DATE_TIME_FORMAT)
          : undefined,
      tipoNotificacao: this.editForm.get(['tipoNotificacao']).value,
      incidente: this.editForm.get(['incidente']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INotificacao>>) {
    result.subscribe((res: HttpResponse<INotificacao>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackIncidenteById(index: number, item: IIncidente) {
    return item.id;
  }
}
