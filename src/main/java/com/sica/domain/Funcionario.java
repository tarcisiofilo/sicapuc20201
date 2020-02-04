package com.sica.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Funcionario.
 */
@Entity
@Table(name = "funcionario")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Funcionario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "cargo", nullable = false)
    private String cargo;

    @NotNull
    @Column(name = "id_dispositivo_monitoramento", nullable = false)
    private Long idDispositivoMonitoramento;

    @ManyToOne
    @JsonIgnoreProperties("funcionarios")
    private SetorMineracao setorMineracao;

    @OneToOne
    @JoinColumn(unique = true)
    private Pessoa pessoa;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCargo() {
        return cargo;
    }

    public Funcionario cargo(String cargo) {
        this.cargo = cargo;
        return this;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Long getIdDispositivoMonitoramento() {
        return idDispositivoMonitoramento;
    }

    public Funcionario idDispositivoMonitoramento(Long idDispositivoMonitoramento) {
        this.idDispositivoMonitoramento = idDispositivoMonitoramento;
        return this;
    }

    public void setIdDispositivoMonitoramento(Long idDispositivoMonitoramento) {
        this.idDispositivoMonitoramento = idDispositivoMonitoramento;
    }

    public SetorMineracao getSetorMineracao() {
        return setorMineracao;
    }

    public Funcionario setorMineracao(SetorMineracao setorMineracao) {
        this.setorMineracao = setorMineracao;
        return this;
    }

    public void setSetorMineracao(SetorMineracao setorMineracao) {
        this.setorMineracao = setorMineracao;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public Funcionario pessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Funcionario)) {
            return false;
        }
        return id != null && id.equals(((Funcionario) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Funcionario{" +
            "id=" + getId() +
            ", cargo='" + getCargo() + "'" +
            ", idDispositivoMonitoramento=" + getIdDispositivoMonitoramento() +
            "}";
    }
}
