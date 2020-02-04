import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INivelIncidente } from 'app/shared/model/nivel-incidente.model';

@Component({
  selector: 'jhi-nivel-incidente-detail',
  templateUrl: './nivel-incidente-detail.component.html'
})
export class NivelIncidenteDetailComponent implements OnInit {
  nivelIncidente: INivelIncidente;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ nivelIncidente }) => {
      this.nivelIncidente = nivelIncidente;
    });
  }

  previousState() {
    window.history.back();
  }
}
