package ua.com.lsd25.domain.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.lsd25.domain.user.role.Role;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author vzagnitko
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserWrapper implements Serializable {

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

}
