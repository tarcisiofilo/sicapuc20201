import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IIncidente, Incidente } from 'app/shared/model/incidente.model';
import { IncidenteService } from './incidente.service';
import { IMedicaoInstrumento } from 'app/shared/model/medicao-instrumento.model';
import { MedicaoInstrumentoService } from 'app/entities/medicao-instrumento';
import { INivelIncidente } from 'app/shared/model/nivel-incidente.model';
import { NivelIncidenteService } from 'app/entities/nivel-incidente';
import { IAtivo } from 'app/shared/model/ativo.model';
import { AtivoService } from 'app/entities/ativo';

@Component({
  selector: 'jhi-incidente-update',
  templateUrl: './incidente-update.component.html'
})
export class IncidenteUpdateComponent implements OnInit {
  incidente: IIncidente;
  isSaving: boolean;

  medicaoinstrumentos: IMedicaoInstrumento[];

  nivelincidentes: INivelIncidente[];

  ativos: IAtivo[];

  editForm = this.fb.group({
    id: [],
    identificacao: [null, [Validators.required]],
    origem: [null, [Validators.required]],
    mensagem: [null, [Validators.required]],
    medicaoInstrumentoId: [],
    nivelIncidenteId: [],
    ativos: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected incidenteService: IncidenteService,
    protected medicaoInstrumentoService: MedicaoInstrumentoService,
    protected nivelIncidenteService: NivelIncidenteService,
    protected ativoService: AtivoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ incidente }) => {
      this.updateForm(incidente);
      this.incidente = incidente;
    });
    this.medicaoInstrumentoService
      .query({ filter: 'incidente-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IMedicaoInstrumento[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMedicaoInstrumento[]>) => response.body)
      )
      .subscribe(
        (res: IMedicaoInstrumento[]) => {
          if (!this.incidente.medicaoInstrumentoId) {
            this.medicaoinstrumentos = res;
          } else {
            this.medicaoInstrumentoService
              .find(this.incidente.medicaoInstrumentoId)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IMedicaoInstrumento>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IMedicaoInstrumento>) => subResponse.body)
              )
              .subscribe(
                (subRes: IMedicaoInstrumento) => (this.medicaoinstrumentos = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.nivelIncidenteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<INivelIncidente[]>) => mayBeOk.ok),
        map((response: HttpResponse<INivelIncidente[]>) => response.body)
      )
      .subscribe((res: INivelIncidente[]) => (this.nivelincidentes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.ativoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAtivo[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAtivo[]>) => response.body)
      )
      .subscribe((res: IAtivo[]) => (this.ativos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(incidente: IIncidente) {
    this.editForm.patchValue({
      id: incidente.id,
      identificacao: incidente.identificacao,
      origem: incidente.origem,
      mensagem: incidente.mensagem,
      medicaoInstrumentoId: incidente.medicaoInstrumentoId,
      nivelIncidenteId: incidente.nivelIncidenteId,
      ativos: incidente.ativos
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const incidente = this.createFromForm();
    if (incidente.id !== undefined) {
      this.subscribeToSaveResponse(this.incidenteService.update(incidente));
    } else {
      this.subscribeToSaveResponse(this.incidenteService.create(incidente));
    }
  }

  private createFromForm(): IIncidente {
    const entity = {
      ...new Incidente(),
      id: this.editForm.get(['id']).value,
      identificacao: this.editForm.get(['identificacao']).value,
      origem: this.editForm.get(['origem']).value,
      mensagem: this.editForm.get(['mensagem']).value,
      medicaoInstrumentoId: this.editForm.get(['medicaoInstrumentoId']).value,
      nivelIncidenteId: this.editForm.get(['nivelIncidenteId']).value,
      ativos: this.editForm.get(['ativos']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IIncidente>>) {
    result.subscribe((res: HttpResponse<IIncidente>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackMedicaoInstrumentoById(index: number, item: IMedicaoInstrumento) {
    return item.id;
  }

  trackNivelIncidenteById(index: number, item: INivelIncidente) {
    return item.id;
  }

  trackAtivoById(index: number, item: IAtivo) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
