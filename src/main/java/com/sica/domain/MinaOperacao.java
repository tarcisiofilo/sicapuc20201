package com.sica.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.sica.domain.enumeration.TipoOperacao;

/**
 * A MinaOperacao.
 */
@Entity
@Table(name = "mina_operacao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MinaOperacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @NotNull
    @Column(name = "segmento", nullable = false)
    private String segmento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_operacao", nullable = false)
    private TipoOperacao tipoOperacao;

    @OneToMany(mappedBy = "minaOperacao")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SetorMineracao> setorMineracaos = new HashSet<>();

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

    public MinaOperacao nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSegmento() {
        return segmento;
    }

    public MinaOperacao segmento(String segmento) {
        this.segmento = segmento;
        return this;
    }

    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }

    public TipoOperacao getTipoOperacao() {
        return tipoOperacao;
    }

    public MinaOperacao tipoOperacao(TipoOperacao tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
        return this;
    }

    public void setTipoOperacao(TipoOperacao tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    public Set<SetorMineracao> getSetorMineracaos() {
        return setorMineracaos;
    }

    public MinaOperacao setorMineracaos(Set<SetorMineracao> setorMineracaos) {
        this.setorMineracaos = setorMineracaos;
        return this;
    }

    public MinaOperacao addSetorMineracao(SetorMineracao setorMineracao) {
        this.setorMineracaos.add(setorMineracao);
        setorMineracao.setMinaOperacao(this);
        return this;
    }

    public MinaOperacao removeSetorMineracao(SetorMineracao setorMineracao) {
        this.setorMineracaos.remove(setorMineracao);
        setorMineracao.setMinaOperacao(null);
        return this;
    }

    public void setSetorMineracaos(Set<SetorMineracao> setorMineracaos) {
        this.setorMineracaos = setorMineracaos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MinaOperacao)) {
            return false;
        }
        return id != null && id.equals(((MinaOperacao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MinaOperacao{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", segmento='" + getSegmento() + "'" +
            ", tipoOperacao='" + getTipoOperacao() + "'" +
            "}";
    }
}
