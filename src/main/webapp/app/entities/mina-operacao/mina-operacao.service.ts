import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMinaOperacao } from 'app/shared/model/mina-operacao.model';

type EntityResponseType = HttpResponse<IMinaOperacao>;
type EntityArrayResponseType = HttpResponse<IMinaOperacao[]>;

@Injectable({ providedIn: 'root' })
export class MinaOperacaoService {
  public resourceUrl = SERVER_API_URL + 'api/mina-operacaos';

  constructor(protected http: HttpClient) {}

  create(minaOperacao: IMinaOperacao): Observable<EntityResponseType> {
    return this.http.post<IMinaOperacao>(this.resourceUrl, minaOperacao, { observe: 'response' });
  }

  update(minaOperacao: IMinaOperacao): Observable<EntityResponseType> {
    return this.http.put<IMinaOperacao>(this.resourceUrl, minaOperacao, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMinaOperacao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMinaOperacao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
