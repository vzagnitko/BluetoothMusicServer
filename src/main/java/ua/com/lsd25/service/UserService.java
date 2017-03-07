package ua.com.lsd25.service;

import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import ua.com.lsd25.domain.user.User;

/**
 * @author vzagnitko
 */
public interface UserService {

    User findUserById(@NonNull Long id) throws ApplicationException;

    User findUserByUsername(@NonNull String username) throws ApplicationException;

    long saveUser(@NonNull User user) throws ApplicationException;

    UserDetails findLoggedInUsername();

    void autologin(@NonNull String username, @NonNull String password);

}
