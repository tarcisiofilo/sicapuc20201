import { IPessoa } from 'app/shared/model/pessoa.model';
import { IAreaSusceptivel } from 'app/shared/model/area-susceptivel.model';

export interface IFamilia {
  id?: number;
  identificacao?: string;
  pessoas?: IPessoa[];
  areaSusceptivel?: IAreaSusceptivel;
}

export class Familia implements IFamilia {
  constructor(public id?: number, public identificacao?: string, public pessoas?: IPessoa[], public areaSusceptivel?: IAreaSusceptivel) {}
}
