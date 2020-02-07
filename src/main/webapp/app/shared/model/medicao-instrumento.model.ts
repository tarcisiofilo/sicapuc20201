import { Moment } from 'moment';

export interface IMedicaoInstrumento {
  id?: number;
  data?: Moment;
  valor?: number;
  incidenteId?: number;
  instrumentoMonitoramentoId?: number;
}

export class MedicaoInstrumento implements IMedicaoInstrumento {
  constructor(
    public id?: number,
    public data?: Moment,
    public valor?: number,
    public incidenteId?: number,
    public instrumentoMonitoramentoId?: number
  ) {}
}
