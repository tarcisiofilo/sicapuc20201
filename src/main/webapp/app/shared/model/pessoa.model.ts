export interface IPessoa {
  id?: number;
  cpf?: string;
  nome?: string;
  email?: string;
  telefone?: string;
  familiaId?: number;
}

export class Pessoa implements IPessoa {
  constructor(
    public id?: number,
    public cpf?: string,
    public nome?: string,
    public email?: string,
    public telefone?: string,
    public familiaId?: number
  ) {}
}
