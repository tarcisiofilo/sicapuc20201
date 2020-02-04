import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFamilia } from 'app/shared/model/familia.model';

type EntityResponseType = HttpResponse<IFamilia>;
type EntityArrayResponseType = HttpResponse<IFamilia[]>;

@Injectable({ providedIn: 'root' })
export class FamiliaService {
  public resourceUrl = SERVER_API_URL + 'api/familias';

  constructor(protected http: HttpClient) {}

  create(familia: IFamilia): Observable<EntityResponseType> {
    return this.http.post<IFamilia>(this.resourceUrl, familia, { observe: 'response' });
  }

  update(familia: IFamilia): Observable<EntityResponseType> {
    return this.http.put<IFamilia>(this.resourceUrl, familia, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFamilia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFamilia[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
