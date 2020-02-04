import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { INivelIncidente } from 'app/shared/model/nivel-incidente.model';

type EntityResponseType = HttpResponse<INivelIncidente>;
type EntityArrayResponseType = HttpResponse<INivelIncidente[]>;

@Injectable({ providedIn: 'root' })
export class NivelIncidenteService {
  public resourceUrl = SERVER_API_URL + 'api/nivel-incidentes';

  constructor(protected http: HttpClient) {}

  create(nivelIncidente: INivelIncidente): Observable<EntityResponseType> {
    return this.http.post<INivelIncidente>(this.resourceUrl, nivelIncidente, { observe: 'response' });
  }

  update(nivelIncidente: INivelIncidente): Observable<EntityResponseType> {
    return this.http.put<INivelIncidente>(this.resourceUrl, nivelIncidente, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<INivelIncidente>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<INivelIncidente[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
