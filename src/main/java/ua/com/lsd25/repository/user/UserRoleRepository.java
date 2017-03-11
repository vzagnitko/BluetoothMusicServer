package ua.com.lsd25.repository.user;

import ua.com.lsd25.domain.user.Role;
import ua.com.lsd25.repository.RepositoryException;

/**
 * @author vzagnitko
 */
public interface UserRoleRepository {

    long saveUserRole(Role role) throws RepositoryException;

}
