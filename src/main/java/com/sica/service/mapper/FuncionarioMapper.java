package com.sica.service.mapper;

import com.sica.domain.*;
import com.sica.service.dto.FuncionarioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Funcionario} and its DTO {@link FuncionarioDTO}.
 */
@Mapper(componentModel = "spring", uses = {PessoaMapper.class, SetorMineracaoMapper.class})
public interface FuncionarioMapper extends EntityMapper<FuncionarioDTO, Funcionario> {

    @Mapping(source = "pessoa.id", target = "pessoaId")
    @Mapping(source = "setorMineracao.id", target = "setorMineracaoId")
    FuncionarioDTO toDto(Funcionario funcionario);

    @Mapping(source = "pessoaId", target = "pessoa")
    @Mapping(source = "setorMineracaoId", target = "setorMineracao")
    Funcionario toEntity(FuncionarioDTO funcionarioDTO);

    default Funcionario fromId(Long id) {
        if (id == null) {
            return null;
        }
        Funcionario funcionario = new Funcionario();
        funcionario.setId(id);
        return funcionario;
    }
}
