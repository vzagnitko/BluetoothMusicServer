package ua.com.lsd25.service.impl;

import lombok.NonNull;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.com.lsd25.domain.music.Music;
import ua.com.lsd25.service.ApplicationException;
import ua.com.lsd25.service.MusicService;
import ua.com.lsd25.service.UserService;
import ua.com.lsd25.service.WifiTransferService;

import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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

    @Autowired
    public WifiTransferServiceImpl(MusicService musicService,
                                   UserService userService) {
        this.musicService = musicService;
        this.userService = userService;
    }

    @Override
    public void sendMusicStream(@NonNull Long musicId) throws ApplicationException {
        long userId = userService.getLoggedUser().getId();
        Music music = musicService.findMusicById(musicId);
        LOG.info(String.format("Start send via WIFI music: %s, user id: %d", music.getName(), userId));
        LOG.info("Start send via WIFI music id: " + music.getId());
        try (ServerSocket socket = new ServerSocket(datagramPort)) {
            try (Socket clientSocket = socket.accept()) {
                try (DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream())) {
                    byte[] musicBytes = musicService.getMusicBytes(music.getName());
                    dos.writeUTF(music.getName());
                    dos.writeInt(musicBytes.length);
                    dos.write(musicBytes);
                }
            }
        } catch (Exception exc) {
            LOG.error(exc);
            throw new ApplicationException(String.format("Cannot play music with id: %d", musicId));
        }
    }

    @Override
    public void sendStopMusic(@NonNull Long musicId) throws ApplicationException {
        throw new NotImplementedException("Not implemented!");
    }

    @Override
    public void sendResumeMusic(@NonNull Long musicId) throws ApplicationException {
        throw new NotImplementedException("Not implemented!");
    }

}
