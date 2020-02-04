import { IFuncionario } from 'app/shared/model/funcionario.model';
import { IFamilia } from 'app/shared/model/familia.model';

export interface IPessoa {
  id?: number;
  cpf?: string;
  nome?: string;
  email?: string;
  telefone?: string;
  funcionario?: IFuncionario;
  familia?: IFamilia;
}

export class Pessoa implements IPessoa {
  constructor(
    public id?: number,
    public cpf?: string,
    public nome?: string,
    public email?: string,
    public telefone?: string,
    public funcionario?: IFuncionario,
    public familia?: IFamilia
  ) {}
}
