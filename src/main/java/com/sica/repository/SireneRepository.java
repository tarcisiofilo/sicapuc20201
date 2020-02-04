package com.sica.repository;

import com.sica.domain.Sirene;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Sirene entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SireneRepository extends JpaRepository<Sirene, Long> {

}
