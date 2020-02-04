import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IInstrumentoMonitoramento } from 'app/shared/model/instrumento-monitoramento.model';

type EntityResponseType = HttpResponse<IInstrumentoMonitoramento>;
type EntityArrayResponseType = HttpResponse<IInstrumentoMonitoramento[]>;

@Injectable({ providedIn: 'root' })
export class InstrumentoMonitoramentoService {
  public resourceUrl = SERVER_API_URL + 'api/instrumento-monitoramentos';

  constructor(protected http: HttpClient) {}

  create(instrumentoMonitoramento: IInstrumentoMonitoramento): Observable<EntityResponseType> {
    return this.http.post<IInstrumentoMonitoramento>(this.resourceUrl, instrumentoMonitoramento, { observe: 'response' });
  }

  update(instrumentoMonitoramento: IInstrumentoMonitoramento): Observable<EntityResponseType> {
    return this.http.put<IInstrumentoMonitoramento>(this.resourceUrl, instrumentoMonitoramento, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IInstrumentoMonitoramento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IInstrumentoMonitoramento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
