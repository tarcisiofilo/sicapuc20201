import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVistoria } from 'app/shared/model/vistoria.model';

type EntityResponseType = HttpResponse<IVistoria>;
type EntityArrayResponseType = HttpResponse<IVistoria[]>;

@Injectable({ providedIn: 'root' })
export class VistoriaService {
  public resourceUrl = SERVER_API_URL + 'api/vistorias';

  constructor(protected http: HttpClient) {}

  create(vistoria: IVistoria): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vistoria);
    return this.http
      .post<IVistoria>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(vistoria: IVistoria): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(vistoria);
    return this.http
      .put<IVistoria>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IVistoria>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IVistoria[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(vistoria: IVistoria): IVistoria {
    const copy: IVistoria = Object.assign({}, vistoria, {
      data: vistoria.data != null && vistoria.data.isValid() ? vistoria.data.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.data = res.body.data != null ? moment(res.body.data) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((vistoria: IVistoria) => {
        vistoria.data = vistoria.data != null ? moment(vistoria.data) : null;
      });
    }
    return res;
  }
}
