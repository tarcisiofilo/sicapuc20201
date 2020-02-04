package com.sica.repository;

import com.sica.domain.Notificacao;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Notificacao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {

}
