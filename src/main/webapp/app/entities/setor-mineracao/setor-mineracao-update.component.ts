import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISetorMineracao, SetorMineracao } from 'app/shared/model/setor-mineracao.model';
import { SetorMineracaoService } from './setor-mineracao.service';
import { IFuncionario } from 'app/shared/model/funcionario.model';
import { FuncionarioService } from 'app/entities/funcionario';
import { IMinaOperacao } from 'app/shared/model/mina-operacao.model';
import { MinaOperacaoService } from 'app/entities/mina-operacao';

@Component({
  selector: 'jhi-setor-mineracao-update',
  templateUrl: './setor-mineracao-update.component.html'
})
export class SetorMineracaoUpdateComponent implements OnInit {
  setorMineracao: ISetorMineracao;
  isSaving: boolean;

  funcionarios: IFuncionario[];

  minaoperacaos: IMinaOperacao[];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    diretorId: [],
    gerenteId: [],
    minaOperacaoId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected setorMineracaoService: SetorMineracaoService,
    protected funcionarioService: FuncionarioService,
    protected minaOperacaoService: MinaOperacaoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ setorMineracao }) => {
      this.updateForm(setorMineracao);
      this.setorMineracao = setorMineracao;
    });
    this.funcionarioService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFuncionario[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFuncionario[]>) => response.body)
      )
      .subscribe((res: IFuncionario[]) => (this.funcionarios = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.minaOperacaoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IMinaOperacao[]>) => mayBeOk.ok),
        map((response: HttpResponse<IMinaOperacao[]>) => response.body)
      )
      .subscribe((res: IMinaOperacao[]) => (this.minaoperacaos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(setorMineracao: ISetorMineracao) {
    this.editForm.patchValue({
      id: setorMineracao.id,
      nome: setorMineracao.nome,
      diretorId: setorMineracao.diretorId,
      gerenteId: setorMineracao.gerenteId,
      minaOperacaoId: setorMineracao.minaOperacaoId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const setorMineracao = this.createFromForm();
    if (setorMineracao.id !== undefined) {
      this.subscribeToSaveResponse(this.setorMineracaoService.update(setorMineracao));
    } else {
      this.subscribeToSaveResponse(this.setorMineracaoService.create(setorMineracao));
    }
  }

  private createFromForm(): ISetorMineracao {
    const entity = {
      ...new SetorMineracao(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      diretorId: this.editForm.get(['diretorId']).value,
      gerenteId: this.editForm.get(['gerenteId']).value,
      minaOperacaoId: this.editForm.get(['minaOperacaoId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISetorMineracao>>) {
    result.subscribe((res: HttpResponse<ISetorMineracao>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackFuncionarioById(index: number, item: IFuncionario) {
    return item.id;
  }

  trackMinaOperacaoById(index: number, item: IMinaOperacao) {
    return item.id;
  }
}
