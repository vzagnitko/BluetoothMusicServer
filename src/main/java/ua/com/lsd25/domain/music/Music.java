package ua.com.lsd25.domain.music;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;
import ua.com.lsd25.domain.user.User;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author vzagnitko
 */
@Entity
@Table(name = "music", schema = "public", indexes = {
        @Index(name = "idx_music_name", columnList = "m_name", unique = true)
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Music implements Serializable {

    @Id
    @Column(name = "m_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    @Column(name = "m_name", length = 100, nullable = false)
    private String name;

    @Column(name = "m_is_favorite", nullable = false)
    private boolean isFavorite = false;

    @JoinColumn(name = "m_user_id")
    @OneToOne(cascade = CascadeType.REMOVE)
    private User user;

}
