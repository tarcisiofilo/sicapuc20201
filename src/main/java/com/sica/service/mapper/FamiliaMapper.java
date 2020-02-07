package com.sica.service.mapper;

import com.sica.domain.*;
import com.sica.service.dto.FamiliaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Familia} and its DTO {@link FamiliaDTO}.
 */
@Mapper(componentModel = "spring", uses = {AreaSusceptivelMapper.class})
public interface FamiliaMapper extends EntityMapper<FamiliaDTO, Familia> {

    @Mapping(source = "areaSusceptivel.id", target = "areaSusceptivelId")
    FamiliaDTO toDto(Familia familia);

    @Mapping(target = "pessoas", ignore = true)
    @Mapping(source = "areaSusceptivelId", target = "areaSusceptivel")
    Familia toEntity(FamiliaDTO familiaDTO);

    default Familia fromId(Long id) {
        if (id == null) {
            return null;
        }
        Familia familia = new Familia();
        familia.setId(id);
        return familia;
    }
}
