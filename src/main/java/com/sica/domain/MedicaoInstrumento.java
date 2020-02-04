package com.sica.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A MedicaoInstrumento.
 */
@Entity
@Table(name = "medicao_instrumento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MedicaoInstrumento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "data", nullable = false)
    private ZonedDateTime data;

    @NotNull
    @Column(name = "valor", nullable = false)
    private Double valor;

    @OneToOne(mappedBy = "medicaoInstrumento")
    @JsonIgnore
    private Incidente incidente;

    @ManyToOne
    @JsonIgnoreProperties("medicaoInstrumentos")
    private InstrumentoMonitoramento instrumentoMonitoramento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public MedicaoInstrumento data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public MedicaoInstrumento valor(Double valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Incidente getIncidente() {
        return incidente;
    }

    public MedicaoInstrumento incidente(Incidente incidente) {
        this.incidente = incidente;
        return this;
    }

    public void setIncidente(Incidente incidente) {
        this.incidente = incidente;
    }

    public InstrumentoMonitoramento getInstrumentoMonitoramento() {
        return instrumentoMonitoramento;
    }

    public MedicaoInstrumento instrumentoMonitoramento(InstrumentoMonitoramento instrumentoMonitoramento) {
        this.instrumentoMonitoramento = instrumentoMonitoramento;
        return this;
    }

    public void setInstrumentoMonitoramento(InstrumentoMonitoramento instrumentoMonitoramento) {
        this.instrumentoMonitoramento = instrumentoMonitoramento;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MedicaoInstrumento)) {
            return false;
        }
        return id != null && id.equals(((MedicaoInstrumento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "MedicaoInstrumento{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", valor=" + getValor() +
            "}";
    }
}
