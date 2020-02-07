import { IAtivo } from 'app/shared/model/ativo.model';

export const enum OrigemIncidente {
  MONITORAMENTO_SENSORES = 'MONITORAMENTO_SENSORES',
  MANUAL = 'MANUAL'
}

export interface IIncidente {
  id?: number;
  identificacao?: string;
  origem?: OrigemIncidente;
  mensagem?: string;
  medicaoInstrumentoId?: number;
  nivelIncidenteId?: number;
  ativos?: IAtivo[];
}

export class Incidente implements IIncidente {
  constructor(
    public id?: number,
    public identificacao?: string,
    public origem?: OrigemIncidente,
    public mensagem?: string,
    public medicaoInstrumentoId?: number,
    public nivelIncidenteId?: number,
    public ativos?: IAtivo[]
  ) {}
}
