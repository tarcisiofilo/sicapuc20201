package com.sica.service.mapper;

import com.sica.domain.*;
import com.sica.service.dto.InstrumentoMonitoramentoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link InstrumentoMonitoramento} and its DTO {@link InstrumentoMonitoramentoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InstrumentoMonitoramentoMapper extends EntityMapper<InstrumentoMonitoramentoDTO, InstrumentoMonitoramento> {


    @Mapping(target = "medicaoInstrumentos", ignore = true)
    @Mapping(target = "vistoria", ignore = true)
    InstrumentoMonitoramento toEntity(InstrumentoMonitoramentoDTO instrumentoMonitoramentoDTO);

    default InstrumentoMonitoramento fromId(Long id) {
        if (id == null) {
            return null;
        }
        InstrumentoMonitoramento instrumentoMonitoramento = new InstrumentoMonitoramento();
        instrumentoMonitoramento.setId(id);
        return instrumentoMonitoramento;
    }
}
