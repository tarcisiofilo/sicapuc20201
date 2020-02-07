package com.sica.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sica.domain.Vistoria} entity.
 */
public class VistoriaDTO implements Serializable {

    private Long id;

    @NotNull
    private String identificao;

    @NotNull
    private ZonedDateTime data;

    @NotNull
    private Double valor;


    private Long instrumentoMonitoramentoId;

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

    public ZonedDateTime getData() {
        return data;
    }

    public void setData(ZonedDateTime data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Long getInstrumentoMonitoramentoId() {
        return instrumentoMonitoramentoId;
    }

    public void setInstrumentoMonitoramentoId(Long instrumentoMonitoramentoId) {
        this.instrumentoMonitoramentoId = instrumentoMonitoramentoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        VistoriaDTO vistoriaDTO = (VistoriaDTO) o;
        if (vistoriaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), vistoriaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "VistoriaDTO{" +
            "id=" + getId() +
            ", identificao='" + getIdentificao() + "'" +
            ", data='" + getData() + "'" +
            ", valor=" + getValor() +
            ", instrumentoMonitoramento=" + getInstrumentoMonitoramentoId() +
            "}";
    }
}
