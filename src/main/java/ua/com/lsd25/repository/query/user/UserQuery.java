package ua.com.lsd25.repository.query.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ua.com.lsd25.domain.user.User;

/**
 * @author vzagnitko
 */
public interface UserQuery extends CrudRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.username = :username")
    User findUserByUsername(@Param(value = "username") String username);

    @Query("SELECT 1 FROM User u WHERE u.username = :username")
//    @Query("SELECT EXISTS(SELECT 1 FROM User u WHERE u.username = :username)")
    Long isExistsUser(@Param(value = "username") String username);

}
