import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAreaSusceptivel } from 'app/shared/model/area-susceptivel.model';

@Component({
  selector: 'jhi-area-susceptivel-detail',
  templateUrl: './area-susceptivel-detail.component.html'
})
export class AreaSusceptivelDetailComponent implements OnInit {
  areaSusceptivel: IAreaSusceptivel;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ areaSusceptivel }) => {
      this.areaSusceptivel = areaSusceptivel;
    });
  }

  previousState() {
    window.history.back();
  }
}
