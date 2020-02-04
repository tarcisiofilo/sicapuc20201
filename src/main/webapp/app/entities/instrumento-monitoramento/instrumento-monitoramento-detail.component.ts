import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IInstrumentoMonitoramento } from 'app/shared/model/instrumento-monitoramento.model';

@Component({
  selector: 'jhi-instrumento-monitoramento-detail',
  templateUrl: './instrumento-monitoramento-detail.component.html'
})
export class InstrumentoMonitoramentoDetailComponent implements OnInit {
  instrumentoMonitoramento: IInstrumentoMonitoramento;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ instrumentoMonitoramento }) => {
      this.instrumentoMonitoramento = instrumentoMonitoramento;
    });
  }

  previousState() {
    window.history.back();
  }
}
