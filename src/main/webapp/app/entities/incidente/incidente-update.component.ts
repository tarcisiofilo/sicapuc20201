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

@Component({
  selector: 'jhi-incidente-update',
  templateUrl: './incidente-update.component.html'
})
export class IncidenteUpdateComponent implements OnInit {
  incidente: IIncidente;
  isSaving: boolean;

  medicaoinstrumentos: IMedicaoInstrumento[];

  nivelincidentes: INivelIncidente[];

  editForm = this.fb.group({
    id: [],
    identificacao: [null, [Validators.required]],
    origem: [null, [Validators.required]],
    medicaoInstrumento: [],
    nivelIncidente: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected incidenteService: IncidenteService,
    protected medicaoInstrumentoService: MedicaoInstrumentoService,
    protected nivelIncidenteService: NivelIncidenteService,
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
          if (!this.incidente.medicaoInstrumento || !this.incidente.medicaoInstrumento.id) {
            this.medicaoinstrumentos = res;
          } else {
            this.medicaoInstrumentoService
              .find(this.incidente.medicaoInstrumento.id)
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
      .query({ filter: 'incidente-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<INivelIncidente[]>) => mayBeOk.ok),
        map((response: HttpResponse<INivelIncidente[]>) => response.body)
      )
      .subscribe(
        (res: INivelIncidente[]) => {
          if (!this.incidente.nivelIncidente || !this.incidente.nivelIncidente.id) {
            this.nivelincidentes = res;
          } else {
            this.nivelIncidenteService
              .find(this.incidente.nivelIncidente.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<INivelIncidente>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<INivelIncidente>) => subResponse.body)
              )
              .subscribe(
                (subRes: INivelIncidente) => (this.nivelincidentes = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(incidente: IIncidente) {
    this.editForm.patchValue({
      id: incidente.id,
      identificacao: incidente.identificacao,
      origem: incidente.origem,
      medicaoInstrumento: incidente.medicaoInstrumento,
      nivelIncidente: incidente.nivelIncidente
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
      medicaoInstrumento: this.editForm.get(['medicaoInstrumento']).value,
      nivelIncidente: this.editForm.get(['nivelIncidente']).value
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
}
