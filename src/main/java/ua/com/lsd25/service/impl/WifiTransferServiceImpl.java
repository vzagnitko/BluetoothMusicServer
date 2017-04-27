package ua.com.lsd25.service.impl;

import lombok.NonNull;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.com.lsd25.service.ApplicationException;
import ua.com.lsd25.service.MusicService;
import ua.com.lsd25.service.UserService;
import ua.com.lsd25.service.WifiTransferService;
import ua.com.lsd25.service.impl.music.send.SendMusicDataThread;
import ua.com.lsd25.service.impl.music.send.SendMusicDataThreadImpl;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author vzagnitko
 */
@Service
public class WifiTransferServiceImpl implements WifiTransferService {

    private static final Logger LOG = Logger.getLogger(WifiTransferServiceImpl.class);

    private final MusicService musicService;

    private final UserService userService;

    @Value("${datagram.socket.server.port}")
    private Integer datagramPort;

    @Value("${datagram.socket.client.host}")
    private String datagramClientHost;

    private ExecutorService executor;

    @Autowired
    public WifiTransferServiceImpl(MusicService musicService,
                                   UserService userService) {
        this.musicService = musicService;
        this.userService = userService;
    }

    @Override
    public void sendMusicStream(@NonNull Long musicId) throws ApplicationException {
        long userId = userService.getLoggedUser().getId();
        byte[] musicBytes = musicService.getMusicBytes(musicId);
        LOG.info(String.format("Start send via WIFI music id: %d, user id: %d", musicId, userId));
        executor.submit(new SendMusicDataThreadImpl(userId, datagramClientHost, datagramPort, musicBytes));
    }

    @Override
    public void sendStopMusic(@NonNull Long musicId) throws ApplicationException {
        long userId = userService.getLoggedUser().getId();
        SendMusicDataThread runnedThread = findRunnedThread(userId);
        if (runnedThread == null) {
            throw new ApplicationException("Cannot find any played thread!");
        }
        runnedThread.stop();
    }

    @Override
    public void sendResumeMusic(@NonNull Long musicId) throws ApplicationException {
        long userId = userService.getLoggedUser().getId();
        SendMusicDataThread runnedThread = findRunnedThread(userId);
        if (runnedThread == null) {
            throw new ApplicationException("Cannot find any played thread!");
        }
        runnedThread.start();
    }

    private SendMusicDataThread findRunnedThread(long userId) {
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread : threadSet) {
            if (thread.getName().equals(String.join(SendMusicDataThreadImpl.THREAD_NAME, String.valueOf(userId)))) {
                return (SendMusicDataThread) thread;
            }
        }
        return null;
    }

    @PostConstruct
    private void onStart() {
        executor = Executors.newWorkStealingPool();
    }

    @PreDestroy
    private void onStop() {
        executor.shutdown();
    }

}
