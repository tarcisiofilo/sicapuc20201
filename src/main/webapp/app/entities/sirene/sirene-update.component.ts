import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISirene, Sirene } from 'app/shared/model/sirene.model';
import { SireneService } from './sirene.service';
import { IAreaSusceptivel } from 'app/shared/model/area-susceptivel.model';
import { AreaSusceptivelService } from 'app/entities/area-susceptivel';

@Component({
  selector: 'jhi-sirene-update',
  templateUrl: './sirene-update.component.html'
})
export class SireneUpdateComponent implements OnInit {
  sirene: ISirene;
  isSaving: boolean;

  areasusceptivels: IAreaSusceptivel[];

  editForm = this.fb.group({
    id: [],
    identificacao: [null, [Validators.required]],
    urlAtivar: [null, [Validators.required]],
    areaSusceptivelId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected sireneService: SireneService,
    protected areaSusceptivelService: AreaSusceptivelService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sirene }) => {
      this.updateForm(sirene);
      this.sirene = sirene;
    });
    this.areaSusceptivelService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAreaSusceptivel[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAreaSusceptivel[]>) => response.body)
      )
      .subscribe((res: IAreaSusceptivel[]) => (this.areasusceptivels = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(sirene: ISirene) {
    this.editForm.patchValue({
      id: sirene.id,
      identificacao: sirene.identificacao,
      urlAtivar: sirene.urlAtivar,
      areaSusceptivelId: sirene.areaSusceptivelId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sirene = this.createFromForm();
    if (sirene.id !== undefined) {
      this.subscribeToSaveResponse(this.sireneService.update(sirene));
    } else {
      this.subscribeToSaveResponse(this.sireneService.create(sirene));
    }
  }

  private createFromForm(): ISirene {
    const entity = {
      ...new Sirene(),
      id: this.editForm.get(['id']).value,
      identificacao: this.editForm.get(['identificacao']).value,
      urlAtivar: this.editForm.get(['urlAtivar']).value,
      areaSusceptivelId: this.editForm.get(['areaSusceptivelId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISirene>>) {
    result.subscribe((res: HttpResponse<ISirene>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
