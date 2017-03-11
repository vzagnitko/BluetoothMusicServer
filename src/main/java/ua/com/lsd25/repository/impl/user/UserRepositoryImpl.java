package ua.com.lsd25.repository.impl.user;

import lombok.NonNull;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.lsd25.domain.user.User;
import ua.com.lsd25.repository.RepositoryException;
import ua.com.lsd25.repository.query.user.UserQuery;
import ua.com.lsd25.repository.user.UserRepository;

/**
 * @author vzagnitko
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final Logger LOG = Logger.getLogger(UserRepositoryImpl.class);

    @Autowired
    private UserQuery userQuery;

    @Override
    public User findUserById(long id) throws RepositoryException {
        try {
            return userQuery.findOne(id);
        } catch (Exception exc) {
            throw new RepositoryException(exc, "Cannot find user by id: " + id);
        }
    }

    @Override
    public User findUserByUsername(String username) throws RepositoryException {
        try {
            return userQuery.findUserByUsername(username);
        } catch (Exception exc) {
            throw new RepositoryException(exc, "Cannot find user by username: " + username);
        }
    }

    @Override
    public long saveUser(User user) throws RepositoryException {
        try {
            return userQuery.save(user).getId();
        } catch (Exception exc) {
            throw new RepositoryException(exc, "Cannot save user: " + user);
        }
    }

    @Override
    public boolean isExists(@NonNull String username) throws RepositoryException {
        try {
            Long id = userQuery.isExistsUser(username);
            return id != null && id != 0;
        } catch (Exception exc) {
            throw new RepositoryException(exc, "Cannot find user by username: " + username);
        }
    }

}
