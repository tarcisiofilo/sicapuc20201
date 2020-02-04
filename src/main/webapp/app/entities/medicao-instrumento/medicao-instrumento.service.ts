import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMedicaoInstrumento } from 'app/shared/model/medicao-instrumento.model';

type EntityResponseType = HttpResponse<IMedicaoInstrumento>;
type EntityArrayResponseType = HttpResponse<IMedicaoInstrumento[]>;

@Injectable({ providedIn: 'root' })
export class MedicaoInstrumentoService {
  public resourceUrl = SERVER_API_URL + 'api/medicao-instrumentos';

  constructor(protected http: HttpClient) {}

  create(medicaoInstrumento: IMedicaoInstrumento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(medicaoInstrumento);
    return this.http
      .post<IMedicaoInstrumento>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(medicaoInstrumento: IMedicaoInstrumento): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(medicaoInstrumento);
    return this.http
      .put<IMedicaoInstrumento>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMedicaoInstrumento>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMedicaoInstrumento[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(medicaoInstrumento: IMedicaoInstrumento): IMedicaoInstrumento {
    const copy: IMedicaoInstrumento = Object.assign({}, medicaoInstrumento, {
      data: medicaoInstrumento.data != null && medicaoInstrumento.data.isValid() ? medicaoInstrumento.data.toJSON() : null
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
      res.body.forEach((medicaoInstrumento: IMedicaoInstrumento) => {
        medicaoInstrumento.data = medicaoInstrumento.data != null ? moment(medicaoInstrumento.data) : null;
      });
    }
    return res;
  }
}
