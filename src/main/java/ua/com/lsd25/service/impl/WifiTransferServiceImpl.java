package ua.com.lsd25.service.impl;

import lombok.NonNull;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import ua.com.lsd25.service.ApplicationException;
import ua.com.lsd25.service.MusicService;
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

    @Autowired
    private MusicService musicService;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${wifi.socket.server.port}")
    private Integer wifiPort;

    @Override
    public void sendMusicStream(@NonNull Long musicId) throws ApplicationException {
        LOG.info("Start send via WIFI music id: " + musicId);
        try (ServerSocket socket = new ServerSocket(1755)) {
            try (Socket clientSocket = socket.accept()) {
                try (DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream())) {
                    byte[] musicBytes = musicService.getMusicInputStream(musicId);
                    dos.writeUTF("Test.mp3");
                    dos.writeInt(musicBytes.length);
                    dos.write(musicBytes);
                }
            }
        } catch (Exception exc) {
            LOG.error(exc);
            throw new ApplicationException(String.format("Cannot play music with id: %d", musicId));
        }
    }

}
