import { Moment } from 'moment';

export const enum TipoNoficacao {
  DNPM = 'DNPM',
  EMAIL = 'EMAIL',
  SMS = 'SMS',
  WHATAPP = 'WHATAPP',
  DISPOSITIVO_SEGURANCA = 'DISPOSITIVO_SEGURANCA',
  SIRENE = 'SIRENE'
}

export interface INotificacao {
  id?: number;
  dataNotifacao?: Moment;
  tipoNotificacao?: TipoNoficacao;
  incidenteId?: number;
}

export class Notificacao implements INotificacao {
  constructor(public id?: number, public dataNotifacao?: Moment, public tipoNotificacao?: TipoNoficacao, public incidenteId?: number) {}
}
