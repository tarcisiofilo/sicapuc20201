package com.sica.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sica.domain.Sirene} entity.
 */
public class SireneDTO implements Serializable {

    private Long id;

    @NotNull
    private String identificacao;

    @NotNull
    private String urlAtivar;


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

    public String getUrlAtivar() {
        return urlAtivar;
    }

    public void setUrlAtivar(String urlAtivar) {
        this.urlAtivar = urlAtivar;
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

        SireneDTO sireneDTO = (SireneDTO) o;
        if (sireneDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), sireneDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SireneDTO{" +
            "id=" + getId() +
            ", identificacao='" + getIdentificacao() + "'" +
            ", urlAtivar='" + getUrlAtivar() + "'" +
            ", areaSusceptivel=" + getAreaSusceptivelId() +
            "}";
    }
}
