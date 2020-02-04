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
 * A AreaSusceptivel.
 */
@Entity
@Table(name = "area_susceptivel")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AreaSusceptivel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "identificacao", nullable = false)
    private String identificacao;

    @NotNull
    @Column(name = "nivel_proximidade", nullable = false)
    private Double nivelProximidade;

    @OneToMany(mappedBy = "areaSusceptivel")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Familia> familias = new HashSet<>();

    @OneToMany(mappedBy = "areaSusceptivel")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Sirene> sirenes = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("areaSusceptivels")
    private Ativo ativo;

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

    public AreaSusceptivel identificacao(String identificacao) {
        this.identificacao = identificacao;
        return this;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public Double getNivelProximidade() {
        return nivelProximidade;
    }

    public AreaSusceptivel nivelProximidade(Double nivelProximidade) {
        this.nivelProximidade = nivelProximidade;
        return this;
    }

    public void setNivelProximidade(Double nivelProximidade) {
        this.nivelProximidade = nivelProximidade;
    }

    public Set<Familia> getFamilias() {
        return familias;
    }

    public AreaSusceptivel familias(Set<Familia> familias) {
        this.familias = familias;
        return this;
    }

    public AreaSusceptivel addFamilia(Familia familia) {
        this.familias.add(familia);
        familia.setAreaSusceptivel(this);
        return this;
    }

    public AreaSusceptivel removeFamilia(Familia familia) {
        this.familias.remove(familia);
        familia.setAreaSusceptivel(null);
        return this;
    }

    public void setFamilias(Set<Familia> familias) {
        this.familias = familias;
    }

    public Set<Sirene> getSirenes() {
        return sirenes;
    }

    public AreaSusceptivel sirenes(Set<Sirene> sirenes) {
        this.sirenes = sirenes;
        return this;
    }

    public AreaSusceptivel addSirene(Sirene sirene) {
        this.sirenes.add(sirene);
        sirene.setAreaSusceptivel(this);
        return this;
    }

    public AreaSusceptivel removeSirene(Sirene sirene) {
        this.sirenes.remove(sirene);
        sirene.setAreaSusceptivel(null);
        return this;
    }

    public void setSirenes(Set<Sirene> sirenes) {
        this.sirenes = sirenes;
    }

    public Ativo getAtivo() {
        return ativo;
    }

    public AreaSusceptivel ativo(Ativo ativo) {
        this.ativo = ativo;
        return this;
    }

    public void setAtivo(Ativo ativo) {
        this.ativo = ativo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AreaSusceptivel)) {
            return false;
        }
        return id != null && id.equals(((AreaSusceptivel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AreaSusceptivel{" +
            "id=" + getId() +
            ", identificacao='" + getIdentificacao() + "'" +
            ", nivelProximidade=" + getNivelProximidade() +
            "}";
    }
}
