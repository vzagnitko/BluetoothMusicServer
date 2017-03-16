package ua.com.lsd25.domain.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import ua.com.lsd25.domain.Wrapper;
import ua.com.lsd25.domain.user.role.Role;

import java.sql.Timestamp;

/**
 * @author vzagnitko
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode
@ToString
public class UserWrapper implements Wrapper {

    @JsonProperty("id")
    private long id;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("username")
    private String username;

    @JsonProperty("register_date")
    private Timestamp registerDate;

    @JsonProperty("register_ip")
    private String registerIp;

    @JsonProperty("role")
    private Role role;

    public UserWrapper() {

    }

    public UserWrapper(long id, String firstName, String lastName, String username, Timestamp registerDate,
                       String registerIp, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.registerDate = registerDate;
        this.registerIp = registerIp;
        this.role = role;
    }

    public UserWrapper(User user) {
        this(user.getId(), user.getFirstName(), user.getLastName(), user.getUsername(), user.getRegisterDate(),
                user.getRegisterIp(), user.getRole());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Timestamp registerDate) {
        this.registerDate = registerDate;
    }

    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
