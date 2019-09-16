package br.com.lingua.liguadeouro.core.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration
public class DataConfig {

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

//    @Bean
//    public ModelMapper modelMapper() {
//        return new ModelMapper();
//    }

    @Bean
    public SpringLiquibase liquibase() {


        SpringLiquibase liquibase = new SpringLiquibase();

        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:migration/main-dbchangelog.xml");
        liquibase.setContexts("test, production");

        return liquibase;
    }

}

