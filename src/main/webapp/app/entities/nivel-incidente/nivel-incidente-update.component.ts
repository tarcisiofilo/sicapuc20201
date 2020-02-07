import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { INivelIncidente, NivelIncidente } from 'app/shared/model/nivel-incidente.model';
import { NivelIncidenteService } from './nivel-incidente.service';

@Component({
  selector: 'jhi-nivel-incidente-update',
  templateUrl: './nivel-incidente-update.component.html'
})
export class NivelIncidenteUpdateComponent implements OnInit {
  nivelIncidente: INivelIncidente;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    notificaDNPM: [null, [Validators.required]],
    notificaEmail: [null, [Validators.required]],
    notificacaoSms: [null, [Validators.required]],
    notificacaoWhatsapp: [null, [Validators.required]],
    notificacaoDispositivoSeguranca: [null, [Validators.required]],
    notificaSirene: [null, [Validators.required]]
  });

  constructor(protected nivelIncidenteService: NivelIncidenteService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ nivelIncidente }) => {
      this.updateForm(nivelIncidente);
      this.nivelIncidente = nivelIncidente;
    });
  }

  updateForm(nivelIncidente: INivelIncidente) {
    this.editForm.patchValue({
      id: nivelIncidente.id,
      nome: nivelIncidente.nome,
      notificaDNPM: nivelIncidente.notificaDNPM,
      notificaEmail: nivelIncidente.notificaEmail,
      notificacaoSms: nivelIncidente.notificacaoSms,
      notificacaoWhatsapp: nivelIncidente.notificacaoWhatsapp,
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
      notificacaoSms: this.editForm.get(['notificacaoSms']).value,
      notificacaoWhatsapp: this.editForm.get(['notificacaoWhatsapp']).value,
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
}
