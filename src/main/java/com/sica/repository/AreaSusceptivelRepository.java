package com.sica.repository;

import com.sica.domain.AreaSusceptivel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AreaSusceptivel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AreaSusceptivelRepository extends JpaRepository<AreaSusceptivel, Long> {

}
