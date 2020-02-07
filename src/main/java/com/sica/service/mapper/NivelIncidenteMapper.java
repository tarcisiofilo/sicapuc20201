package com.sica.service.mapper;

import com.sica.domain.*;
import com.sica.service.dto.NivelIncidenteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link NivelIncidente} and its DTO {@link NivelIncidenteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NivelIncidenteMapper extends EntityMapper<NivelIncidenteDTO, NivelIncidente> {



    default NivelIncidente fromId(Long id) {
        if (id == null) {
            return null;
        }
        NivelIncidente nivelIncidente = new NivelIncidente();
        nivelIncidente.setId(id);
        return nivelIncidente;
    }
}
