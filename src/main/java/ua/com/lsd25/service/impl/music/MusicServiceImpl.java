package ua.com.lsd25.service.impl.music;

import lombok.NonNull;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.com.lsd25.domain.music.Music;
import ua.com.lsd25.domain.user.User;
import ua.com.lsd25.repository.RepositoryException;
import ua.com.lsd25.repository.music.MusicRepository;
import ua.com.lsd25.service.ApplicationException;
import ua.com.lsd25.service.FileService;
import ua.com.lsd25.service.MusicService;
import ua.com.lsd25.service.UserService;

import java.util.List;

/**
 * @author vzagnitko
 */
@Service
@CacheConfig(cacheNames = "musics")
public class MusicServiceImpl implements MusicService {

    private static final Logger LOG = Logger.getLogger(MusicServiceImpl.class);

    private final MusicRepository musicRepository;

    private final UserService userService;

    private final FileService fileService;

    @Autowired
    public MusicServiceImpl(MusicRepository musicRepository,
                            UserService userService,
                            FileService fileService) {
        this.musicRepository = musicRepository;
        this.userService = userService;
        this.fileService = fileService;
    }

    @Override
    @Cacheable
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Music> findMusics() throws ApplicationException {
        User loggedUser = userService.getLoggedUser();
        long userId = loggedUser.getId();
        try {
            LOG.info(String.format("Find music by user id: %d", userId));
            return musicRepository.findAllMusicsByUserId(userId);
        } catch (RepositoryException exc) {
            LOG.error(exc);
            throw new ApplicationException(exc, "Cannot retrieve music by user id: " + userId);
        }
    }

    @Override
    @Cacheable
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Music findMusicByName(@NonNull String name) throws ApplicationException {
        try {
            LOG.info(String.format("Find music by name: %s", name));
            return musicRepository.findMusicByName(name);
        } catch (RepositoryException exc) {
            LOG.error(exc);
            throw new ApplicationException(exc, "Cannot retrieve music by name: " + name);
        }
    }

    @Override
    @Cacheable
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Music findMusicById(@NonNull Long id) throws ApplicationException {
        try {
            LOG.info(String.format("Find music by id: %d", id));
            return musicRepository.findMusicById(id);
        } catch (RepositoryException exc) {
            LOG.error(exc);
            throw new ApplicationException(exc, "Cannot retrieve music by id: " + id);
        }
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional
    public long saveMusic(@NonNull String musicName, @NonNull byte[] musicBytes) throws ApplicationException {
        LOG.info(String.format("Save music, name: %s", musicName));
        User loggedUser = userService.getLoggedUser();
        Music music = Music
                .builder()
                .name(musicName)
                .user(loggedUser)
                .build();
        try {
            long musicId = musicRepository.saveMusic(music);
            if (musicId != 0) {
                fileService.fastSaveFile(musicBytes, musicName);
            }
            return musicId;
        } catch (RepositoryException exc) {
            LOG.error(exc);
            throw new ApplicationException(exc, "Cannot save music: " + musicName);
        }
    }

    @Override
    @Cacheable
    public byte[] getMusicBytes(@NonNull String musicName) throws ApplicationException {
        try {
            LOG.info(String.format("Get music, music name: %s", musicName));
            return fileService.fastReadFile(musicName);
        } catch (ApplicationException exc) {
            LOG.error(exc);
            throw new ApplicationException(exc, "Cannot get music input stream by music name: " + musicName);
        }
    }

}
