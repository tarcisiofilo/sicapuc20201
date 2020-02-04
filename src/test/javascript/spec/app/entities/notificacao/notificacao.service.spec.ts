/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { NotificacaoService } from 'app/entities/notificacao/notificacao.service';
import { INotificacao, Notificacao, TipoNoficacao } from 'app/shared/model/notificacao.model';

describe('Service Tests', () => {
  describe('Notificacao Service', () => {
    let injector: TestBed;
    let service: NotificacaoService;
    let httpMock: HttpTestingController;
    let elemDefault: INotificacao;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(NotificacaoService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Notificacao(0, currentDate, TipoNoficacao.DNPM);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            dataNotifacao: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Notificacao', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataNotifacao: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataNotifacao: currentDate
          },
          returnedFromService
        );
        service
          .create(new Notificacao(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Notificacao', async () => {
        const returnedFromService = Object.assign(
          {
            dataNotifacao: currentDate.format(DATE_TIME_FORMAT),
            tipoNotificacao: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataNotifacao: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Notificacao', async () => {
        const returnedFromService = Object.assign(
          {
            dataNotifacao: currentDate.format(DATE_TIME_FORMAT),
            tipoNotificacao: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataNotifacao: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Notificacao', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
