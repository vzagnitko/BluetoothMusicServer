package ua.com.lsd25.service.impl;

import lombok.NonNull;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.lsd25.domain.music.Music;
import ua.com.lsd25.domain.user.User;
import ua.com.lsd25.repository.RepositoryException;
import ua.com.lsd25.repository.music.MusicRepository;
import ua.com.lsd25.service.ApplicationException;
import ua.com.lsd25.service.MusicService;
import ua.com.lsd25.service.UserService;

import java.util.List;

/**
 * @author vzagnitko
 */
@Service
@CacheConfig(cacheNames = "/musics")
public class MusicServiceImpl implements MusicService {

    private static final Logger LOG = Logger.getLogger(MusicServiceImpl.class);

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private UserService userService;

    @Override
    @Cacheable
    @Transactional(readOnly = true)
    public List<Music> findMusics() throws ApplicationException {
        User loggedUser = userService.getLoggedUser();
        long userId = loggedUser.getId();
        try {
            return musicRepository.findAllMusicsByUserId(userId);
        } catch (RepositoryException exc) {
            LOG.error(exc);
            throw new ApplicationException(exc, "Cannot retrieve music by user id: " + userId);
        }
    }

    @Override
    @Cacheable
    @Transactional(readOnly = true)
    public Music findMusicByName(@NonNull String name) throws ApplicationException {
        try {
            return musicRepository.findMusicByName(name);
        } catch (RepositoryException exc) {
            LOG.error(exc);
            throw new ApplicationException(exc, "Cannot retrieve music by name: " + name);
        }
    }

    @Override
    @CacheEvict
    @Transactional
    public long saveMusic(@NonNull String musicName, @NonNull byte[] musicBytes) throws ApplicationException {
        User loggedUser = userService.getLoggedUser();
        Music music = new Music(musicName, loggedUser, musicBytes);
        try {
            return musicRepository.saveMusic(music);
        } catch (RepositoryException exc) {
            LOG.error(exc);
            throw new ApplicationException(exc, "Cannot save music: " + musicName);
        }
    }

}
