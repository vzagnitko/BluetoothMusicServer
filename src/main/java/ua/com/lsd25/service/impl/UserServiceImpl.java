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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.lsd25.controller.handler.register.UserAlreadyRegisterException;
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

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final Md5PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserRoleRepository userRoleRepository,
                           Md5PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Cacheable
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public User findUserById(@NonNull Long id) throws ApplicationException {
        try {
            return userRepository.findUserById(id);
        } catch (RepositoryException exc) {
            LOG.error(exc);
            throw new ApplicationException(exc, String.format("Cannot retrieve user by id: %d", id));
        }
    }

    @Override
    @Cacheable
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public User findUserByUsername(@NonNull String username) throws ApplicationException {
        try {
            return userRepository.findUserByUsername(username);
        } catch (RepositoryException exc) {
            LOG.error(exc);
            throw new ApplicationException(exc, String.format("Cannot retrieve user by username: %s", username));
        }
    }

    @Override
    @Cacheable
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public boolean isExistsUser(@NonNull String username) throws ApplicationException {
        try {
            return userRepository.isExists(username);
        } catch (RepositoryException exc) {
            LOG.error(exc);
            throw new ApplicationException(exc, String.format("Cannot retrieve user by username: %s", username));
        }
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = ApplicationException.class)
    public long saveUser(@NonNull User user) throws ApplicationException {
        if (isExistsUser(user.getUsername())) {
            throw new UserAlreadyRegisterException(String.format("User with username: %s already registered", user.getUsername()));
        }
        try {
            Role role = user.getRole();
            user.setPassword(passwordEncoder.encodePassword(user.getPassword(), user.getUsername()));
            userRoleRepository.saveUserRole(role);
            return userRepository.saveUser(user);
        } catch (RepositoryException exc) {
            LOG.error(exc);
            throw new ApplicationException(exc, String.format("Cannot save user: %s", user));
        }
    }

    @Override
    @Cacheable
    public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AccessDeniedException("User is not logged");
        }
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
