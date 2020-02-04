package com.sica.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Vistoria.
 */
@Entity
@Table(name = "vistoria")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Vistoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "identificao", nullable = false)
    private String identificao;

    @NotNull
    @Column(name = "data", nullable = false)
    private ZonedDateTime data;

    @NotNull
    @Column(name = "valor", nullable = false)
    private Double valor;

    @OneToOne
    @JoinColumn(unique = true)
    private InstrumentoMonitoramento instrumentoMonitoramento;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificao() {
        return identificao;
    }

    public Vistoria identificao(String identificao) {
        this.identificao = identificao;
        return this;
    }

    public void setIdentificao(String identificao) {
        this.identificao = identificao;
    }

    public ZonedDateTime getData() {
        return data;
    }

    public Vistoria data(ZonedDateTime data) {
        this.data = data;
        return this;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public Vistoria valor(Double valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public InstrumentoMonitoramento getInstrumentoMonitoramento() {
        return instrumentoMonitoramento;
    }

    public Vistoria instrumentoMonitoramento(InstrumentoMonitoramento instrumentoMonitoramento) {
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
        if (!(o instanceof Vistoria)) {
            return false;
        }
        return id != null && id.equals(((Vistoria) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Vistoria{" +
            "id=" + getId() +
            ", identificao='" + getIdentificao() + "'" +
            ", data='" + getData() + "'" +
            ", valor=" + getValor() +
            "}";
    }
}
