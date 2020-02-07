import { IMedicaoInstrumento } from 'app/shared/model/medicao-instrumento.model';

export const enum TipoInstrumentoMonitoramento {
  PIEZOMETRO = 'PIEZOMETRO',
  MEDIDOR_NIVEL_AGUA = 'MEDIDOR_NIVEL_AGUA',
  INCLINOMETRO = 'INCLINOMETRO'
}

export const enum TipoMedicao {
  MANUAL = 'MANUAL',
  API = 'API'
}

export interface IInstrumentoMonitoramento {
  id?: number;
  identificao?: string;
  tipoInstrumentoMonitoramento?: TipoInstrumentoMonitoramento;
  tipoMedicao?: TipoMedicao;
  urlGetMedicao?: string;
  intervaloMedicaoAPI?: number;
  varianciaTolerada?: number;
  limiteSuperior?: number;
  medicaoInstrumentos?: IMedicaoInstrumento[];
  vistoriaId?: number;
}

export class InstrumentoMonitoramento implements IInstrumentoMonitoramento {
  constructor(
    public id?: number,
    public identificao?: string,
    public tipoInstrumentoMonitoramento?: TipoInstrumentoMonitoramento,
    public tipoMedicao?: TipoMedicao,
    public urlGetMedicao?: string,
    public intervaloMedicaoAPI?: number,
    public varianciaTolerada?: number,
    public limiteSuperior?: number,
    public medicaoInstrumentos?: IMedicaoInstrumento[],
    public vistoriaId?: number
  ) {}
}
