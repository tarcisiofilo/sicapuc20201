import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { INotificacao } from 'app/shared/model/notificacao.model';

type EntityResponseType = HttpResponse<INotificacao>;
type EntityArrayResponseType = HttpResponse<INotificacao[]>;

@Injectable({ providedIn: 'root' })
export class NotificacaoService {
  public resourceUrl = SERVER_API_URL + 'api/notificacaos';

  constructor(protected http: HttpClient) {}

  create(notificacao: INotificacao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(notificacao);
    return this.http
      .post<INotificacao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(notificacao: INotificacao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(notificacao);
    return this.http
      .put<INotificacao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<INotificacao>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<INotificacao[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(notificacao: INotificacao): INotificacao {
    const copy: INotificacao = Object.assign({}, notificacao, {
      dataNotifacao: notificacao.dataNotifacao != null && notificacao.dataNotifacao.isValid() ? notificacao.dataNotifacao.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dataNotifacao = res.body.dataNotifacao != null ? moment(res.body.dataNotifacao) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((notificacao: INotificacao) => {
        notificacao.dataNotifacao = notificacao.dataNotifacao != null ? moment(notificacao.dataNotifacao) : null;
      });
    }
    return res;
  }
}
