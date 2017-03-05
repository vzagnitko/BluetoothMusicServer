package ua.com.lsd25.domain.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author vzagnitko
 */
@Entity
@Table(name = "user", schema = "public", indexes = {
        @Index(name = "idx_user_username", columnList = "u_username", unique = true)
})
@EqualsAndHashCode
@ToString
public class User implements Serializable {

    @Getter
    @Setter
    @Id
    @Column(name = "u_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    @Setter
    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "u_first_name", nullable = false)
    private String firstName;

    @Getter
    @Setter
    @NotNull
    @Size(min = 2, max = 50)
    @Column(name = "u_last_name", length = 50, nullable = false)
    private String lastName;

    @Getter
    @Setter
    @NotNull
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
    @Column(name = "u_register_ip")
    private String registerIp;

    @Getter
    @Setter
    @JoinColumn(name = "u_user_role")
    @OneToOne(cascade = CascadeType.REMOVE)
    private Role role;

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

}
