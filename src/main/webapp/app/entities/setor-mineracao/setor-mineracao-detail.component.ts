import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISetorMineracao } from 'app/shared/model/setor-mineracao.model';

@Component({
  selector: 'jhi-setor-mineracao-detail',
  templateUrl: './setor-mineracao-detail.component.html'
})
export class SetorMineracaoDetailComponent implements OnInit {
  setorMineracao: ISetorMineracao;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ setorMineracao }) => {
      this.setorMineracao = setorMineracao;
    });
  }

  previousState() {
    window.history.back();
  }
}
