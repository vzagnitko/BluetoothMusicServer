package ua.com.lsd25.repository.query.music;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ua.com.lsd25.domain.music.Music;

import java.util.List;

/**
 * @author vzagnitko
 */
public interface MusicQuery extends CrudRepository<Music, Long> {

    @Query("SELECT m FROM Music m WHERE m.user.id = :userId")
    List<Music> findMusicsByUserId(@Param("userId") long userId);

    @Query("SELECT m FROM Music m WHERE m.name = :name")
    Music findMusicByName(@Param("name") String name);

}
