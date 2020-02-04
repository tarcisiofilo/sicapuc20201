import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IInstrumentoMonitoramento, InstrumentoMonitoramento } from 'app/shared/model/instrumento-monitoramento.model';
import { InstrumentoMonitoramentoService } from './instrumento-monitoramento.service';
import { IVistoria } from 'app/shared/model/vistoria.model';
import { VistoriaService } from 'app/entities/vistoria';

@Component({
  selector: 'jhi-instrumento-monitoramento-update',
  templateUrl: './instrumento-monitoramento-update.component.html'
})
export class InstrumentoMonitoramentoUpdateComponent implements OnInit {
  instrumentoMonitoramento: IInstrumentoMonitoramento;
  isSaving: boolean;

  vistorias: IVistoria[];

  editForm = this.fb.group({
    id: [],
    identificao: [null, [Validators.required]],
    tipoInstrumentoMonitoramento: [null, [Validators.required]],
    tipoMedicao: [null, [Validators.required]],
    urlGetMedicao: [],
    intervaloMedicaoAPI: [],
    varianciaTolerada: [null, [Validators.required]],
    limiteSuperior: [null, [Validators.required]]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected instrumentoMonitoramentoService: InstrumentoMonitoramentoService,
    protected vistoriaService: VistoriaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ instrumentoMonitoramento }) => {
      this.updateForm(instrumentoMonitoramento);
      this.instrumentoMonitoramento = instrumentoMonitoramento;
    });
    this.vistoriaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IVistoria[]>) => mayBeOk.ok),
        map((response: HttpResponse<IVistoria[]>) => response.body)
      )
      .subscribe((res: IVistoria[]) => (this.vistorias = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(instrumentoMonitoramento: IInstrumentoMonitoramento) {
    this.editForm.patchValue({
      id: instrumentoMonitoramento.id,
      identificao: instrumentoMonitoramento.identificao,
      tipoInstrumentoMonitoramento: instrumentoMonitoramento.tipoInstrumentoMonitoramento,
      tipoMedicao: instrumentoMonitoramento.tipoMedicao,
      urlGetMedicao: instrumentoMonitoramento.urlGetMedicao,
      intervaloMedicaoAPI: instrumentoMonitoramento.intervaloMedicaoAPI,
      varianciaTolerada: instrumentoMonitoramento.varianciaTolerada,
      limiteSuperior: instrumentoMonitoramento.limiteSuperior
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const instrumentoMonitoramento = this.createFromForm();
    if (instrumentoMonitoramento.id !== undefined) {
      this.subscribeToSaveResponse(this.instrumentoMonitoramentoService.update(instrumentoMonitoramento));
    } else {
      this.subscribeToSaveResponse(this.instrumentoMonitoramentoService.create(instrumentoMonitoramento));
    }
  }

  private createFromForm(): IInstrumentoMonitoramento {
    const entity = {
      ...new InstrumentoMonitoramento(),
      id: this.editForm.get(['id']).value,
      identificao: this.editForm.get(['identificao']).value,
      tipoInstrumentoMonitoramento: this.editForm.get(['tipoInstrumentoMonitoramento']).value,
      tipoMedicao: this.editForm.get(['tipoMedicao']).value,
      urlGetMedicao: this.editForm.get(['urlGetMedicao']).value,
      intervaloMedicaoAPI: this.editForm.get(['intervaloMedicaoAPI']).value,
      varianciaTolerada: this.editForm.get(['varianciaTolerada']).value,
      limiteSuperior: this.editForm.get(['limiteSuperior']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInstrumentoMonitoramento>>) {
    result.subscribe(
      (res: HttpResponse<IInstrumentoMonitoramento>) => this.onSaveSuccess(),
      (res: HttpErrorResponse) => this.onSaveError()
    );
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

  trackVistoriaById(index: number, item: IVistoria) {
    return item.id;
  }
}
