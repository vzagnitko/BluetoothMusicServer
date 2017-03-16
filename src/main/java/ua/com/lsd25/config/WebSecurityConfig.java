package ua.com.lsd25.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

/**
 * @author vzagnitko
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String REMEMBER_ME_KEY = "4f4225782027c7afc93cd37d";

    private static final int REMEMBER_ME_COOKIE_VALID_TIME = 86400;

    private static final int ENCODE_PASSWORD_ITERATION = 3;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenBasedRememberMeServices tokenBasedRememberMeServices;

    public WebSecurityConfig() {

    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable();

        //authorize requests
        http.authorizeRequests().
                antMatchers("/rest/logins", "/login", "/rest/tests").permitAll().
                antMatchers("/js/**", "/css/**", "/images/**").permitAll().
                anyRequest().
                authenticated();

        //login configuration
        http.formLogin().
                loginPage("/login").
                defaultSuccessUrl("/music", true);

        //remember me configuration
        http.rememberMe().
                rememberMeServices(tokenBasedRememberMeServices).
                userDetailsService(userDetailsService).
                key(REMEMBER_ME_KEY).
                tokenValiditySeconds(REMEMBER_ME_COOKIE_VALID_TIME);

        //logout configuration
        http.logout().
                logoutSuccessUrl("/login").
                deleteCookies("JSESSIONID", "remember-me");
    }


    @Bean
    public Md5PasswordEncoder passwordEncoder() {
        Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();
        passwordEncoder.setIterations(ENCODE_PASSWORD_ITERATION);
        return passwordEncoder;
    }

    @Bean
    public TokenBasedRememberMeServices tokenBasedRememberMeServices() {
        TokenBasedRememberMeServices tokenBasedRememberMeServices =
                new TokenBasedRememberMeServices(REMEMBER_ME_KEY, userDetailsService);
        tokenBasedRememberMeServices.setTokenValiditySeconds(REMEMBER_ME_COOKIE_VALID_TIME);
        return tokenBasedRememberMeServices;
    }

}