package com.sica.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A SetorMineracao.
 */
@Entity
@Table(name = "setor_mineracao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SetorMineracao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @OneToOne
    @JoinColumn(unique = true)
    private Funcionario diretor;

    @OneToOne
    @JoinColumn(unique = true)
    private Funcionario gerente;

    @OneToMany(mappedBy = "setorMineracao")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Funcionario> funcionarios = new HashSet<>();

    @OneToOne(mappedBy = "setorMineracao")
    @JsonIgnore
    private Ativo ativo;

    @ManyToOne
    @JsonIgnoreProperties("setorMineracaos")
    private MinaOperacao minaOperacao;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public SetorMineracao nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Funcionario getDiretor() {
        return diretor;
    }

    public SetorMineracao diretor(Funcionario funcionario) {
        this.diretor = funcionario;
        return this;
    }

    public void setDiretor(Funcionario funcionario) {
        this.diretor = funcionario;
    }

    public Funcionario getGerente() {
        return gerente;
    }

    public SetorMineracao gerente(Funcionario funcionario) {
        this.gerente = funcionario;
        return this;
    }

    public void setGerente(Funcionario funcionario) {
        this.gerente = funcionario;
    }

    public Set<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public SetorMineracao funcionarios(Set<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
        return this;
    }

    public SetorMineracao addFuncionarios(Funcionario funcionario) {
        this.funcionarios.add(funcionario);
        funcionario.setSetorMineracao(this);
        return this;
    }

    public SetorMineracao removeFuncionarios(Funcionario funcionario) {
        this.funcionarios.remove(funcionario);
        funcionario.setSetorMineracao(null);
        return this;
    }

    public void setFuncionarios(Set<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public Ativo getAtivo() {
        return ativo;
    }

    public SetorMineracao ativo(Ativo ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Ativo ativo) {
        this.ativo = ativo;
    }

    public MinaOperacao getMinaOperacao() {
        return minaOperacao;
    }

    public SetorMineracao minaOperacao(MinaOperacao minaOperacao) {
        this.minaOperacao = minaOperacao;
        return this;
    }

    public void setMinaOperacao(MinaOperacao minaOperacao) {
        this.minaOperacao = minaOperacao;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SetorMineracao)) {
            return false;
        }
        return id != null && id.equals(((SetorMineracao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SetorMineracao{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
