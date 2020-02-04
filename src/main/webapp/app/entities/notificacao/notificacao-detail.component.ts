import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INotificacao } from 'app/shared/model/notificacao.model';

@Component({
  selector: 'jhi-notificacao-detail',
  templateUrl: './notificacao-detail.component.html'
})
export class NotificacaoDetailComponent implements OnInit {
  notificacao: INotificacao;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ notificacao }) => {
      this.notificacao = notificacao;
    });
  }

  previousState() {
    window.history.back();
  }
}
