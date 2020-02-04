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
import { IAtivo } from 'app/shared/model/ativo.model';
import { AtivoService } from 'app/entities/ativo';
import { IMinaOperacao } from 'app/shared/model/mina-operacao.model';
import { MinaOperacaoService } from 'app/entities/mina-operacao';

@Component({
  selector: 'jhi-setor-mineracao-update',
  templateUrl: './setor-mineracao-update.component.html'
})
export class SetorMineracaoUpdateComponent implements OnInit {
  setorMineracao: ISetorMineracao;
  isSaving: boolean;

  diretors: IFuncionario[];

  gerentes: IFuncionario[];

  ativos: IAtivo[];

  minaoperacaos: IMinaOperacao[];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    diretor: [],
    gerente: [],
    minaOperacao: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected setorMineracaoService: SetorMineracaoService,
    protected funcionarioService: FuncionarioService,
    protected ativoService: AtivoService,
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
      .query({ filter: 'setormineracao-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IFuncionario[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFuncionario[]>) => response.body)
      )
      .subscribe(
        (res: IFuncionario[]) => {
          if (!this.setorMineracao.diretor || !this.setorMineracao.diretor.id) {
            this.diretors = res;
          } else {
            this.funcionarioService
              .find(this.setorMineracao.diretor.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IFuncionario>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IFuncionario>) => subResponse.body)
              )
              .subscribe(
                (subRes: IFuncionario) => (this.diretors = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.funcionarioService
      .query({ filter: 'setormineracao-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IFuncionario[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFuncionario[]>) => response.body)
      )
      .subscribe(
        (res: IFuncionario[]) => {
          if (!this.setorMineracao.gerente || !this.setorMineracao.gerente.id) {
            this.gerentes = res;
          } else {
            this.funcionarioService
              .find(this.setorMineracao.gerente.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IFuncionario>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IFuncionario>) => subResponse.body)
              )
              .subscribe(
                (subRes: IFuncionario) => (this.gerentes = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.ativoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAtivo[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAtivo[]>) => response.body)
      )
      .subscribe((res: IAtivo[]) => (this.ativos = res), (res: HttpErrorResponse) => this.onError(res.message));
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
      diretor: setorMineracao.diretor,
      gerente: setorMineracao.gerente,
      minaOperacao: setorMineracao.minaOperacao
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
      diretor: this.editForm.get(['diretor']).value,
      gerente: this.editForm.get(['gerente']).value,
      minaOperacao: this.editForm.get(['minaOperacao']).value
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

  trackAtivoById(index: number, item: IAtivo) {
    return item.id;
  }

  trackMinaOperacaoById(index: number, item: IMinaOperacao) {
    return item.id;
  }
}
