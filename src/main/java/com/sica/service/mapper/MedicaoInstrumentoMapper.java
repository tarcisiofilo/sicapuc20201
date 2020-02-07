package com.sica.service.mapper;

import com.sica.domain.*;
import com.sica.service.dto.MedicaoInstrumentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MedicaoInstrumento} and its DTO {@link MedicaoInstrumentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {InstrumentoMonitoramentoMapper.class})
public interface MedicaoInstrumentoMapper extends EntityMapper<MedicaoInstrumentoDTO, MedicaoInstrumento> {

    @Mapping(source = "instrumentoMonitoramento.id", target = "instrumentoMonitoramentoId")
    MedicaoInstrumentoDTO toDto(MedicaoInstrumento medicaoInstrumento);

    @Mapping(target = "incidente", ignore = true)
    @Mapping(source = "instrumentoMonitoramentoId", target = "instrumentoMonitoramento")
    MedicaoInstrumento toEntity(MedicaoInstrumentoDTO medicaoInstrumentoDTO);

    default MedicaoInstrumento fromId(Long id) {
        if (id == null) {
            return null;
        }
        MedicaoInstrumento medicaoInstrumento = new MedicaoInstrumento();
        medicaoInstrumento.setId(id);
        return medicaoInstrumento;
    }
}
