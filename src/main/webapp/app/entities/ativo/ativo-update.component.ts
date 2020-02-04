import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAtivo, Ativo } from 'app/shared/model/ativo.model';
import { AtivoService } from './ativo.service';
import { ISetorMineracao } from 'app/shared/model/setor-mineracao.model';
import { SetorMineracaoService } from 'app/entities/setor-mineracao';
import { IIncidente } from 'app/shared/model/incidente.model';
import { IncidenteService } from 'app/entities/incidente';

@Component({
  selector: 'jhi-ativo-update',
  templateUrl: './ativo-update.component.html'
})
export class AtivoUpdateComponent implements OnInit {
  ativo: IAtivo;
  isSaving: boolean;

  setormineracaos: ISetorMineracao[];

  incidentes: IIncidente[];

  editForm = this.fb.group({
    id: [],
    idTipoAtivo: [null, [Validators.required]],
    tipoAtivo: [null, [Validators.required]],
    periodicidadeDiasManutencao: [null, [Validators.required]],
    setorMineracao: [],
    incidente: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected ativoService: AtivoService,
    protected setorMineracaoService: SetorMineracaoService,
    protected incidenteService: IncidenteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ ativo }) => {
      this.updateForm(ativo);
      this.ativo = ativo;
    });
    this.setorMineracaoService
      .query({ filter: 'ativo-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<ISetorMineracao[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISetorMineracao[]>) => response.body)
      )
      .subscribe(
        (res: ISetorMineracao[]) => {
          if (!this.ativo.setorMineracao || !this.ativo.setorMineracao.id) {
            this.setormineracaos = res;
          } else {
            this.setorMineracaoService
              .find(this.ativo.setorMineracao.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<ISetorMineracao>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<ISetorMineracao>) => subResponse.body)
              )
              .subscribe(
                (subRes: ISetorMineracao) => (this.setormineracaos = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.incidenteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IIncidente[]>) => mayBeOk.ok),
        map((response: HttpResponse<IIncidente[]>) => response.body)
      )
      .subscribe((res: IIncidente[]) => (this.incidentes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(ativo: IAtivo) {
    this.editForm.patchValue({
      id: ativo.id,
      idTipoAtivo: ativo.idTipoAtivo,
      tipoAtivo: ativo.tipoAtivo,
      periodicidadeDiasManutencao: ativo.periodicidadeDiasManutencao,
      setorMineracao: ativo.setorMineracao,
      incidente: ativo.incidente
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const ativo = this.createFromForm();
    if (ativo.id !== undefined) {
      this.subscribeToSaveResponse(this.ativoService.update(ativo));
    } else {
      this.subscribeToSaveResponse(this.ativoService.create(ativo));
    }
  }

  private createFromForm(): IAtivo {
    const entity = {
      ...new Ativo(),
      id: this.editForm.get(['id']).value,
      idTipoAtivo: this.editForm.get(['idTipoAtivo']).value,
      tipoAtivo: this.editForm.get(['tipoAtivo']).value,
      periodicidadeDiasManutencao: this.editForm.get(['periodicidadeDiasManutencao']).value,
      setorMineracao: this.editForm.get(['setorMineracao']).value,
      incidente: this.editForm.get(['incidente']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAtivo>>) {
    result.subscribe((res: HttpResponse<IAtivo>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackSetorMineracaoById(index: number, item: ISetorMineracao) {
    return item.id;
  }

  trackIncidenteById(index: number, item: IIncidente) {
    return item.id;
  }
}
