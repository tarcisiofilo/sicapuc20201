import { Moment } from 'moment';

export interface IVistoria {
  id?: number;
  identificao?: string;
  data?: Moment;
  valor?: number;
  instrumentoMonitoramentoId?: number;
}

export class Vistoria implements IVistoria {
  constructor(
    public id?: number,
    public identificao?: string,
    public data?: Moment,
    public valor?: number,
    public instrumentoMonitoramentoId?: number
  ) {}
}
