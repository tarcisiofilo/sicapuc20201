package com.sica.service.mapper;

import com.sica.domain.*;
import com.sica.service.dto.PessoaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pessoa} and its DTO {@link PessoaDTO}.
 */
@Mapper(componentModel = "spring", uses = {FamiliaMapper.class})
public interface PessoaMapper extends EntityMapper<PessoaDTO, Pessoa> {

    @Mapping(source = "familia.id", target = "familiaId")
    PessoaDTO toDto(Pessoa pessoa);

    @Mapping(source = "familiaId", target = "familia")
    Pessoa toEntity(PessoaDTO pessoaDTO);

    default Pessoa fromId(Long id) {
        if (id == null) {
            return null;
        }
        Pessoa pessoa = new Pessoa();
        pessoa.setId(id);
        return pessoa;
    }
}
