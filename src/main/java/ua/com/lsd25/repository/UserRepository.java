package ua.com.lsd25.repository;

import lombok.NonNull;
import ua.com.lsd25.domain.user.User;

/**
 * @author vzagnitko
 */
public interface UserRepository {

    User findUserById(long id) throws RepositoryException;

    User findUserByUsername(@NonNull String username) throws RepositoryException;

    long saveUser(@NonNull User user) throws RepositoryException;

}
