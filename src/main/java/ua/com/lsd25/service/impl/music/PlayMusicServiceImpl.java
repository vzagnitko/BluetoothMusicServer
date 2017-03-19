package ua.com.lsd25.service.impl.music;

import lombok.NonNull;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.lsd25.service.ApplicationException;
import ua.com.lsd25.service.MusicService;
import ua.com.lsd25.service.PlayMusicService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author vzagnitko
 */
@Service
public class PlayMusicServiceImpl implements PlayMusicService {

    private static final Logger LOG = Logger.getLogger(PlayMusicServiceImpl.class);

    @Autowired
    private MusicService musicService;

    private ExecutorService executor;

    @Override
    public void playMusic(@NonNull Long musicId) throws ApplicationException {
        try {
            byte[] musicBytes = musicService.getMusicInputStream(musicId);
//            AudioPlayer d = AudioPlayer.player;
//            d.start(musicBytes);
//            PlayMusic musicThread = new PlayMusicThread(is);
//            musicThreadMap.put(musicId, musicThread);
//            executor.submit(musicThread);
        } catch (Exception exc) {
            throw new ApplicationException("Cannot play music");
        }
    }

    @Override
    public void suspendMusic(@NonNull Long musicId) throws ApplicationException {
//        PlayMusic playMusic = musicThreadMap.get(musicId);
//        if (playMusic == null) {
//            throw new ApplicationException(String.format("Cannot find music with music id: %d", musicId));
//        }
//        playMusic.suspend();
    }

    @Override
    public void resumeMusic(@NonNull Long musicId) throws ApplicationException {
//        PlayMusic playMusic = musicThreadMap.get(musicId);
//        if (playMusic == null) {
//            throw new ApplicationException(String.format("Cannot find music with music id: %d", musicId));
//        }
//        playMusic.resume();
    }

    @PostConstruct
    public void onStart() {
        executor = Executors.newWorkStealingPool();
    }

    @PreDestroy
    public void onStop() {
        executor.shutdown();
    }

}
