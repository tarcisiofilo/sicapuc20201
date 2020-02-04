import { Moment } from 'moment';
import { IInstrumentoMonitoramento } from 'app/shared/model/instrumento-monitoramento.model';

export interface IVistoria {
  id?: number;
  identificao?: string;
  data?: Moment;
  valor?: number;
  instrumentoMonitoramento?: IInstrumentoMonitoramento;
}

export class Vistoria implements IVistoria {
  constructor(
    public id?: number,
    public identificao?: string,
    public data?: Moment,
    public valor?: number,
    public instrumentoMonitoramento?: IInstrumentoMonitoramento
  ) {}
}
