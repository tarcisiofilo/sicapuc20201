package com.sica.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sica.domain.Familia} entity.
 */
public class FamiliaDTO implements Serializable {

    private Long id;

    @NotNull
    private String identificacao;


    private Long areaSusceptivelId;

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

    public Long getAreaSusceptivelId() {
        return areaSusceptivelId;
    }

    public void setAreaSusceptivelId(Long areaSusceptivelId) {
        this.areaSusceptivelId = areaSusceptivelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FamiliaDTO familiaDTO = (FamiliaDTO) o;
        if (familiaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), familiaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FamiliaDTO{" +
            "id=" + getId() +
            ", identificacao='" + getIdentificacao() + "'" +
            ", areaSusceptivel=" + getAreaSusceptivelId() +
            "}";
    }
}
