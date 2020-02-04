import { IMedicaoInstrumento } from 'app/shared/model/medicao-instrumento.model';
import { INivelIncidente } from 'app/shared/model/nivel-incidente.model';
import { INotificacao } from 'app/shared/model/notificacao.model';
import { IAtivo } from 'app/shared/model/ativo.model';

export const enum OrigemIncidente {
  MONITORAMENTO_SENSORES = 'MONITORAMENTO_SENSORES',
  MANUAL = 'MANUAL'
}

export interface IIncidente {
  id?: number;
  identificacao?: string;
  origem?: OrigemIncidente;
  medicaoInstrumento?: IMedicaoInstrumento;
  nivelIncidente?: INivelIncidente;
  notificacaos?: INotificacao[];
  ativos?: IAtivo[];
}

export class Incidente implements IIncidente {
  constructor(
    public id?: number,
    public identificacao?: string,
    public origem?: OrigemIncidente,
    public medicaoInstrumento?: IMedicaoInstrumento,
    public nivelIncidente?: INivelIncidente,
    public notificacaos?: INotificacao[],
    public ativos?: IAtivo[]
  ) {}
}
