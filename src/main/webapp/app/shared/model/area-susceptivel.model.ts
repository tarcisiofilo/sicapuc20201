import { IFamilia } from 'app/shared/model/familia.model';
import { ISirene } from 'app/shared/model/sirene.model';
import { IAtivo } from 'app/shared/model/ativo.model';

export interface IAreaSusceptivel {
  id?: number;
  identificacao?: string;
  nivelProximidade?: number;
  familias?: IFamilia[];
  sirenes?: ISirene[];
  ativo?: IAtivo;
}

export class AreaSusceptivel implements IAreaSusceptivel {
  constructor(
    public id?: number,
    public identificacao?: string,
    public nivelProximidade?: number,
    public familias?: IFamilia[],
    public sirenes?: ISirene[],
    public ativo?: IAtivo
  ) {}
}
