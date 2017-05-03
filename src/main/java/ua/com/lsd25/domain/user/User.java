package ua.com.lsd25.domain.user;

import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import ua.com.lsd25.domain.user.role.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * @author vzagnitko
 */
@Entity
@Table(name = "user", schema = "public", indexes = {
        @Index(name = "idx_user_username", columnList = "u_username", unique = true)
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "password")
public class User implements UserDetails, Serializable {

    @Id
    @Column(name = "u_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "{userRegisterRequest.empty.firstName}")
    @Length(min = 2, max = 50, message = "{userRegisterRequest.incorrect.firstname.length}")
    @Column(name = "u_first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "{userRegisterRequest.empty.lastName}")
    @Length(min = 2, max = 50, message = "{userRegisterRequest.incorrect.lastname.length}")
    @Column(name = "u_last_name", length = 50, nullable = false)
    private String lastName;

    @Length(min = 8, max = 100, message = "{userRegisterRequest.incorrect.password.length}")
    @NotEmpty(message = "{userRegisterRequest.empty.password}")
    @Column(name = "u_password", length = 100, nullable = false)
    private String password;

    @Email(message = "{userRegisterRequest.incorrect.mail}")
    @NotEmpty(message = "{userRegisterRequest.empty.mail}")
    @Length(max = 50, message = "{userRegisterRequest.incorrect.mail.length}")
    @Column(name = "u_username", unique = true, length = 50, nullable = false)
    private String username;

    @Column(name = "u_register_date")
    private Timestamp registerDate;

    @NotEmpty
    @Column(name = "u_register_ip")
    private String registerIp;

    @JoinColumn(name = "u_user_role")
    @OneToOne(cascade = CascadeType.REMOVE)
    private Role role;

    @Column(name = "u_is_enabled")
    private boolean isEnabled;

    @Column(name = "u_is_account_non_expired")
    private boolean isAccountNonExpired;

    @Column(name = "u_is_account_non_locked")
    private boolean isAccountNonLocked;

    @Column(name = "u_is_credentials_non_expired")
    private boolean isCredentialsNonExpired;

//    public User() {
//
//    }

//    public User(String firstName, String lastName, String password, String username, Role role, String registerIp) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.password = password;
//        this.username = username;
//        this.role = role;
//        this.registerIp = registerIp;
//        this.registerDate = new Timestamp(System.currentTimeMillis());
//    }
//
//    public User(UserRegisterRequest userRegisterRequest, String registerIp) {
//        this.firstName = userRegisterRequest.getFirstName();
//        this.lastName = userRegisterRequest.getLastName();
//        this.password = userRegisterRequest.getPassword();
//        this.username = userRegisterRequest.getUsername();
//        this.role = Role.USER;
//        this.registerIp = registerIp;
//        this.registerDate = new Timestamp(System.currentTimeMillis());
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(getRole().getAuthority());
    }

//    @Override
//    public String getPassword() {
//        return password;
//    }

//    public void setPassword(String password) {
//        this.password = password;
//    }

//    @Override
//    public String getUsername() {
//        return username;
//    }

//    @Override
//    public boolean isAccountNonExpired() {
//        return isAccountNonExpired;
//    }

//    public void setAccountNonExpired(boolean accountNonExpired) {
//        isAccountNonExpired = accountNonExpired;
//    }

//    @Override
//    public boolean isAccountNonLocked() {
//        return isAccountNonLocked;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return isCredentialsNonExpired;
//    }

//    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
//        isCredentialsNonExpired = credentialsNonExpired;
//    }

//    @Override
//    public boolean isEnabled() {
//        return isEnabled;
//    }

//    public void setEnabled(boolean enabled) {
//        isEnabled = enabled;
//    }
//
//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public Timestamp getRegisterDate() {
//        return registerDate;
//    }
//
//    public void setRegisterDate(Timestamp registerDate) {
//        this.registerDate = registerDate;
//    }
//
//    public String getRegisterIp() {
//        return registerIp;
//    }
//
//    public void setRegisterIp(String registerIp) {
//        this.registerIp = registerIp;
//    }
//
//    public Role getRole() {
//        return role;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }
//
//    public void setAccountNonBlocked(boolean accountNonBlocked) {
//        isAccountNonBlocked = accountNonBlocked;
//    }

}
