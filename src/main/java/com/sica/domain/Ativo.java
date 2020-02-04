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
 * A Ativo.
 */
@Entity
@Table(name = "ativo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ativo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "id_tipo_ativo", nullable = false)
    private Long idTipoAtivo;

    @NotNull
    @Column(name = "tipo_ativo", nullable = false)
    private String tipoAtivo;

    @NotNull
    @Column(name = "periodicidade_dias_manutencao", nullable = false)
    private Long periodicidadeDiasManutencao;

    @OneToOne
    @JoinColumn(unique = true)
    private SetorMineracao setorMineracao;

    @OneToMany(mappedBy = "ativo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AreaSusceptivel> areaSusceptivels = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("ativos")
    private Incidente incidente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdTipoAtivo() {
        return idTipoAtivo;
    }

    public Ativo idTipoAtivo(Long idTipoAtivo) {
        this.idTipoAtivo = idTipoAtivo;
        return this;
    }

    public void setIdTipoAtivo(Long idTipoAtivo) {
        this.idTipoAtivo = idTipoAtivo;
    }

    public String getTipoAtivo() {
        return tipoAtivo;
    }

    public Ativo tipoAtivo(String tipoAtivo) {
        this.tipoAtivo = tipoAtivo;
        return this;
    }

    public void setTipoAtivo(String tipoAtivo) {
        this.tipoAtivo = tipoAtivo;
    }

    public Long getPeriodicidadeDiasManutencao() {
        return periodicidadeDiasManutencao;
    }

    public Ativo periodicidadeDiasManutencao(Long periodicidadeDiasManutencao) {
        this.periodicidadeDiasManutencao = periodicidadeDiasManutencao;
        return this;
    }

    public void setPeriodicidadeDiasManutencao(Long periodicidadeDiasManutencao) {
        this.periodicidadeDiasManutencao = periodicidadeDiasManutencao;
    }

    public SetorMineracao getSetorMineracao() {
        return setorMineracao;
    }

    public Ativo setorMineracao(SetorMineracao setorMineracao) {
        this.setorMineracao = setorMineracao;
        return this;
    }

    public void setSetorMineracao(SetorMineracao setorMineracao) {
        this.setorMineracao = setorMineracao;
    }

    public Set<AreaSusceptivel> getAreaSusceptivels() {
        return areaSusceptivels;
    }

    public Ativo areaSusceptivels(Set<AreaSusceptivel> areaSusceptivels) {
        this.areaSusceptivels = areaSusceptivels;
        return this;
    }

    public Ativo addAreaSusceptivel(AreaSusceptivel areaSusceptivel) {
        this.areaSusceptivels.add(areaSusceptivel);
        areaSusceptivel.setAtivo(this);
        return this;
    }

    public Ativo removeAreaSusceptivel(AreaSusceptivel areaSusceptivel) {
        this.areaSusceptivels.remove(areaSusceptivel);
        areaSusceptivel.setAtivo(null);
        return this;
    }

    public void setAreaSusceptivels(Set<AreaSusceptivel> areaSusceptivels) {
        this.areaSusceptivels = areaSusceptivels;
    }

    public Incidente getIncidente() {
        return incidente;
    }

    public Ativo incidente(Incidente incidente) {
        this.incidente = incidente;
        return this;
    }

    public void setIncidente(Incidente incidente) {
        this.incidente = incidente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ativo)) {
            return false;
        }
        return id != null && id.equals(((Ativo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Ativo{" +
            "id=" + getId() +
            ", idTipoAtivo=" + getIdTipoAtivo() +
            ", tipoAtivo='" + getTipoAtivo() + "'" +
            ", periodicidadeDiasManutencao=" + getPeriodicidadeDiasManutencao() +
            "}";
    }
}
