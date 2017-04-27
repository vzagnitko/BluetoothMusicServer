package ua.com.lsd25.repository.user;

import ua.com.lsd25.domain.user.User;
import ua.com.lsd25.repository.RepositoryException;

/**
 * @author vzagnitko
 */
public interface UserRepository {

    User findUserById(long id) throws RepositoryException;

    User findUserByUsername(String username) throws RepositoryException;

    long saveUser(User user) throws RepositoryException;

    boolean isExists(String username) throws RepositoryException;

}
