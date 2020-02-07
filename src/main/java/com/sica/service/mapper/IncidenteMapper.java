package com.sica.service.mapper;

import com.sica.domain.*;
import com.sica.service.dto.IncidenteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Incidente} and its DTO {@link IncidenteDTO}.
 */
@Mapper(componentModel = "spring", uses = {MedicaoInstrumentoMapper.class, NivelIncidenteMapper.class, AtivoMapper.class})
public interface IncidenteMapper extends EntityMapper<IncidenteDTO, Incidente> {

    @Mapping(source = "medicaoInstrumento.id", target = "medicaoInstrumentoId")
    @Mapping(source = "nivelIncidente.id", target = "nivelIncidenteId")
    IncidenteDTO toDto(Incidente incidente);

    @Mapping(source = "medicaoInstrumentoId", target = "medicaoInstrumento")
    @Mapping(source = "nivelIncidenteId", target = "nivelIncidente")
    Incidente toEntity(IncidenteDTO incidenteDTO);

    default Incidente fromId(Long id) {
        if (id == null) {
            return null;
        }
        Incidente incidente = new Incidente();
        incidente.setId(id);
        return incidente;
    }
}
