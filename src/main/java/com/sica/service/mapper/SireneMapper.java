package com.sica.service.mapper;

import com.sica.domain.*;
import com.sica.service.dto.SireneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sirene} and its DTO {@link SireneDTO}.
 */
@Mapper(componentModel = "spring", uses = {AreaSusceptivelMapper.class})
public interface SireneMapper extends EntityMapper<SireneDTO, Sirene> {

    @Mapping(source = "areaSusceptivel.id", target = "areaSusceptivelId")
    SireneDTO toDto(Sirene sirene);

    @Mapping(source = "areaSusceptivelId", target = "areaSusceptivel")
    Sirene toEntity(SireneDTO sireneDTO);

    default Sirene fromId(Long id) {
        if (id == null) {
            return null;
        }
        Sirene sirene = new Sirene();
        sirene.setId(id);
        return sirene;
    }
}
