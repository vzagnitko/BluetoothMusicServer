package ua.com.lsd25.domain.music;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import ua.com.lsd25.domain.DomainBuildWrapper;
import ua.com.lsd25.domain.user.User;

import javax.persistence.*;

/**
 * @author vzagnitko
 */
@Entity
@Table(name = "music", schema = "public", indexes = {
        @Index(name = "idx_music_name", columnList = "m_name", unique = true)
})
@EqualsAndHashCode
@ToString
public class Music implements DomainBuildWrapper<MusicWrapper> {

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

    public Music() {

    }

    public Music(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public MusicWrapper buildWrapper() {
        return new MusicWrapper(this);
    }

}
