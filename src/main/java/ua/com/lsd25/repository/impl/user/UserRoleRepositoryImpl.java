package ua.com.lsd25.repository.impl.user;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.lsd25.domain.user.role.Role;
import ua.com.lsd25.repository.RepositoryException;
import ua.com.lsd25.repository.query.user.UserRoleQuery;
import ua.com.lsd25.repository.user.UserRoleRepository;

/**
 * @author vzagnitko
 */
@Repository
public class UserRoleRepositoryImpl implements UserRoleRepository {

    private static final Logger LOG = Logger.getLogger(UserRoleRepositoryImpl.class);

    private final UserRoleQuery userRoleQuery;

    @Autowired
    public UserRoleRepositoryImpl(UserRoleQuery userRoleQuery) {
        this.userRoleQuery = userRoleQuery;
    }

    @Override
    public long saveUserRole(Role role) throws RepositoryException {
        try {
            return userRoleQuery.save(role).getId();
        } catch (Exception exc) {
            throw new RepositoryException(exc, "Cannot save user role: " + role);
        }
    }

}
