import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IMinaOperacao, MinaOperacao } from 'app/shared/model/mina-operacao.model';
import { MinaOperacaoService } from './mina-operacao.service';

@Component({
  selector: 'jhi-mina-operacao-update',
  templateUrl: './mina-operacao-update.component.html'
})
export class MinaOperacaoUpdateComponent implements OnInit {
  minaOperacao: IMinaOperacao;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    segmento: [null, [Validators.required]],
    tipoOperacao: [null, [Validators.required]]
  });

  constructor(protected minaOperacaoService: MinaOperacaoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ minaOperacao }) => {
      this.updateForm(minaOperacao);
      this.minaOperacao = minaOperacao;
    });
  }

  updateForm(minaOperacao: IMinaOperacao) {
    this.editForm.patchValue({
      id: minaOperacao.id,
      nome: minaOperacao.nome,
      segmento: minaOperacao.segmento,
      tipoOperacao: minaOperacao.tipoOperacao
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const minaOperacao = this.createFromForm();
    if (minaOperacao.id !== undefined) {
      this.subscribeToSaveResponse(this.minaOperacaoService.update(minaOperacao));
    } else {
      this.subscribeToSaveResponse(this.minaOperacaoService.create(minaOperacao));
    }
  }

  private createFromForm(): IMinaOperacao {
    const entity = {
      ...new MinaOperacao(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      segmento: this.editForm.get(['segmento']).value,
      tipoOperacao: this.editForm.get(['tipoOperacao']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMinaOperacao>>) {
    result.subscribe((res: HttpResponse<IMinaOperacao>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
