package ua.com.lsd25.controller.rest.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * @author vzagnitko
 */
@EqualsAndHashCode
@ToString
public class UserLoginRequest implements Serializable {

    @Getter
    @Setter
    @Email
    private String username;

    @Getter
    @Setter
    @NotEmpty
    private String password;

    public UserLoginRequest() {

    }

}
