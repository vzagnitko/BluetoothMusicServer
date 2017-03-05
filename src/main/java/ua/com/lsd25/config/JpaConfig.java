package ua.com.lsd25.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author vzagnitko
 */
@Configuration
@EnableJpaRepositories(basePackages = "ua.com.lsd25.repository.query")
public class JpaConfig {

    public JpaConfig() {

    }

}
