package com.sica.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sica.domain.SetorMineracao} entity.
 */
public class SetorMineracaoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;


    private Long diretorId;

    private Long gerenteId;

    private Long minaOperacaoId;

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

    public Long getDiretorId() {
        return diretorId;
    }

    public void setDiretorId(Long funcionarioId) {
        this.diretorId = funcionarioId;
    }

    public Long getGerenteId() {
        return gerenteId;
    }

    public void setGerenteId(Long funcionarioId) {
        this.gerenteId = funcionarioId;
    }

    public Long getMinaOperacaoId() {
        return minaOperacaoId;
    }

    public void setMinaOperacaoId(Long minaOperacaoId) {
        this.minaOperacaoId = minaOperacaoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SetorMineracaoDTO setorMineracaoDTO = (SetorMineracaoDTO) o;
        if (setorMineracaoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), setorMineracaoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SetorMineracaoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", diretor=" + getDiretorId() +
            ", gerente=" + getGerenteId() +
            ", minaOperacao=" + getMinaOperacaoId() +
            "}";
    }
}
