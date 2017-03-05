package ua.com.lsd25.repository.query;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ua.com.lsd25.domain.user.User;

/**
 * @author vzagnitko
 */
public interface UserQuery extends CrudRepository<User, Long> {

    @Query("select u from User u where u.username = :username")
    User findUserByUsername(@Param(value = "username") String username);

}
