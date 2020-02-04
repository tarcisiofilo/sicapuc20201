import { Moment } from 'moment';
import { IIncidente } from 'app/shared/model/incidente.model';
import { IInstrumentoMonitoramento } from 'app/shared/model/instrumento-monitoramento.model';

export interface IMedicaoInstrumento {
  id?: number;
  data?: Moment;
  valor?: number;
  incidente?: IIncidente;
  instrumentoMonitoramento?: IInstrumentoMonitoramento;
}

export class MedicaoInstrumento implements IMedicaoInstrumento {
  constructor(
    public id?: number,
    public data?: Moment,
    public valor?: number,
    public incidente?: IIncidente,
    public instrumentoMonitoramento?: IInstrumentoMonitoramento
  ) {}
}
