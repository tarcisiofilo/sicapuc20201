import { IPessoa } from 'app/shared/model/pessoa.model';

export interface IFamilia {
  id?: number;
  identificacao?: string;
  pessoas?: IPessoa[];
  areaSusceptivelId?: number;
}

export class Familia implements IFamilia {
  constructor(public id?: number, public identificacao?: string, public pessoas?: IPessoa[], public areaSusceptivelId?: number) {}
}
