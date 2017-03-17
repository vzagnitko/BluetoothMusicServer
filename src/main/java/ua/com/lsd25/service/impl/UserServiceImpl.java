package ua.com.lsd25.service.impl;

import lombok.NonNull;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.lsd25.controller.handler.login.UserAlreadyLoggedException;
import ua.com.lsd25.domain.user.User;
import ua.com.lsd25.domain.user.role.Role;
import ua.com.lsd25.repository.RepositoryException;
import ua.com.lsd25.repository.user.UserRepository;
import ua.com.lsd25.repository.user.UserRoleRepository;
import ua.com.lsd25.service.ApplicationException;
import ua.com.lsd25.service.UserService;

/**
 * @author vzagnitko
 */
@Service
@CacheConfig(cacheNames = "users")
public class UserServiceImpl implements UserService {

    private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);

    private static final String ROLE_ANONYMOUS = "anonymousUser";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

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
    @Cacheable
    @Transactional(readOnly = true)
    public boolean isExistsUser(@NonNull String username) throws ApplicationException {
        try {
            return userRepository.isExists(username);
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
            if (isExistsUser(user.getUsername())) {
                throw new UserAlreadyLoggedException("User with username: " + user.getUsername() + " already registered");
            }
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
    public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal == null || principal.equals(ROLE_ANONYMOUS)) {
            throw new AccessDeniedException("User is not logged");
        }
        return (User) authentication.getPrincipal();
    }

    @Override
    public boolean isLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        return !(principal == null || principal.equals(ROLE_ANONYMOUS));
    }

}
