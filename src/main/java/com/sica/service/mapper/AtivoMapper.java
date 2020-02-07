package com.sica.service.mapper;

import com.sica.domain.*;
import com.sica.service.dto.AtivoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ativo} and its DTO {@link AtivoDTO}.
 */
@Mapper(componentModel = "spring", uses = {SetorMineracaoMapper.class})
public interface AtivoMapper extends EntityMapper<AtivoDTO, Ativo> {

    @Mapping(source = "setorMineracao.id", target = "setorMineracaoId")
    AtivoDTO toDto(Ativo ativo);

    @Mapping(source = "setorMineracaoId", target = "setorMineracao")
    @Mapping(target = "areaSusceptivels", ignore = true)
    @Mapping(target = "incidentes", ignore = true)
    Ativo toEntity(AtivoDTO ativoDTO);

    default Ativo fromId(Long id) {
        if (id == null) {
            return null;
        }
        Ativo ativo = new Ativo();
        ativo.setId(id);
        return ativo;
    }
}
