package br.com.lingua.liguadeouro.core.config;

import br.com.lingua.liguadeouro.utils.SecurityUtils;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;


@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(
        repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class
)
@EntityScan({"br.com.lingua.liguadeouro.auth"})
public class DataAuditedConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return new SpringSecurityAuditorAware();
    }

    class SpringSecurityAuditorAware implements AuditorAware<String> {
        @Override
        public Optional<String> getCurrentAuditor() {
            return SecurityUtils.getCurrentUserName();
        }
    }

}