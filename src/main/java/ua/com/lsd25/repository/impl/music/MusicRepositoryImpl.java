package ua.com.lsd25.repository.impl.music;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.lsd25.domain.music.Music;
import ua.com.lsd25.repository.RepositoryException;
import ua.com.lsd25.repository.music.MusicRepository;
import ua.com.lsd25.repository.query.music.MusicQuery;

import java.util.List;

/**
 * @author vzagnitko
 */
@Repository
public class MusicRepositoryImpl implements MusicRepository {

    private static final Logger LOG = Logger.getLogger(MusicRepositoryImpl.class);

    @Autowired
    private MusicQuery musicQuery;

    @Override
    public List<Music> findAllMusicsByUserId(long userId) throws RepositoryException {
        try {
            return musicQuery.findMusicsByUserId(userId);
        } catch (Exception exc) {
            throw new RepositoryException(exc, "Cannot find music by user id: " + userId);
        }
    }

    @Override
    public Music findMusicByName(String name) throws RepositoryException {
        try {
            return musicQuery.findMusicByName(name);
        } catch (Exception exc) {
            throw new RepositoryException(exc, "Cannot find music by name: " + name);
        }
    }

    @Override
    public Music findMusicById(long id) throws RepositoryException {
        try {
            return musicQuery.findOne(id);
        } catch (Exception exc) {
            throw new RepositoryException(exc, "Cannot find music by id: " + id);
        }
    }

    @Override
    public long saveMusic(Music music) throws RepositoryException {
        try {
            return musicQuery.save(music).getId();
        } catch (Exception exc) {
            throw new RepositoryException(exc, "Cannot save music: " + music);
        }
    }

}
