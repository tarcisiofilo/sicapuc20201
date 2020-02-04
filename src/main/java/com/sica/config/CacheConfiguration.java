package com.sica.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.sica.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.sica.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.sica.domain.User.class.getName());
            createCache(cm, com.sica.domain.Authority.class.getName());
            createCache(cm, com.sica.domain.User.class.getName() + ".authorities");
            createCache(cm, com.sica.domain.MinaOperacao.class.getName());
            createCache(cm, com.sica.domain.MinaOperacao.class.getName() + ".setorMineracaos");
            createCache(cm, com.sica.domain.SetorMineracao.class.getName());
            createCache(cm, com.sica.domain.SetorMineracao.class.getName() + ".funcionarios");
            createCache(cm, com.sica.domain.Pessoa.class.getName());
            createCache(cm, com.sica.domain.Funcionario.class.getName());
            createCache(cm, com.sica.domain.AreaSusceptivel.class.getName());
            createCache(cm, com.sica.domain.AreaSusceptivel.class.getName() + ".familias");
            createCache(cm, com.sica.domain.AreaSusceptivel.class.getName() + ".sirenes");
            createCache(cm, com.sica.domain.Familia.class.getName());
            createCache(cm, com.sica.domain.Familia.class.getName() + ".pessoas");
            createCache(cm, com.sica.domain.Ativo.class.getName());
            createCache(cm, com.sica.domain.Ativo.class.getName() + ".areaSusceptivels");
            createCache(cm, com.sica.domain.Sirene.class.getName());
            createCache(cm, com.sica.domain.Incidente.class.getName());
            createCache(cm, com.sica.domain.Incidente.class.getName() + ".notificacaos");
            createCache(cm, com.sica.domain.Incidente.class.getName() + ".ativos");
            createCache(cm, com.sica.domain.Vistoria.class.getName());
            createCache(cm, com.sica.domain.InstrumentoMonitoramento.class.getName());
            createCache(cm, com.sica.domain.InstrumentoMonitoramento.class.getName() + ".medicaoInstrumentos");
            createCache(cm, com.sica.domain.MedicaoInstrumento.class.getName());
            createCache(cm, com.sica.domain.Notificacao.class.getName());
            createCache(cm, com.sica.domain.NivelIncidente.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }
}
