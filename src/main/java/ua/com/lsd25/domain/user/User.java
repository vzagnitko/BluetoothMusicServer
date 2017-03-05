package ua.com.lsd25.domain.user;

import com.google.common.collect.Sets;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
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

    @Getter
    @Setter
    @Id
    @Column(name = "u_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    @Setter
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 50)
    @Column(name = "u_first_name", nullable = false)
    private String firstName;

    @Getter
    @Setter
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 50)
    @Column(name = "u_last_name", length = 50, nullable = false)
    private String lastName;

    @Getter
    @Setter
    @NotNull
    @NotEmpty
    @Column(name = "u_password", length = 100, nullable = false)
    private String password;

    @Getter
    @Setter
    @Email
    @Size(max = 50)
    @Column(name = "u_username", unique = true, length = 50, nullable = false)
    private String username;

    @Getter
    @Setter
    @NotNull
    @Column(name = "u_register_date")
    private Timestamp registerDate;

    @Getter
    @Setter
    @NotNull
    @NotEmpty
    @Column(name = "u_register_ip")
    private String registerIp;

    @Getter
    @Setter
    @JoinColumn(name = "u_user_role")
    @OneToOne(cascade = CascadeType.REMOVE)
    private Role role;

    @Setter
    @NotNull
    @Column(name = "u_is_enabled")
    private boolean isEnabled;

    @Setter
    @NotNull
    @Column(name = "u_is_account_non_expired")
    private boolean isAccountNonExpired = true;

    @Setter
    @NotNull
    @Column(name = "u_is_account_non_blocked")
    private boolean isAccountNonBlocked = true;

    @Setter
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
        return Sets.newHashSet(this.role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonBlocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

}
