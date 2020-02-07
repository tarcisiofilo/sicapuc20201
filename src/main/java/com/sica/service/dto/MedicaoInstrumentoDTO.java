package com.sica.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sica.domain.MedicaoInstrumento} entity.
 */
public class MedicaoInstrumentoDTO implements Serializable {

    private Long id;

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

        MedicaoInstrumentoDTO medicaoInstrumentoDTO = (MedicaoInstrumentoDTO) o;
        if (medicaoInstrumentoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medicaoInstrumentoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MedicaoInstrumentoDTO{" +
            "id=" + getId() +
            ", data='" + getData() + "'" +
            ", valor=" + getValor() +
            ", instrumentoMonitoramento=" + getInstrumentoMonitoramentoId() +
            "}";
    }
}
