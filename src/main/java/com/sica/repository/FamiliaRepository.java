package com.sica.repository;

import com.sica.domain.Familia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Familia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FamiliaRepository extends JpaRepository<Familia, Long> {

}
