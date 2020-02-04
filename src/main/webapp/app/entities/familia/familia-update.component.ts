import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFamilia, Familia } from 'app/shared/model/familia.model';
import { FamiliaService } from './familia.service';
import { IAreaSusceptivel } from 'app/shared/model/area-susceptivel.model';
import { AreaSusceptivelService } from 'app/entities/area-susceptivel';

@Component({
  selector: 'jhi-familia-update',
  templateUrl: './familia-update.component.html'
})
export class FamiliaUpdateComponent implements OnInit {
  familia: IFamilia;
  isSaving: boolean;

  areasusceptivels: IAreaSusceptivel[];

  editForm = this.fb.group({
    id: [],
    identificacao: [null, [Validators.required]],
    areaSusceptivel: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected familiaService: FamiliaService,
    protected areaSusceptivelService: AreaSusceptivelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ familia }) => {
      this.updateForm(familia);
      this.familia = familia;
    });
    this.areaSusceptivelService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAreaSusceptivel[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAreaSusceptivel[]>) => response.body)
      )
      .subscribe((res: IAreaSusceptivel[]) => (this.areasusceptivels = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(familia: IFamilia) {
    this.editForm.patchValue({
      id: familia.id,
      identificacao: familia.identificacao,
      areaSusceptivel: familia.areaSusceptivel
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const familia = this.createFromForm();
    if (familia.id !== undefined) {
      this.subscribeToSaveResponse(this.familiaService.update(familia));
    } else {
      this.subscribeToSaveResponse(this.familiaService.create(familia));
    }
  }

  private createFromForm(): IFamilia {
    const entity = {
      ...new Familia(),
      id: this.editForm.get(['id']).value,
      identificacao: this.editForm.get(['identificacao']).value,
      areaSusceptivel: this.editForm.get(['areaSusceptivel']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFamilia>>) {
    result.subscribe((res: HttpResponse<IFamilia>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackAreaSusceptivelById(index: number, item: IAreaSusceptivel) {
    return item.id;
  }
}
