import { IFuncionario } from 'app/shared/model/funcionario.model';
import { IAtivo } from 'app/shared/model/ativo.model';
import { IMinaOperacao } from 'app/shared/model/mina-operacao.model';

export interface ISetorMineracao {
  id?: number;
  nome?: string;
  diretor?: IFuncionario;
  gerente?: IFuncionario;
  funcionarios?: IFuncionario[];
  ativo?: IAtivo;
  minaOperacao?: IMinaOperacao;
}

export class SetorMineracao implements ISetorMineracao {
  constructor(
    public id?: number,
    public nome?: string,
    public diretor?: IFuncionario,
    public gerente?: IFuncionario,
    public funcionarios?: IFuncionario[],
    public ativo?: IAtivo,
    public minaOperacao?: IMinaOperacao
  ) {}
}
