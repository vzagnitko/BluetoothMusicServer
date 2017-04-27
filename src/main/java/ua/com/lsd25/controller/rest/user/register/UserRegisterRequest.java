package ua.com.lsd25.controller.rest.user.register;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * @author vzagnitko
 */
@Data
public class UserRegisterRequest implements Serializable {

    @NotBlank(message = "{userRegisterRequest.empty.firstName}")
    @Length(min = 2, max = 50, message = "{userRegisterRequest.incorrect.firstname.length}")
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank(message = "{userRegisterRequest.empty.lastName}")
    @Length(min = 2, max = 50, message = "{userRegisterRequest.incorrect.lastname.length}")
    @JsonProperty("last_name")
    private String lastName;

    @Email(message = "{userRegisterRequest.incorrect.mail}")
    @NotEmpty(message = "{userRegisterRequest.empty.mail}")
    @Length(max = 50, message = "{userRegisterRequest.incorrect.mail.length}")
    @JsonProperty("username")
    private String username;

    @Length(min = 8, max = 100, message = "{userRegisterRequest.incorrect.password.length}")
    @NotEmpty(message = "{userRegisterRequest.empty.password}")
    @JsonProperty("password")
    private String password;

    public UserRegisterRequest() {

    }

}
