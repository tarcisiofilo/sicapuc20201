import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAreaSusceptivel, AreaSusceptivel } from 'app/shared/model/area-susceptivel.model';
import { AreaSusceptivelService } from './area-susceptivel.service';
import { IAtivo } from 'app/shared/model/ativo.model';
import { AtivoService } from 'app/entities/ativo';

@Component({
  selector: 'jhi-area-susceptivel-update',
  templateUrl: './area-susceptivel-update.component.html'
})
export class AreaSusceptivelUpdateComponent implements OnInit {
  areaSusceptivel: IAreaSusceptivel;
  isSaving: boolean;

  ativos: IAtivo[];

  editForm = this.fb.group({
    id: [],
    identificacao: [null, [Validators.required]],
    nivelProximidade: [null, [Validators.required]],
    ativo: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected areaSusceptivelService: AreaSusceptivelService,
    protected ativoService: AtivoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ areaSusceptivel }) => {
      this.updateForm(areaSusceptivel);
      this.areaSusceptivel = areaSusceptivel;
    });
    this.ativoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAtivo[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAtivo[]>) => response.body)
      )
      .subscribe((res: IAtivo[]) => (this.ativos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(areaSusceptivel: IAreaSusceptivel) {
    this.editForm.patchValue({
      id: areaSusceptivel.id,
      identificacao: areaSusceptivel.identificacao,
      nivelProximidade: areaSusceptivel.nivelProximidade,
      ativo: areaSusceptivel.ativo
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const areaSusceptivel = this.createFromForm();
    if (areaSusceptivel.id !== undefined) {
      this.subscribeToSaveResponse(this.areaSusceptivelService.update(areaSusceptivel));
    } else {
      this.subscribeToSaveResponse(this.areaSusceptivelService.create(areaSusceptivel));
    }
  }

  private createFromForm(): IAreaSusceptivel {
    const entity = {
      ...new AreaSusceptivel(),
      id: this.editForm.get(['id']).value,
      identificacao: this.editForm.get(['identificacao']).value,
      nivelProximidade: this.editForm.get(['nivelProximidade']).value,
      ativo: this.editForm.get(['ativo']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAreaSusceptivel>>) {
    result.subscribe((res: HttpResponse<IAreaSusceptivel>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackAtivoById(index: number, item: IAtivo) {
    return item.id;
  }
}
