export interface IFuncionario {
  id?: number;
  cargo?: string;
  idDispositivoMonitoramento?: number;
  pessoaId?: number;
  setorMineracaoId?: number;
}

export class Funcionario implements IFuncionario {
  constructor(
    public id?: number,
    public cargo?: string,
    public idDispositivoMonitoramento?: number,
    public pessoaId?: number,
    public setorMineracaoId?: number
  ) {}
}
