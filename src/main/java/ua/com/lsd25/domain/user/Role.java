package ua.com.lsd25.domain.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author vzagnitko
 */
@Entity
@Table(name = "user_role", schema = "public")
@EqualsAndHashCode
@ToString
public class Role implements GrantedAuthority, Serializable {

    public static Role ADMIN = new Role(UserRole.ADMIN);
    public static Role USER = new Role(UserRole.USER);

    @Getter
    @Setter
    @Id
    @Column(name = "ur_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    @Setter
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

    private enum UserRole {
        ADMIN,
        USER
    }

}