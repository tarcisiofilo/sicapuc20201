import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIncidente } from 'app/shared/model/incidente.model';

@Component({
  selector: 'jhi-incidente-detail',
  templateUrl: './incidente-detail.component.html'
})
export class IncidenteDetailComponent implements OnInit {
  incidente: IIncidente;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ incidente }) => {
      this.incidente = incidente;
    });
  }

  previousState() {
    window.history.back();
  }
}
