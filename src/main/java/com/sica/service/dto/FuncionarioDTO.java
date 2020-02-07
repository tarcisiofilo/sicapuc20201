package com.sica.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sica.domain.Funcionario} entity.
 */
public class FuncionarioDTO implements Serializable {

    private Long id;

    @NotNull
    private String cargo;

    @NotNull
    private Long idDispositivoMonitoramento;


    private Long pessoaId;

    private Long setorMineracaoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Long getIdDispositivoMonitoramento() {
        return idDispositivoMonitoramento;
    }

    public void setIdDispositivoMonitoramento(Long idDispositivoMonitoramento) {
        this.idDispositivoMonitoramento = idDispositivoMonitoramento;
    }

    public Long getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Long pessoaId) {
        this.pessoaId = pessoaId;
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

        FuncionarioDTO funcionarioDTO = (FuncionarioDTO) o;
        if (funcionarioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), funcionarioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FuncionarioDTO{" +
            "id=" + getId() +
            ", cargo='" + getCargo() + "'" +
            ", idDispositivoMonitoramento=" + getIdDispositivoMonitoramento() +
            ", pessoa=" + getPessoaId() +
            ", setorMineracao=" + getSetorMineracaoId() +
            "}";
    }
}
