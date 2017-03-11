package ua.com.lsd25.repository.query.user;

import org.springframework.data.repository.CrudRepository;
import ua.com.lsd25.domain.user.Role;

/**
 * @author vzagnitko
 */
public interface UserRoleQuery extends CrudRepository<Role, Long> {
}
