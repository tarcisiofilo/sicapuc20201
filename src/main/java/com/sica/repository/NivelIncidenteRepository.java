package com.sica.repository;

import com.sica.domain.NivelIncidente;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NivelIncidente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NivelIncidenteRepository extends JpaRepository<NivelIncidente, Long> {

}
