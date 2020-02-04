import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAreaSusceptivel } from 'app/shared/model/area-susceptivel.model';

type EntityResponseType = HttpResponse<IAreaSusceptivel>;
type EntityArrayResponseType = HttpResponse<IAreaSusceptivel[]>;

@Injectable({ providedIn: 'root' })
export class AreaSusceptivelService {
  public resourceUrl = SERVER_API_URL + 'api/area-susceptivels';

  constructor(protected http: HttpClient) {}

  create(areaSusceptivel: IAreaSusceptivel): Observable<EntityResponseType> {
    return this.http.post<IAreaSusceptivel>(this.resourceUrl, areaSusceptivel, { observe: 'response' });
  }

  update(areaSusceptivel: IAreaSusceptivel): Observable<EntityResponseType> {
    return this.http.put<IAreaSusceptivel>(this.resourceUrl, areaSusceptivel, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAreaSusceptivel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAreaSusceptivel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
