import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMinaOperacao } from 'app/shared/model/mina-operacao.model';

@Component({
  selector: 'jhi-mina-operacao-detail',
  templateUrl: './mina-operacao-detail.component.html'
})
export class MinaOperacaoDetailComponent implements OnInit {
  minaOperacao: IMinaOperacao;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ minaOperacao }) => {
      this.minaOperacao = minaOperacao;
    });
  }

  previousState() {
    window.history.back();
  }
}
