import { ISetorMineracao } from 'app/shared/model/setor-mineracao.model';
import { IPessoa } from 'app/shared/model/pessoa.model';

export interface IFuncionario {
  id?: number;
  cargo?: string;
  idDispositivoMonitoramento?: number;
  setorMineracao?: ISetorMineracao;
  pessoa?: IPessoa;
}

export class Funcionario implements IFuncionario {
  constructor(
    public id?: number,
    public cargo?: string,
    public idDispositivoMonitoramento?: number,
    public setorMineracao?: ISetorMineracao,
    public pessoa?: IPessoa
  ) {}
}
