package ua.com.lsd25.domain.user.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author vzagnitko
 */
@Entity
@Table(name = "user_role", schema = "public")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority, Serializable {

    @Id
    @Column(name = "ur_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "ur_role")
    @Enumerated(EnumType.ORDINAL)
    private UserRole userRole;

    @Override
    public String getAuthority() {
        return userRole.name();
    }

    public enum UserRole {
        ADMIN,
        USER
    }

}