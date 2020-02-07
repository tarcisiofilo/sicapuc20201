package com.sica.service.mapper;

import com.sica.domain.*;
import com.sica.service.dto.MinaOperacaoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link MinaOperacao} and its DTO {@link MinaOperacaoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MinaOperacaoMapper extends EntityMapper<MinaOperacaoDTO, MinaOperacao> {


    @Mapping(target = "setorMineracaos", ignore = true)
    MinaOperacao toEntity(MinaOperacaoDTO minaOperacaoDTO);

    default MinaOperacao fromId(Long id) {
        if (id == null) {
            return null;
        }
        MinaOperacao minaOperacao = new MinaOperacao();
        minaOperacao.setId(id);
        return minaOperacao;
    }
}
