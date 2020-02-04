package com.sica.repository;

import com.sica.domain.InstrumentoMonitoramento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the InstrumentoMonitoramento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InstrumentoMonitoramentoRepository extends JpaRepository<InstrumentoMonitoramento, Long> {

}
