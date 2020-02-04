import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMedicaoInstrumento } from 'app/shared/model/medicao-instrumento.model';

@Component({
  selector: 'jhi-medicao-instrumento-detail',
  templateUrl: './medicao-instrumento-detail.component.html'
})
export class MedicaoInstrumentoDetailComponent implements OnInit {
  medicaoInstrumento: IMedicaoInstrumento;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ medicaoInstrumento }) => {
      this.medicaoInstrumento = medicaoInstrumento;
    });
  }

  previousState() {
    window.history.back();
  }
}
