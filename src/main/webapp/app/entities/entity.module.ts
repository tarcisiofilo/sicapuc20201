import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'mina-operacao',
        loadChildren: './mina-operacao/mina-operacao.module#Sicapuc20201MinaOperacaoModule'
      },
      {
        path: 'setor-mineracao',
        loadChildren: './setor-mineracao/setor-mineracao.module#Sicapuc20201SetorMineracaoModule'
      },
      {
        path: 'pessoa',
        loadChildren: './pessoa/pessoa.module#Sicapuc20201PessoaModule'
      },
      {
        path: 'funcionario',
        loadChildren: './funcionario/funcionario.module#Sicapuc20201FuncionarioModule'
      },
      {
        path: 'area-susceptivel',
        loadChildren: './area-susceptivel/area-susceptivel.module#Sicapuc20201AreaSusceptivelModule'
      },
      {
        path: 'familia',
        loadChildren: './familia/familia.module#Sicapuc20201FamiliaModule'
      },
      {
        path: 'ativo',
        loadChildren: './ativo/ativo.module#Sicapuc20201AtivoModule'
      },
      {
        path: 'sirene',
        loadChildren: './sirene/sirene.module#Sicapuc20201SireneModule'
      },
      {
        path: 'incidente',
        loadChildren: './incidente/incidente.module#Sicapuc20201IncidenteModule'
      },
      {
        path: 'vistoria',
        loadChildren: './vistoria/vistoria.module#Sicapuc20201VistoriaModule'
      },
      {
        path: 'instrumento-monitoramento',
        loadChildren: './instrumento-monitoramento/instrumento-monitoramento.module#Sicapuc20201InstrumentoMonitoramentoModule'
      },
      {
        path: 'medicao-instrumento',
        loadChildren: './medicao-instrumento/medicao-instrumento.module#Sicapuc20201MedicaoInstrumentoModule'
      },
      {
        path: 'notificacao',
        loadChildren: './notificacao/notificacao.module#Sicapuc20201NotificacaoModule'
      },
      {
        path: 'nivel-incidente',
        loadChildren: './nivel-incidente/nivel-incidente.module#Sicapuc20201NivelIncidenteModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Sicapuc20201EntityModule {}
