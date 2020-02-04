import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFuncionario, Funcionario } from 'app/shared/model/funcionario.model';
import { FuncionarioService } from './funcionario.service';
import { ISetorMineracao } from 'app/shared/model/setor-mineracao.model';
import { SetorMineracaoService } from 'app/entities/setor-mineracao';
import { IPessoa } from 'app/shared/model/pessoa.model';
import { PessoaService } from 'app/entities/pessoa';

@Component({
  selector: 'jhi-funcionario-update',
  templateUrl: './funcionario-update.component.html'
})
export class FuncionarioUpdateComponent implements OnInit {
  funcionario: IFuncionario;
  isSaving: boolean;

  setormineracaos: ISetorMineracao[];

  pessoas: IPessoa[];

  editForm = this.fb.group({
    id: [],
    cargo: [null, [Validators.required]],
    idDispositivoMonitoramento: [null, [Validators.required]],
    setorMineracao: [],
    pessoa: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected funcionarioService: FuncionarioService,
    protected setorMineracaoService: SetorMineracaoService,
    protected pessoaService: PessoaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ funcionario }) => {
      this.updateForm(funcionario);
      this.funcionario = funcionario;
    });
    this.setorMineracaoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISetorMineracao[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISetorMineracao[]>) => response.body)
      )
      .subscribe((res: ISetorMineracao[]) => (this.setormineracaos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.pessoaService
      .query({ filter: 'funcionario-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IPessoa[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPessoa[]>) => response.body)
      )
      .subscribe(
        (res: IPessoa[]) => {
          if (!this.funcionario.pessoa || !this.funcionario.pessoa.id) {
            this.pessoas = res;
          } else {
            this.pessoaService
              .find(this.funcionario.pessoa.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IPessoa>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IPessoa>) => subResponse.body)
              )
              .subscribe(
                (subRes: IPessoa) => (this.pessoas = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(funcionario: IFuncionario) {
    this.editForm.patchValue({
      id: funcionario.id,
      cargo: funcionario.cargo,
      idDispositivoMonitoramento: funcionario.idDispositivoMonitoramento,
      setorMineracao: funcionario.setorMineracao,
      pessoa: funcionario.pessoa
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const funcionario = this.createFromForm();
    if (funcionario.id !== undefined) {
      this.subscribeToSaveResponse(this.funcionarioService.update(funcionario));
    } else {
      this.subscribeToSaveResponse(this.funcionarioService.create(funcionario));
    }
  }

  private createFromForm(): IFuncionario {
    const entity = {
      ...new Funcionario(),
      id: this.editForm.get(['id']).value,
      cargo: this.editForm.get(['cargo']).value,
      idDispositivoMonitoramento: this.editForm.get(['idDispositivoMonitoramento']).value,
      setorMineracao: this.editForm.get(['setorMineracao']).value,
      pessoa: this.editForm.get(['pessoa']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFuncionario>>) {
    result.subscribe((res: HttpResponse<IFuncionario>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackPessoaById(index: number, item: IPessoa) {
    return item.id;
  }
}
