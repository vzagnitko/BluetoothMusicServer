package ua.com.lsd25.controller.rest.user.login;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * @author vzagnitko
 */
@Data
public class UserLoginRequest implements Serializable {

    @Email(message = "{userLoginRequest.incorrect.mail}")
    @NotEmpty(message = "{userLoginRequest.empty.mail}")
    @Length(max = 50, message = "{userLoginRequest.incorrect.mail.length}")
    @JsonProperty("username")
    private String username;

    @Length(min = 8, max = 100, message = "{userLoginRequest.incorrect.password.length}")
    @NotEmpty(message = "{userLoginRequest.empty.password}")
    @JsonProperty("password")
    private String password;

    @JsonProperty(value = "remember_me")
    private boolean isRememberMe;

    public UserLoginRequest() {

    }

}
