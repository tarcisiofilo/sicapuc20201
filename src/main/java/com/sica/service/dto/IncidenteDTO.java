package com.sica.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.sica.domain.enumeration.OrigemIncidente;

/**
 * A DTO for the {@link com.sica.domain.Incidente} entity.
 */
public class IncidenteDTO implements Serializable {

    private Long id;

    @NotNull
    private String identificacao;

    @NotNull
    private OrigemIncidente origem;

    @NotNull
    private String mensagem;


    private Long medicaoInstrumentoId;

    private Long nivelIncidenteId;

    private Set<AtivoDTO> ativos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }

    public OrigemIncidente getOrigem() {
        return origem;
    }

    public void setOrigem(OrigemIncidente origem) {
        this.origem = origem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Long getMedicaoInstrumentoId() {
        return medicaoInstrumentoId;
    }

    public void setMedicaoInstrumentoId(Long medicaoInstrumentoId) {
        this.medicaoInstrumentoId = medicaoInstrumentoId;
    }

    public Long getNivelIncidenteId() {
        return nivelIncidenteId;
    }

    public void setNivelIncidenteId(Long nivelIncidenteId) {
        this.nivelIncidenteId = nivelIncidenteId;
    }

    public Set<AtivoDTO> getAtivos() {
        return ativos;
    }

    public void setAtivos(Set<AtivoDTO> ativos) {
        this.ativos = ativos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IncidenteDTO incidenteDTO = (IncidenteDTO) o;
        if (incidenteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), incidenteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "IncidenteDTO{" +
            "id=" + getId() +
            ", identificacao='" + getIdentificacao() + "'" +
            ", origem='" + getOrigem() + "'" +
            ", mensagem='" + getMensagem() + "'" +
            ", medicaoInstrumento=" + getMedicaoInstrumentoId() +
            ", nivelIncidente=" + getNivelIncidenteId() +
            "}";
    }
}
