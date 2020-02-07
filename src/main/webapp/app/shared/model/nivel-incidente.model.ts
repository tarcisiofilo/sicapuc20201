export interface INivelIncidente {
  id?: number;
  nome?: string;
  notificaDNPM?: boolean;
  notificaEmail?: boolean;
  notificacaoSms?: boolean;
  notificacaoWhatsapp?: boolean;
  notificacaoDispositivoSeguranca?: boolean;
  notificaSirene?: boolean;
}

export class NivelIncidente implements INivelIncidente {
  constructor(
    public id?: number,
    public nome?: string,
    public notificaDNPM?: boolean,
    public notificaEmail?: boolean,
    public notificacaoSms?: boolean,
    public notificacaoWhatsapp?: boolean,
    public notificacaoDispositivoSeguranca?: boolean,
    public notificaSirene?: boolean
  ) {
    this.notificaDNPM = this.notificaDNPM || false;
    this.notificaEmail = this.notificaEmail || false;
    this.notificacaoSms = this.notificacaoSms || false;
    this.notificacaoWhatsapp = this.notificacaoWhatsapp || false;
    this.notificacaoDispositivoSeguranca = this.notificacaoDispositivoSeguranca || false;
    this.notificaSirene = this.notificaSirene || false;
  }
}
