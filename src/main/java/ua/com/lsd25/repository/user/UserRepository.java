package ua.com.lsd25.repository.user;

import lombok.NonNull;
import ua.com.lsd25.domain.user.User;
import ua.com.lsd25.repository.RepositoryException;

/**
 * @author vzagnitko
 */
public interface UserRepository {

    User findUserById(long id) throws RepositoryException;

    User findUserByUsername(@NonNull String username) throws RepositoryException;

    long saveUser(@NonNull User user) throws RepositoryException;

    boolean isExists(@NonNull String username) throws RepositoryException;

}
