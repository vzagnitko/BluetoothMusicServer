package ua.com.lsd25.repository;

import ua.com.lsd25.domain.user.Role;

/**
 * @author vzagnitko
 */
public interface UserRoleRepository {

    long saveUserRole(Role role) throws RepositoryException;

}
