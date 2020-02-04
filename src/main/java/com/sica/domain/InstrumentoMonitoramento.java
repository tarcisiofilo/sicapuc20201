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

import com.sica.domain.enumeration.TipoInstrumentoMonitoramento;

import com.sica.domain.enumeration.TipoMedicao;

/**
 * A InstrumentoMonitoramento.
 */
@Entity
@Table(name = "instrumento_monitoramento")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InstrumentoMonitoramento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "identificao", nullable = false)
    private String identificao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_instrumento_monitoramento", nullable = false)
    private TipoInstrumentoMonitoramento tipoInstrumentoMonitoramento;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_medicao", nullable = false)
    private TipoMedicao tipoMedicao;

    @Column(name = "url_get_medicao")
    private String urlGetMedicao;

    @Column(name = "intervalo_medicao_api")
    private Double intervaloMedicaoAPI;

    @NotNull
    @Column(name = "variancia_tolerada", nullable = false)
    private Double varianciaTolerada;

    @NotNull
    @Column(name = "limite_superior", nullable = false)
    private Double limiteSuperior;

    @OneToMany(mappedBy = "instrumentoMonitoramento")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MedicaoInstrumento> medicaoInstrumentos = new HashSet<>();

    @OneToOne(mappedBy = "instrumentoMonitoramento")
    @JsonIgnore
    private Vistoria vistoria;

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

    public InstrumentoMonitoramento identificao(String identificao) {
        this.identificao = identificao;
        return this;
    }

    public void setIdentificao(String identificao) {
        this.identificao = identificao;
    }

    public TipoInstrumentoMonitoramento getTipoInstrumentoMonitoramento() {
        return tipoInstrumentoMonitoramento;
    }

    public InstrumentoMonitoramento tipoInstrumentoMonitoramento(TipoInstrumentoMonitoramento tipoInstrumentoMonitoramento) {
        this.tipoInstrumentoMonitoramento = tipoInstrumentoMonitoramento;
        return this;
    }

    public void setTipoInstrumentoMonitoramento(TipoInstrumentoMonitoramento tipoInstrumentoMonitoramento) {
        this.tipoInstrumentoMonitoramento = tipoInstrumentoMonitoramento;
    }

    public TipoMedicao getTipoMedicao() {
        return tipoMedicao;
    }

    public InstrumentoMonitoramento tipoMedicao(TipoMedicao tipoMedicao) {
        this.tipoMedicao = tipoMedicao;
        return this;
    }

    public void setTipoMedicao(TipoMedicao tipoMedicao) {
        this.tipoMedicao = tipoMedicao;
    }

    public String getUrlGetMedicao() {
        return urlGetMedicao;
    }

    public InstrumentoMonitoramento urlGetMedicao(String urlGetMedicao) {
        this.urlGetMedicao = urlGetMedicao;
        return this;
    }

    public void setUrlGetMedicao(String urlGetMedicao) {
        this.urlGetMedicao = urlGetMedicao;
    }

    public Double getIntervaloMedicaoAPI() {
        return intervaloMedicaoAPI;
    }

    public InstrumentoMonitoramento intervaloMedicaoAPI(Double intervaloMedicaoAPI) {
        this.intervaloMedicaoAPI = intervaloMedicaoAPI;
        return this;
    }

    public void setIntervaloMedicaoAPI(Double intervaloMedicaoAPI) {
        this.intervaloMedicaoAPI = intervaloMedicaoAPI;
    }

    public Double getVarianciaTolerada() {
        return varianciaTolerada;
    }

    public InstrumentoMonitoramento varianciaTolerada(Double varianciaTolerada) {
        this.varianciaTolerada = varianciaTolerada;
        return this;
    }

    public void setVarianciaTolerada(Double varianciaTolerada) {
        this.varianciaTolerada = varianciaTolerada;
    }

    public Double getLimiteSuperior() {
        return limiteSuperior;
    }

    public InstrumentoMonitoramento limiteSuperior(Double limiteSuperior) {
        this.limiteSuperior = limiteSuperior;
        return this;
    }

    public void setLimiteSuperior(Double limiteSuperior) {
        this.limiteSuperior = limiteSuperior;
    }

    public Set<MedicaoInstrumento> getMedicaoInstrumentos() {
        return medicaoInstrumentos;
    }

    public InstrumentoMonitoramento medicaoInstrumentos(Set<MedicaoInstrumento> medicaoInstrumentos) {
        this.medicaoInstrumentos = medicaoInstrumentos;
        return this;
    }

    public InstrumentoMonitoramento addMedicaoInstrumento(MedicaoInstrumento medicaoInstrumento) {
        this.medicaoInstrumentos.add(medicaoInstrumento);
        medicaoInstrumento.setInstrumentoMonitoramento(this);
        return this;
    }

    public InstrumentoMonitoramento removeMedicaoInstrumento(MedicaoInstrumento medicaoInstrumento) {
        this.medicaoInstrumentos.remove(medicaoInstrumento);
        medicaoInstrumento.setInstrumentoMonitoramento(null);
        return this;
    }

    public void setMedicaoInstrumentos(Set<MedicaoInstrumento> medicaoInstrumentos) {
        this.medicaoInstrumentos = medicaoInstrumentos;
    }

    public Vistoria getVistoria() {
        return vistoria;
    }

    public InstrumentoMonitoramento vistoria(Vistoria vistoria) {
        this.vistoria = vistoria;
        return this;
    }

    public void setVistoria(Vistoria vistoria) {
        this.vistoria = vistoria;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InstrumentoMonitoramento)) {
            return false;
        }
        return id != null && id.equals(((InstrumentoMonitoramento) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "InstrumentoMonitoramento{" +
            "id=" + getId() +
            ", identificao='" + getIdentificao() + "'" +
            ", tipoInstrumentoMonitoramento='" + getTipoInstrumentoMonitoramento() + "'" +
            ", tipoMedicao='" + getTipoMedicao() + "'" +
            ", urlGetMedicao='" + getUrlGetMedicao() + "'" +
            ", intervaloMedicaoAPI=" + getIntervaloMedicaoAPI() +
            ", varianciaTolerada=" + getVarianciaTolerada() +
            ", limiteSuperior=" + getLimiteSuperior() +
            "}";
    }
}
