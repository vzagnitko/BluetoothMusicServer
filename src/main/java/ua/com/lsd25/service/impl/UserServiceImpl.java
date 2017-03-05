package ua.com.lsd25.service.impl;

import lombok.NonNull;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.lsd25.domain.user.User;
import ua.com.lsd25.repository.RepositoryException;
import ua.com.lsd25.repository.UserRepository;
import ua.com.lsd25.repository.UserRoleRepository;
import ua.com.lsd25.service.ApplicationException;
import ua.com.lsd25.service.UserService;

/**
 * @author vzagnitko
 */
@Log4j
@Service
@CacheConfig(cacheNames = "users")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    @Cacheable
    @Transactional(readOnly = true)
    public User findUserById(@NonNull Long id) throws ApplicationException {
        try {
            return userRepository.findUserById(id);
        } catch (RepositoryException exc) {
            log.error(exc);
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
            log.error(exc);
            throw new ApplicationException(exc, "Cannot retrieve user by username: " + username);
        }
    }

    @Override
    @Transactional(rollbackFor = ApplicationException.class)
    public long saveUser(@NonNull User user) throws ApplicationException {
        try {
            userRoleRepository.saveUserRole(user.getRole());
            return userRepository.saveUser(user);
        } catch (RepositoryException exc) {
            log.error(exc);
            throw new ApplicationException(exc, "Cannot save user: " + user);
        }
    }

}
