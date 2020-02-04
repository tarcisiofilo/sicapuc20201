package com.sica.repository;

import com.sica.domain.MedicaoInstrumento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the MedicaoInstrumento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicaoInstrumentoRepository extends JpaRepository<MedicaoInstrumento, Long> {

}
