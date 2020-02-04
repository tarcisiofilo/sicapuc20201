import { ISetorMineracao } from 'app/shared/model/setor-mineracao.model';

export const enum TipoOperacao {
  MINERIO_FERRO_PELOTAS = 'MINERIO_FERRO_PELOTAS',
  NIQUEL = 'NIQUEL',
  MANGANES_FERROLIGAS = 'MANGANES_FERROLIGAS',
  CARVAO_COBRE = 'CARVAO_COBRE'
}

export interface IMinaOperacao {
  id?: number;
  nome?: string;
  segmento?: string;
  tipoOperacao?: TipoOperacao;
  setorMineracaos?: ISetorMineracao[];
}

export class MinaOperacao implements IMinaOperacao {
  constructor(
    public id?: number,
    public nome?: string,
    public segmento?: string,
    public tipoOperacao?: TipoOperacao,
    public setorMineracaos?: ISetorMineracao[]
  ) {}
}
