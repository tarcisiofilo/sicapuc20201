import { IFamilia } from 'app/shared/model/familia.model';
import { ISirene } from 'app/shared/model/sirene.model';

export interface IAreaSusceptivel {
  id?: number;
  identificacao?: string;
  nivelProximidade?: number;
  familias?: IFamilia[];
  sirenes?: ISirene[];
  ativoId?: number;
}

export class AreaSusceptivel implements IAreaSusceptivel {
  constructor(
    public id?: number,
    public identificacao?: string,
    public nivelProximidade?: number,
    public familias?: IFamilia[],
    public sirenes?: ISirene[],
    public ativoId?: number
  ) {}
}
