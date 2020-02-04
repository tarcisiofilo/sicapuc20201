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
 * A Familia.
 */
@Entity
@Table(name = "familia")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Familia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "identificacao", nullable = false)
    private String identificacao;

    @OneToMany(mappedBy = "familia")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Pessoa> pessoas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("familias")
    private AreaSusceptivel areaSusceptivel;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public Familia identificacao(String identificacao) {
        this.identificacao = identificacao;
        return this;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public Set<Pessoa> getPessoas() {
        return pessoas;
    }

    public Familia pessoas(Set<Pessoa> pessoas) {
        this.pessoas = pessoas;
        return this;
    }

    public Familia addPessoa(Pessoa pessoa) {
        this.pessoas.add(pessoa);
        pessoa.setFamilia(this);
        return this;
    }

    public Familia removePessoa(Pessoa pessoa) {
        this.pessoas.remove(pessoa);
        pessoa.setFamilia(null);
        return this;
    }

    public void setPessoas(Set<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

    public AreaSusceptivel getAreaSusceptivel() {
        return areaSusceptivel;
    }

    public Familia areaSusceptivel(AreaSusceptivel areaSusceptivel) {
        this.areaSusceptivel = areaSusceptivel;
        return this;
    }

    public void setAreaSusceptivel(AreaSusceptivel areaSusceptivel) {
        this.areaSusceptivel = areaSusceptivel;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Familia)) {
            return false;
        }
        return id != null && id.equals(((Familia) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Familia{" +
            "id=" + getId() +
            ", identificacao='" + getIdentificacao() + "'" +
            "}";
    }
}
