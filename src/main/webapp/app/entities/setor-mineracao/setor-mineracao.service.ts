import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISetorMineracao } from 'app/shared/model/setor-mineracao.model';

type EntityResponseType = HttpResponse<ISetorMineracao>;
type EntityArrayResponseType = HttpResponse<ISetorMineracao[]>;

@Injectable({ providedIn: 'root' })
export class SetorMineracaoService {
  public resourceUrl = SERVER_API_URL + 'api/setor-mineracaos';

  constructor(protected http: HttpClient) {}

  create(setorMineracao: ISetorMineracao): Observable<EntityResponseType> {
    return this.http.post<ISetorMineracao>(this.resourceUrl, setorMineracao, { observe: 'response' });
  }

  update(setorMineracao: ISetorMineracao): Observable<EntityResponseType> {
    return this.http.put<ISetorMineracao>(this.resourceUrl, setorMineracao, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISetorMineracao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISetorMineracao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
