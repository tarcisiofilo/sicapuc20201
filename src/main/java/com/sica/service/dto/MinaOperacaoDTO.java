package com.sica.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.sica.domain.enumeration.TipoOperacao;

/**
 * A DTO for the {@link com.sica.domain.MinaOperacao} entity.
 */
public class MinaOperacaoDTO implements Serializable {

    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private TipoOperacao tipoOperacao;


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

    public TipoOperacao getTipoOperacao() {
        return tipoOperacao;
    }

    public void setTipoOperacao(TipoOperacao tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MinaOperacaoDTO minaOperacaoDTO = (MinaOperacaoDTO) o;
        if (minaOperacaoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), minaOperacaoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MinaOperacaoDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", tipoOperacao='" + getTipoOperacao() + "'" +
            "}";
    }
}
