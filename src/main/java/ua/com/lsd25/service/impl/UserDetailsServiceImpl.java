package ua.com.lsd25.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.com.lsd25.domain.user.User;
import ua.com.lsd25.service.ApplicationException;
import ua.com.lsd25.service.UserService;

import java.util.List;

/**
 * @author vzagnitko
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger LOG = Logger.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserService userService;

    @Autowired
    private Md5PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOG.info("Login user: " + username);
        try {
            User user = userService.findUserByUsername(username);
            if (user == null) {
                throw new ApplicationException("User with username: " + username + " not found");
            }
            List<GrantedAuthority> grantes = AuthorityUtils.createAuthorityList(user.getRole().getAuthority());
            user.setPassword(passwordEncoder.encodePassword(user.getPassword(), user.getUsername()));
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantes);

        } catch (ApplicationException exc) {
            LOG.error("Username not found: " + username, exc);
            throw new UsernameNotFoundException(exc.getLocalizedMessage(), exc);
        }
    }

}
