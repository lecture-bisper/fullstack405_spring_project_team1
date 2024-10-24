package bitc.fullstack405.publicwc.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:/application.properties")
public class DatabaseConfiguration {

    private final Environment env;

    public DatabaseConfiguration(Environment env) {
        this.env = env;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = hikariConfig();
        hikariConfig.setDriverClassName(env.getProperty("spring.datasource.hikari.driver-class-name"));
        hikariConfig.setJdbcUrl(env.getProperty("spring.datasource.hikari.jdbc-url"));
        hikariConfig.setUsername(env.getProperty("spring.datasource.hikari.username"));
        hikariConfig.setPassword(env.getProperty("spring.datasource.hikari.password"));
        hikariConfig.setConnectionTestQuery(env.getProperty("spring.datasource.hikari.connection-test-query"));

        return new HikariDataSource(hikariConfig);
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.jpa")
    public Properties hibernateConfig() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.setProperty("hibernate.hbm2ddl.auto", "create");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        return properties;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}