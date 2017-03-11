package ua.com.lsd25.repository.music;

import ua.com.lsd25.domain.music.Music;
import ua.com.lsd25.repository.RepositoryException;

import java.util.List;

/**
 * @author vzagnitko
 */
public interface MusicRepository {

    List<Music> findAllMusicsByUserId(long userId) throws RepositoryException;

    Music findMusicByName(String name) throws RepositoryException;

    long saveMusic(Music music) throws RepositoryException;

}
