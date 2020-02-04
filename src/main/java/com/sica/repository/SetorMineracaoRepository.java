package com.sica.repository;

import com.sica.domain.SetorMineracao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SetorMineracao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SetorMineracaoRepository extends JpaRepository<SetorMineracao, Long> {

}
