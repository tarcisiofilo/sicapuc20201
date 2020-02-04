import { IIncidente } from 'app/shared/model/incidente.model';

export interface INivelIncidente {
  id?: number;
  nome?: string;
  notificaDNPM?: boolean;
  notificaEmail?: boolean;
  notificacaoSmsWhatsapp?: boolean;
  notificacaoDispositivoSeguranca?: boolean;
  notificaSirene?: boolean;
  incidente?: IIncidente;
}

export class NivelIncidente implements INivelIncidente {
  constructor(
    public id?: number,
    public nome?: string,
    public notificaDNPM?: boolean,
    public notificaEmail?: boolean,
    public notificacaoSmsWhatsapp?: boolean,
    public notificacaoDispositivoSeguranca?: boolean,
    public notificaSirene?: boolean,
    public incidente?: IIncidente
  ) {
    this.notificaDNPM = this.notificaDNPM || false;
    this.notificaEmail = this.notificaEmail || false;
    this.notificacaoSmsWhatsapp = this.notificacaoSmsWhatsapp || false;
    this.notificacaoDispositivoSeguranca = this.notificacaoDispositivoSeguranca || false;
    this.notificaSirene = this.notificaSirene || false;
  }
}
