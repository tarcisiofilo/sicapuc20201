import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAtivo } from 'app/shared/model/ativo.model';

@Component({
  selector: 'jhi-ativo-detail',
  templateUrl: './ativo-detail.component.html'
})
export class AtivoDetailComponent implements OnInit {
  ativo: IAtivo;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ ativo }) => {
      this.ativo = ativo;
    });
  }

  previousState() {
    window.history.back();
  }
}
