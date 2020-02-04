import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISirene } from 'app/shared/model/sirene.model';

type EntityResponseType = HttpResponse<ISirene>;
type EntityArrayResponseType = HttpResponse<ISirene[]>;

@Injectable({ providedIn: 'root' })
export class SireneService {
  public resourceUrl = SERVER_API_URL + 'api/sirenes';

  constructor(protected http: HttpClient) {}

  create(sirene: ISirene): Observable<EntityResponseType> {
    return this.http.post<ISirene>(this.resourceUrl, sirene, { observe: 'response' });
  }

  update(sirene: ISirene): Observable<EntityResponseType> {
    return this.http.put<ISirene>(this.resourceUrl, sirene, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISirene>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISirene[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
