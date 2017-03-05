package ua.com.lsd25.domain.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author vzagnitko
 */
@Entity
@Table(name = "user_role", schema = "public")
@EqualsAndHashCode
@ToString
public class Role implements Serializable {

    public enum UserRole {
        ADMIN,
        USER
    }

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

    public Role(UserRole userRole) {
        this.userRole = userRole;
    }

}