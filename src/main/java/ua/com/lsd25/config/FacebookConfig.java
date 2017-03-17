package ua.com.lsd25.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;

/**
 * @author vzagnitko
 */
@Configuration
public class FacebookConfig {

    @Value("${spring.social.facebook.access.token}")
    private String facebookAccessToken;

    public FacebookConfig() {

    }

    @Bean
    public Facebook getFacebook() {
        return new FacebookTemplate(facebookAccessToken);
    }

}
