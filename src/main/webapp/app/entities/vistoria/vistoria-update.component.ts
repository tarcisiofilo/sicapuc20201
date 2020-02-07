import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IVistoria, Vistoria } from 'app/shared/model/vistoria.model';
import { VistoriaService } from './vistoria.service';
import { IInstrumentoMonitoramento } from 'app/shared/model/instrumento-monitoramento.model';
import { InstrumentoMonitoramentoService } from 'app/entities/instrumento-monitoramento';

@Component({
  selector: 'jhi-vistoria-update',
  templateUrl: './vistoria-update.component.html'
})
export class VistoriaUpdateComponent implements OnInit {
  vistoria: IVistoria;
  isSaving: boolean;

  instrumentomonitoramentos: IInstrumentoMonitoramento[];

  editForm = this.fb.group({
    id: [],
    identificao: [null, [Validators.required]],
    data: [null, [Validators.required]],
    valor: [null, [Validators.required]],
    instrumentoMonitoramentoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected vistoriaService: VistoriaService,
    protected instrumentoMonitoramentoService: InstrumentoMonitoramentoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ vistoria }) => {
      this.updateForm(vistoria);
      this.vistoria = vistoria;
    });
    this.instrumentoMonitoramentoService
      .query({ filter: 'vistoria-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IInstrumentoMonitoramento[]>) => mayBeOk.ok),
        map((response: HttpResponse<IInstrumentoMonitoramento[]>) => response.body)
      )
      .subscribe(
        (res: IInstrumentoMonitoramento[]) => {
          if (!this.vistoria.instrumentoMonitoramentoId) {
            this.instrumentomonitoramentos = res;
          } else {
            this.instrumentoMonitoramentoService
              .find(this.vistoria.instrumentoMonitoramentoId)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IInstrumentoMonitoramento>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IInstrumentoMonitoramento>) => subResponse.body)
              )
              .subscribe(
                (subRes: IInstrumentoMonitoramento) => (this.instrumentomonitoramentos = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(vistoria: IVistoria) {
    this.editForm.patchValue({
      id: vistoria.id,
      identificao: vistoria.identificao,
      data: vistoria.data != null ? vistoria.data.format(DATE_TIME_FORMAT) : null,
      valor: vistoria.valor,
      instrumentoMonitoramentoId: vistoria.instrumentoMonitoramentoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const vistoria = this.createFromForm();
    if (vistoria.id !== undefined) {
      this.subscribeToSaveResponse(this.vistoriaService.update(vistoria));
    } else {
      this.subscribeToSaveResponse(this.vistoriaService.create(vistoria));
    }
  }

  private createFromForm(): IVistoria {
    const entity = {
      ...new Vistoria(),
      id: this.editForm.get(['id']).value,
      identificao: this.editForm.get(['identificao']).value,
      data: this.editForm.get(['data']).value != null ? moment(this.editForm.get(['data']).value, DATE_TIME_FORMAT) : undefined,
      valor: this.editForm.get(['valor']).value,
      instrumentoMonitoramentoId: this.editForm.get(['instrumentoMonitoramentoId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVistoria>>) {
    result.subscribe((res: HttpResponse<IVistoria>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackInstrumentoMonitoramentoById(index: number, item: IInstrumentoMonitoramento) {
    return item.id;
  }
}
