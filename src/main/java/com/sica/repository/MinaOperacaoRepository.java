package com.sica.repository;

import com.sica.domain.MinaOperacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MinaOperacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MinaOperacaoRepository extends JpaRepository<MinaOperacao, Long> {

}
