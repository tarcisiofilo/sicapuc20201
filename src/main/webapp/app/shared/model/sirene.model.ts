export interface ISirene {
  id?: number;
  identificacao?: string;
  urlAtivar?: string;
  areaSusceptivelId?: number;
}

export class Sirene implements ISirene {
  constructor(public id?: number, public identificacao?: string, public urlAtivar?: string, public areaSusceptivelId?: number) {}
}
