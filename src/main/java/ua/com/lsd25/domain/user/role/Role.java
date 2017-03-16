package ua.com.lsd25.domain.user.role;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import ua.com.lsd25.domain.Domain;

import javax.persistence.*;

/**
 * @author vzagnitko
 */
@Entity
@Table(name = "user_role", schema = "public")
@EqualsAndHashCode
@ToString
public class Role implements GrantedAuthority, Domain {

    public static Role ADMIN = new Role(UserRole.ADMIN);
    public static Role USER = new Role(UserRole.USER);

    @Id
    @Column(name = "ur_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "ur_role")
    @Enumerated(EnumType.ORDINAL)
    private UserRole userRole;

    public Role() {

    }

    private Role(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String getAuthority() {
        return userRole.name();
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private enum UserRole {
        ADMIN,
        USER
    }

}