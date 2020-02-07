import { IAreaSusceptivel } from 'app/shared/model/area-susceptivel.model';
import { IIncidente } from 'app/shared/model/incidente.model';

export interface IAtivo {
  id?: number;
  nome?: string;
  setorMineracaoId?: number;
  areaSusceptivels?: IAreaSusceptivel[];
  incidentes?: IIncidente[];
}

export class Ativo implements IAtivo {
  constructor(
    public id?: number,
    public nome?: string,
    public setorMineracaoId?: number,
    public areaSusceptivels?: IAreaSusceptivel[],
    public incidentes?: IIncidente[]
  ) {}
}
