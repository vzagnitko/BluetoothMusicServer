package ua.com.lsd25.service.impl;

import lombok.NonNull;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.lsd25.domain.user.Role;
import ua.com.lsd25.domain.user.User;
import ua.com.lsd25.repository.RepositoryException;
import ua.com.lsd25.repository.UserRepository;
import ua.com.lsd25.repository.UserRoleRepository;
import ua.com.lsd25.service.ApplicationException;
import ua.com.lsd25.service.UserService;

/**
 * @author vzagnitko
 */
@Service
@CacheConfig(cacheNames = "users")
public class UserServiceImpl implements UserService {

    private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private Md5PasswordEncoder passwordEncoder;

    @Override
    @Cacheable
    @Transactional(readOnly = true)
    public User findUserById(@NonNull Long id) throws ApplicationException {
        try {
            return userRepository.findUserById(id);
        } catch (RepositoryException exc) {
            LOG.error(exc);
            throw new ApplicationException(exc, "Cannot retrieve user by id: " + id);
        }
    }

    @Override
    @Cacheable
    @Transactional(readOnly = true)
    public User findUserByUsername(@NonNull String username) throws ApplicationException {
        try {
            return userRepository.findUserByUsername(username);
        } catch (RepositoryException exc) {
            LOG.error(exc);
            throw new ApplicationException(exc, "Cannot retrieve user by username: " + username);
        }
    }

    @Override
    @CacheEvict
    @Transactional(rollbackFor = ApplicationException.class)
    public long saveUser(@NonNull User user) throws ApplicationException {
        try {
            @NonNull
            Role role = user.getRole();
            user.setPassword(passwordEncoder.encodePassword(user.getPassword(), user.getUsername()));
            userRoleRepository.saveUserRole(role);
            return userRepository.saveUser(user);
        } catch (RepositoryException exc) {
            LOG.error(exc);
            throw new ApplicationException(exc, "Cannot save user: " + user);
        }
    }

    @Override
    public UserDetails findLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            LOG.info("User was found: " + userDetails);
            return (UserDetails) userDetails;
        }
        LOG.info("User was not found: " + userDetails);
        return null;
    }

    @Override
    public void autologin(@NonNull String username, @NonNull String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        password = passwordEncoder.encodePassword(password, username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            LOG.debug(String.format("Auto login %s successfully!", username));
        }
    }

}
