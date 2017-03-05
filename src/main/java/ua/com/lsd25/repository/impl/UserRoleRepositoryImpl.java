package ua.com.lsd25.repository.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.lsd25.domain.user.Role;
import ua.com.lsd25.repository.RepositoryException;
import ua.com.lsd25.repository.UserRoleRepository;
import ua.com.lsd25.repository.query.UserRoleQuery;

/**
 * @author vzagnitko
 */
@Log4j
@Repository
public class UserRoleRepositoryImpl implements UserRoleRepository {

    @Autowired
    private UserRoleQuery userRoleQuery;

    @Override
    public long saveUserRole(Role role) throws RepositoryException {
        try {
            return userRoleQuery.save(role).getId();
        } catch (Exception exc) {
            throw new RepositoryException(exc, "Cannot save user role: " + role);
        }
    }

}
