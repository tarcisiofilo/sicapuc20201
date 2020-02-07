package com.sica.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sica.domain.AreaSusceptivel} entity.
 */
public class AreaSusceptivelDTO implements Serializable {

    private Long id;

    @NotNull
    private String identificacao;

    @NotNull
    private Double nivelProximidade;


    private Long ativoId;

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

    public Double getNivelProximidade() {
        return nivelProximidade;
    }

    public void setNivelProximidade(Double nivelProximidade) {
        this.nivelProximidade = nivelProximidade;
    }

    public Long getAtivoId() {
        return ativoId;
    }

    public void setAtivoId(Long ativoId) {
        this.ativoId = ativoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AreaSusceptivelDTO areaSusceptivelDTO = (AreaSusceptivelDTO) o;
        if (areaSusceptivelDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), areaSusceptivelDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AreaSusceptivelDTO{" +
            "id=" + getId() +
            ", identificacao='" + getIdentificacao() + "'" +
            ", nivelProximidade=" + getNivelProximidade() +
            ", ativo=" + getAtivoId() +
            "}";
    }
}
