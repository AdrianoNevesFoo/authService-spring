package br.com.lingua.liguadeouro;

import br.com.lingua.liguadeouro.core.config.DataConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@Import({DataConfig.class})
public class LiguaDeOuroApplication {

	public static void main(String[] args) {
		SpringApplication.run(LiguaDeOuroApplication.class, args);
	}

}
