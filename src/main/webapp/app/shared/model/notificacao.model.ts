import { Moment } from 'moment';
import { IIncidente } from 'app/shared/model/incidente.model';

export const enum TipoNoficacao {
  DNPM = 'DNPM',
  EMAIL = 'EMAIL',
  SMS_WHATAPP = 'SMS_WHATAPP',
  DISPOSITIVO_SEGURANCA = 'DISPOSITIVO_SEGURANCA',
  SIRENE = 'SIRENE'
}

export interface INotificacao {
  id?: number;
  dataNotifacao?: Moment;
  tipoNotificacao?: TipoNoficacao;
  incidente?: IIncidente;
}

export class Notificacao implements INotificacao {
  constructor(public id?: number, public dataNotifacao?: Moment, public tipoNotificacao?: TipoNoficacao, public incidente?: IIncidente) {}
}
