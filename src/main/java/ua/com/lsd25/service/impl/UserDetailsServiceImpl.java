package ua.com.lsd25.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.com.lsd25.domain.user.User;
import ua.com.lsd25.service.ApplicationException;
import ua.com.lsd25.service.UserService;

/**
 * @author vzagnitko
 */
@Log4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Login user: " + username);
        try {
            User user = userService.findUserByUsername(username);
            if (user == null) {
                throw new ApplicationException("User with username: " + username + " not found in DB!");
            }
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());

        } catch (ApplicationException exc) {
            log.error("Username not found: " + username, exc);
            throw new UsernameNotFoundException("Username not found in DB: " + username, exc);
        }
    }

}
