package com.sica.service.mapper;

import com.sica.domain.*;
import com.sica.service.dto.VistoriaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vistoria} and its DTO {@link VistoriaDTO}.
 */
@Mapper(componentModel = "spring", uses = {InstrumentoMonitoramentoMapper.class})
public interface VistoriaMapper extends EntityMapper<VistoriaDTO, Vistoria> {

    @Mapping(source = "instrumentoMonitoramento.id", target = "instrumentoMonitoramentoId")
    VistoriaDTO toDto(Vistoria vistoria);

    @Mapping(source = "instrumentoMonitoramentoId", target = "instrumentoMonitoramento")
    Vistoria toEntity(VistoriaDTO vistoriaDTO);

    default Vistoria fromId(Long id) {
        if (id == null) {
            return null;
        }
        Vistoria vistoria = new Vistoria();
        vistoria.setId(id);
        return vistoria;
    }
}
