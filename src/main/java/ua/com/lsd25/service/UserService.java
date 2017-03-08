package ua.com.lsd25.service;

import lombok.NonNull;
import ua.com.lsd25.domain.user.User;

/**
 * @author vzagnitko
 */
public interface UserService {

    User findUserById(@NonNull Long id) throws ApplicationException;

    User findUserByUsername(@NonNull String username) throws ApplicationException;

    boolean isExistsUser(@NonNull String username) throws ApplicationException;

    long saveUser(@NonNull User user) throws ApplicationException;

    User getLoggedUser();

    boolean isLoggedUser();

    void autologin(@NonNull String username, @NonNull String password);

}
