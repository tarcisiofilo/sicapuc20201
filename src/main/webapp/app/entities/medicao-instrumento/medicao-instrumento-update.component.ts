import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IMedicaoInstrumento, MedicaoInstrumento } from 'app/shared/model/medicao-instrumento.model';
import { MedicaoInstrumentoService } from './medicao-instrumento.service';
import { IIncidente } from 'app/shared/model/incidente.model';
import { IncidenteService } from 'app/entities/incidente';
import { IInstrumentoMonitoramento } from 'app/shared/model/instrumento-monitoramento.model';
import { InstrumentoMonitoramentoService } from 'app/entities/instrumento-monitoramento';

@Component({
  selector: 'jhi-medicao-instrumento-update',
  templateUrl: './medicao-instrumento-update.component.html'
})
export class MedicaoInstrumentoUpdateComponent implements OnInit {
  medicaoInstrumento: IMedicaoInstrumento;
  isSaving: boolean;

  incidentes: IIncidente[];

  instrumentomonitoramentos: IInstrumentoMonitoramento[];

  editForm = this.fb.group({
    id: [],
    data: [null, [Validators.required]],
    valor: [null, [Validators.required]],
    instrumentoMonitoramentoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected medicaoInstrumentoService: MedicaoInstrumentoService,
    protected incidenteService: IncidenteService,
    protected instrumentoMonitoramentoService: InstrumentoMonitoramentoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ medicaoInstrumento }) => {
      this.updateForm(medicaoInstrumento);
      this.medicaoInstrumento = medicaoInstrumento;
    });
    this.incidenteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IIncidente[]>) => mayBeOk.ok),
        map((response: HttpResponse<IIncidente[]>) => response.body)
      )
      .subscribe((res: IIncidente[]) => (this.incidentes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.instrumentoMonitoramentoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IInstrumentoMonitoramento[]>) => mayBeOk.ok),
        map((response: HttpResponse<IInstrumentoMonitoramento[]>) => response.body)
      )
      .subscribe(
        (res: IInstrumentoMonitoramento[]) => (this.instrumentomonitoramentos = res),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(medicaoInstrumento: IMedicaoInstrumento) {
    this.editForm.patchValue({
      id: medicaoInstrumento.id,
      data: medicaoInstrumento.data != null ? medicaoInstrumento.data.format(DATE_TIME_FORMAT) : null,
      valor: medicaoInstrumento.valor,
      instrumentoMonitoramentoId: medicaoInstrumento.instrumentoMonitoramentoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const medicaoInstrumento = this.createFromForm();
    if (medicaoInstrumento.id !== undefined) {
      this.subscribeToSaveResponse(this.medicaoInstrumentoService.update(medicaoInstrumento));
    } else {
      this.subscribeToSaveResponse(this.medicaoInstrumentoService.create(medicaoInstrumento));
    }
  }

  private createFromForm(): IMedicaoInstrumento {
    const entity = {
      ...new MedicaoInstrumento(),
      id: this.editForm.get(['id']).value,
      data: this.editForm.get(['data']).value != null ? moment(this.editForm.get(['data']).value, DATE_TIME_FORMAT) : undefined,
      valor: this.editForm.get(['valor']).value,
      instrumentoMonitoramentoId: this.editForm.get(['instrumentoMonitoramentoId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMedicaoInstrumento>>) {
    result.subscribe((res: HttpResponse<IMedicaoInstrumento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackInstrumentoMonitoramentoById(index: number, item: IInstrumentoMonitoramento) {
    return item.id;
  }
}
