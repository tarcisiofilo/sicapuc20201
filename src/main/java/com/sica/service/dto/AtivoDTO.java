package com.sica.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sica.domain.Ativo} entity.
 */
public class AtivoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;


    private Long setorMineracaoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getSetorMineracaoId() {
        return setorMineracaoId;
    }

    public void setSetorMineracaoId(Long setorMineracaoId) {
        this.setorMineracaoId = setorMineracaoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AtivoDTO ativoDTO = (AtivoDTO) o;
        if (ativoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ativoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AtivoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", setorMineracao=" + getSetorMineracaoId() +
            "}";
    }
}
