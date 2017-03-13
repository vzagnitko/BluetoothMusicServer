package ua.com.lsd25.domain.music;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import ua.com.lsd25.domain.DomainBuildWrapperMarker;
import ua.com.lsd25.domain.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author vzagnitko
 */
@Entity
@Table(name = "music", schema = "public", indexes = {
        @Index(name = "idx_music_name", columnList = "m_name", unique = true)
})
@EqualsAndHashCode
@ToString
public class Music implements DomainBuildWrapperMarker<MusicWrapper> {

    @Id
    @Column(name = "m_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NotEmpty
    @Column(name = "m_name", length = 100, nullable = false)
    private String name;

    @JoinColumn(name = "m_user_id")
    @OneToOne(cascade = CascadeType.REMOVE)
    private User user;

    @NotNull
    @Column(name = "m_music")
    private byte[] music;

    public Music() {

    }

    public Music(String name, User user, byte[] music) {
        this.name = name;
        this.user = user;
        this.music = music;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getMusic() {
        return music;
    }

    public void setMusic(byte[] music) {
        this.music = music;
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

    @Override
    public MusicWrapper buildWrapper() {
        return new MusicWrapper(this);
    }

}
