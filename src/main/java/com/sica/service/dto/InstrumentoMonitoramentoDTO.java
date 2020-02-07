package com.sica.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.sica.domain.enumeration.TipoInstrumentoMonitoramento;
import com.sica.domain.enumeration.TipoMedicao;

/**
 * A DTO for the {@link com.sica.domain.InstrumentoMonitoramento} entity.
 */
public class InstrumentoMonitoramentoDTO implements Serializable {

    private Long id;

    @NotNull
    private String identificao;

    @NotNull
    private TipoInstrumentoMonitoramento tipoInstrumentoMonitoramento;

    @NotNull
    private TipoMedicao tipoMedicao;

    private String urlGetMedicao;

    private Double intervaloMedicaoAPI;

    @NotNull
    private Double varianciaTolerada;

    @NotNull
    private Double limiteSuperior;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificao() {
        return identificao;
    }

    public void setIdentificao(String identificao) {
        this.identificao = identificao;
    }

    public TipoInstrumentoMonitoramento getTipoInstrumentoMonitoramento() {
        return tipoInstrumentoMonitoramento;
    }

    public void setTipoInstrumentoMonitoramento(TipoInstrumentoMonitoramento tipoInstrumentoMonitoramento) {
        this.tipoInstrumentoMonitoramento = tipoInstrumentoMonitoramento;
    }

    public TipoMedicao getTipoMedicao() {
        return tipoMedicao;
    }

    public void setTipoMedicao(TipoMedicao tipoMedicao) {
        this.tipoMedicao = tipoMedicao;
    }

    public String getUrlGetMedicao() {
        return urlGetMedicao;
    }

    public void setUrlGetMedicao(String urlGetMedicao) {
        this.urlGetMedicao = urlGetMedicao;
    }

    public Double getIntervaloMedicaoAPI() {
        return intervaloMedicaoAPI;
    }

    public void setIntervaloMedicaoAPI(Double intervaloMedicaoAPI) {
        this.intervaloMedicaoAPI = intervaloMedicaoAPI;
    }

    public Double getVarianciaTolerada() {
        return varianciaTolerada;
    }

    public void setVarianciaTolerada(Double varianciaTolerada) {
        this.varianciaTolerada = varianciaTolerada;
    }

    public Double getLimiteSuperior() {
        return limiteSuperior;
    }

    public void setLimiteSuperior(Double limiteSuperior) {
        this.limiteSuperior = limiteSuperior;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InstrumentoMonitoramentoDTO instrumentoMonitoramentoDTO = (InstrumentoMonitoramentoDTO) o;
        if (instrumentoMonitoramentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), instrumentoMonitoramentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InstrumentoMonitoramentoDTO{" +
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
