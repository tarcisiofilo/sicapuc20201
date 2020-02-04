import { IAreaSusceptivel } from 'app/shared/model/area-susceptivel.model';

export interface ISirene {
  id?: number;
  identificacao?: string;
  urlAtivar?: string;
  areaSusceptivel?: IAreaSusceptivel;
}

export class Sirene implements ISirene {
  constructor(public id?: number, public identificacao?: string, public urlAtivar?: string, public areaSusceptivel?: IAreaSusceptivel) {}
}
