package com.sica.service.mapper;

import com.sica.domain.*;
import com.sica.service.dto.SetorMineracaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SetorMineracao} and its DTO {@link SetorMineracaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {FuncionarioMapper.class, MinaOperacaoMapper.class})
public interface SetorMineracaoMapper extends EntityMapper<SetorMineracaoDTO, SetorMineracao> {

    @Mapping(source = "diretor.id", target = "diretorId")
    @Mapping(source = "gerente.id", target = "gerenteId")
    @Mapping(source = "minaOperacao.id", target = "minaOperacaoId")
    SetorMineracaoDTO toDto(SetorMineracao setorMineracao);

    @Mapping(target = "funcionarios", ignore = true)
    @Mapping(source = "diretorId", target = "diretor")
    @Mapping(source = "gerenteId", target = "gerente")
    @Mapping(source = "minaOperacaoId", target = "minaOperacao")
    SetorMineracao toEntity(SetorMineracaoDTO setorMineracaoDTO);

    default SetorMineracao fromId(Long id) {
        if (id == null) {
            return null;
        }
        SetorMineracao setorMineracao = new SetorMineracao();
        setorMineracao.setId(id);
        return setorMineracao;
    }
}
