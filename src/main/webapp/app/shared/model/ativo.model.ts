import { ISetorMineracao } from 'app/shared/model/setor-mineracao.model';
import { IAreaSusceptivel } from 'app/shared/model/area-susceptivel.model';
import { IIncidente } from 'app/shared/model/incidente.model';

export interface IAtivo {
  id?: number;
  idTipoAtivo?: number;
  tipoAtivo?: string;
  periodicidadeDiasManutencao?: number;
  setorMineracao?: ISetorMineracao;
  areaSusceptivels?: IAreaSusceptivel[];
  incidente?: IIncidente;
}

export class Ativo implements IAtivo {
  constructor(
    public id?: number,
    public idTipoAtivo?: number,
    public tipoAtivo?: string,
    public periodicidadeDiasManutencao?: number,
    public setorMineracao?: ISetorMineracao,
    public areaSusceptivels?: IAreaSusceptivel[],
    public incidente?: IIncidente
  ) {}
}
