package ru.abr.region.pets.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import ru.abr.region.pets.PetsApplication;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackageClasses = PetsApplication.class)
@EnableTransactionManagement
@Profile("prod")
public class AppConfig implements TransactionManagementConfigurer {

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .username("zoo_keeper")
                .password("zoo_pass")
                .url("jdbc:postgresql://localhost:5432/zoo")
                .driverClassName("org.postgresql.Driver")
                .build();
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return transactionManager();
    }
}
