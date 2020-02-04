import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { INivelIncidente, NivelIncidente } from 'app/shared/model/nivel-incidente.model';
import { NivelIncidenteService } from './nivel-incidente.service';
import { IIncidente } from 'app/shared/model/incidente.model';
import { IncidenteService } from 'app/entities/incidente';

@Component({
  selector: 'jhi-nivel-incidente-update',
  templateUrl: './nivel-incidente-update.component.html'
})
export class NivelIncidenteUpdateComponent implements OnInit {
  nivelIncidente: INivelIncidente;
  isSaving: boolean;

  incidentes: IIncidente[];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    notificaDNPM: [null, [Validators.required]],
    notificaEmail: [null, [Validators.required]],
    notificacaoSmsWhatsapp: [null, [Validators.required]],
    notificacaoDispositivoSeguranca: [null, [Validators.required]],
    notificaSirene: [null, [Validators.required]]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected nivelIncidenteService: NivelIncidenteService,
    protected incidenteService: IncidenteService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ nivelIncidente }) => {
      this.updateForm(nivelIncidente);
      this.nivelIncidente = nivelIncidente;
    });
    this.incidenteService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IIncidente[]>) => mayBeOk.ok),
        map((response: HttpResponse<IIncidente[]>) => response.body)
      )
      .subscribe((res: IIncidente[]) => (this.incidentes = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(nivelIncidente: INivelIncidente) {
    this.editForm.patchValue({
      id: nivelIncidente.id,
      nome: nivelIncidente.nome,
      notificaDNPM: nivelIncidente.notificaDNPM,
      notificaEmail: nivelIncidente.notificaEmail,
      notificacaoSmsWhatsapp: nivelIncidente.notificacaoSmsWhatsapp,
      notificacaoDispositivoSeguranca: nivelIncidente.notificacaoDispositivoSeguranca,
      notificaSirene: nivelIncidente.notificaSirene
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const nivelIncidente = this.createFromForm();
    if (nivelIncidente.id !== undefined) {
      this.subscribeToSaveResponse(this.nivelIncidenteService.update(nivelIncidente));
    } else {
      this.subscribeToSaveResponse(this.nivelIncidenteService.create(nivelIncidente));
    }
  }

  private createFromForm(): INivelIncidente {
    const entity = {
      ...new NivelIncidente(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      notificaDNPM: this.editForm.get(['notificaDNPM']).value,
      notificaEmail: this.editForm.get(['notificaEmail']).value,
      notificacaoSmsWhatsapp: this.editForm.get(['notificacaoSmsWhatsapp']).value,
      notificacaoDispositivoSeguranca: this.editForm.get(['notificacaoDispositivoSeguranca']).value,
      notificaSirene: this.editForm.get(['notificaSirene']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INivelIncidente>>) {
    result.subscribe((res: HttpResponse<INivelIncidente>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
