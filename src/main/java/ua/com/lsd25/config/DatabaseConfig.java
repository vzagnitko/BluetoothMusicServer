package ua.com.lsd25.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author vzagnitko
 */
@Configuration
@EntityScan("ua.com.lsd25.domain")
@EnableTransactionManagement
public class DatabaseConfig {

    public DatabaseConfig() {

    }

}