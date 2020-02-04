package com.sica.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Sirene.
 */
@Entity
@Table(name = "sirene")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Sirene implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "identificacao", nullable = false)
    private String identificacao;

    @NotNull
    @Column(name = "url_ativar", nullable = false)
    private String urlAtivar;

    @ManyToOne
    @JsonIgnoreProperties("sirenes")
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

    public Sirene identificacao(String identificacao) {
        this.identificacao = identificacao;
        return this;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public String getUrlAtivar() {
        return urlAtivar;
    }

    public Sirene urlAtivar(String urlAtivar) {
        this.urlAtivar = urlAtivar;
        return this;
    }

    public void setUrlAtivar(String urlAtivar) {
        this.urlAtivar = urlAtivar;
    }

    public AreaSusceptivel getAreaSusceptivel() {
        return areaSusceptivel;
    }

    public Sirene areaSusceptivel(AreaSusceptivel areaSusceptivel) {
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
        if (!(o instanceof Sirene)) {
            return false;
        }
        return id != null && id.equals(((Sirene) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Sirene{" +
            "id=" + getId() +
            ", identificacao='" + getIdentificacao() + "'" +
            ", urlAtivar='" + getUrlAtivar() + "'" +
            "}";
    }
}
