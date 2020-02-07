import { IFuncionario } from 'app/shared/model/funcionario.model';

export interface ISetorMineracao {
  id?: number;
  nome?: string;
  funcionarios?: IFuncionario[];
  diretorId?: number;
  gerenteId?: number;
  minaOperacaoId?: number;
}

export class SetorMineracao implements ISetorMineracao {
  constructor(
    public id?: number,
    public nome?: string,
    public funcionarios?: IFuncionario[],
    public diretorId?: number,
    public gerenteId?: number,
    public minaOperacaoId?: number
  ) {}
}
