package com.sica.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.sica.domain.enumeration.TipoNoficacao;

/**
 * A Notificacao.
 */
@Entity
@Table(name = "notificacao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Notificacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "data_notifacao", nullable = false)
    private ZonedDateTime dataNotifacao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_notificacao", nullable = false)
    private TipoNoficacao tipoNotificacao;

    @ManyToOne
    @JsonIgnoreProperties("notificacaos")
    private Incidente incidente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDataNotifacao() {
        return dataNotifacao;
    }

    public Notificacao dataNotifacao(ZonedDateTime dataNotifacao) {
        this.dataNotifacao = dataNotifacao;
        return this;
    }

    public void setDataNotifacao(ZonedDateTime dataNotifacao) {
        this.dataNotifacao = dataNotifacao;
    }

    public TipoNoficacao getTipoNotificacao() {
        return tipoNotificacao;
    }

    public Notificacao tipoNotificacao(TipoNoficacao tipoNotificacao) {
        this.tipoNotificacao = tipoNotificacao;
        return this;
    }

    public void setTipoNotificacao(TipoNoficacao tipoNotificacao) {
        this.tipoNotificacao = tipoNotificacao;
    }

    public Incidente getIncidente() {
        return incidente;
    }

    public Notificacao incidente(Incidente incidente) {
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
        if (!(o instanceof Notificacao)) {
            return false;
        }
        return id != null && id.equals(((Notificacao) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Notificacao{" +
            "id=" + getId() +
            ", dataNotifacao='" + getDataNotifacao() + "'" +
            ", tipoNotificacao='" + getTipoNotificacao() + "'" +
            "}";
    }
}
