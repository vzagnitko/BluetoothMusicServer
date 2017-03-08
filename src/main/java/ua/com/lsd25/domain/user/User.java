package ua.com.lsd25.domain.user;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
@EqualsAndHashCode
@ToString
public class User implements UserDetails, Serializable {

    @Id
    @Column(name = "u_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 50)
    @Column(name = "u_first_name", nullable = false)
    private String firstName;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 50)
    @Column(name = "u_last_name", length = 50, nullable = false)
    private String lastName;

    @NotNull
    @NotEmpty
    @Column(name = "u_password", length = 100, nullable = false)
    private String password;

    @Email
    @Size(max = 50)
    @Column(name = "u_username", unique = true, length = 50, nullable = false)
    private String username;

    @NotNull
    @Column(name = "u_register_date")
    private Timestamp registerDate;

    @NotNull
    @NotEmpty
    @Column(name = "u_register_ip")
    private String registerIp;

    @JoinColumn(name = "u_user_role")
    @OneToOne(cascade = CascadeType.REMOVE)
    private Role role;

    @NotNull
    @Column(name = "u_is_enabled")
    private boolean isEnabled;

    @NotNull
    @Column(name = "u_is_account_non_expired")
    private boolean isAccountNonExpired = true;

    @NotNull
    @Column(name = "u_is_account_non_blocked")
    private boolean isAccountNonBlocked = true;

    @NotNull
    @Column(name = "u_is_credentials_non_expired")
    private boolean isCredentialsNonExpired = true;

    public User() {

    }

    public User(String firstName, String lastName, String password, String username, Role role, String registerIp) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.username = username;
        this.role = role;
        this.registerIp = registerIp;
        this.registerDate = new Timestamp(System.currentTimeMillis());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(getRole().getAuthority());
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        isAccountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonBlocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        isCredentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
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

    public void setAccountNonBlocked(boolean accountNonBlocked) {
        isAccountNonBlocked = accountNonBlocked;
    }
}
