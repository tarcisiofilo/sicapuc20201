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

import com.sica.domain.enumeration.OrigemIncidente;

/**
 * A Incidente.
 */
@Entity
@Table(name = "incidente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Incidente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "identificacao", nullable = false)
    private String identificacao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "origem", nullable = false)
    private OrigemIncidente origem;

    @OneToOne
    @JoinColumn(unique = true)
    private MedicaoInstrumento medicaoInstrumento;

    @OneToOne
    @JoinColumn(unique = true)
    private NivelIncidente nivelIncidente;

    @OneToMany(mappedBy = "incidente")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Notificacao> notificacaos = new HashSet<>();

    @OneToMany(mappedBy = "incidente")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ativo> ativos = new HashSet<>();

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

    public Incidente identificacao(String identificacao) {
        this.identificacao = identificacao;
        return this;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public OrigemIncidente getOrigem() {
        return origem;
    }

    public Incidente origem(OrigemIncidente origem) {
        this.origem = origem;
        return this;
    }

    public void setOrigem(OrigemIncidente origem) {
        this.origem = origem;
    }

    public MedicaoInstrumento getMedicaoInstrumento() {
        return medicaoInstrumento;
    }

    public Incidente medicaoInstrumento(MedicaoInstrumento medicaoInstrumento) {
        this.medicaoInstrumento = medicaoInstrumento;
        return this;
    }

    public void setMedicaoInstrumento(MedicaoInstrumento medicaoInstrumento) {
        this.medicaoInstrumento = medicaoInstrumento;
    }

    public NivelIncidente getNivelIncidente() {
        return nivelIncidente;
    }

    public Incidente nivelIncidente(NivelIncidente nivelIncidente) {
        this.nivelIncidente = nivelIncidente;
        return this;
    }

    public void setNivelIncidente(NivelIncidente nivelIncidente) {
        this.nivelIncidente = nivelIncidente;
    }

    public Set<Notificacao> getNotificacaos() {
        return notificacaos;
    }

    public Incidente notificacaos(Set<Notificacao> notificacaos) {
        this.notificacaos = notificacaos;
        return this;
    }

    public Incidente addNotificacao(Notificacao notificacao) {
        this.notificacaos.add(notificacao);
        notificacao.setIncidente(this);
        return this;
    }

    public Incidente removeNotificacao(Notificacao notificacao) {
        this.notificacaos.remove(notificacao);
        notificacao.setIncidente(null);
        return this;
    }

    public void setNotificacaos(Set<Notificacao> notificacaos) {
        this.notificacaos = notificacaos;
    }

    public Set<Ativo> getAtivos() {
        return ativos;
    }

    public Incidente ativos(Set<Ativo> ativos) {
        this.ativos = ativos;
        return this;
    }

    public Incidente addAtivo(Ativo ativo) {
        this.ativos.add(ativo);
        ativo.setIncidente(this);
        return this;
    }

    public Incidente removeAtivo(Ativo ativo) {
        this.ativos.remove(ativo);
        ativo.setIncidente(null);
        return this;
    }

    public void setAtivos(Set<Ativo> ativos) {
        this.ativos = ativos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Incidente)) {
            return false;
        }
        return id != null && id.equals(((Incidente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Incidente{" +
            "id=" + getId() +
            ", identificacao='" + getIdentificacao() + "'" +
            ", origem='" + getOrigem() + "'" +
            "}";
    }
}
