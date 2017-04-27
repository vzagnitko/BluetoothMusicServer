package ua.com.lsd25.service.impl;

import org.apache.log4j.Logger;
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
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOG = Logger.getLogger(UserDetailsServiceImpl.class);

    private final UserService userService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOG.info(String.format("Login user: %s", username));
        try {
            User user = userService.findUserByUsername(username);
            if (user == null) {
                throw new ApplicationException(String.format("User with username: %s not found", username));
            }
            user.setEnabled(true);
            return user;

        } catch (ApplicationException exc) {
            LOG.error(String.format("Username not found: %s", username), exc);
            throw new UsernameNotFoundException(exc.getLocalizedMessage(), exc);
        }
    }

}
