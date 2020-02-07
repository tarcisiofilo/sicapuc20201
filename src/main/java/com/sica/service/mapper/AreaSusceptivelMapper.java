package com.sica.service.mapper;

import com.sica.domain.*;
import com.sica.service.dto.AreaSusceptivelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AreaSusceptivel} and its DTO {@link AreaSusceptivelDTO}.
 */
@Mapper(componentModel = "spring", uses = {AtivoMapper.class})
public interface AreaSusceptivelMapper extends EntityMapper<AreaSusceptivelDTO, AreaSusceptivel> {

    @Mapping(source = "ativo.id", target = "ativoId")
    AreaSusceptivelDTO toDto(AreaSusceptivel areaSusceptivel);

    @Mapping(target = "familias", ignore = true)
    @Mapping(target = "sirenes", ignore = true)
    @Mapping(source = "ativoId", target = "ativo")
    AreaSusceptivel toEntity(AreaSusceptivelDTO areaSusceptivelDTO);

    default AreaSusceptivel fromId(Long id) {
        if (id == null) {
            return null;
        }
        AreaSusceptivel areaSusceptivel = new AreaSusceptivel();
        areaSusceptivel.setId(id);
        return areaSusceptivel;
    }
}
