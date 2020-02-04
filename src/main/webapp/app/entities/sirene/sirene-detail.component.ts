import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISirene } from 'app/shared/model/sirene.model';

@Component({
  selector: 'jhi-sirene-detail',
  templateUrl: './sirene-detail.component.html'
})
export class SireneDetailComponent implements OnInit {
  sirene: ISirene;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ sirene }) => {
      this.sirene = sirene;
    });
  }

  previousState() {
    window.history.back();
  }
}
