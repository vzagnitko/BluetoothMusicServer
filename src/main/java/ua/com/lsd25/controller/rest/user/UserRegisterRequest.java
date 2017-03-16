package ua.com.lsd25.controller.rest.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * @author vzagnitko
 */
@EqualsAndHashCode
@ToString
public class UserRegisterRequest implements Serializable {

    @NotBlank(message = "{userRegisterRequest.empty.firstname}")
    @Length(min = 2, max = 50, message = "{userRegisterRequest.incorrect.firstname.length}")
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank(message = "{userRegisterRequest.empty.lastname}")
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
