package ua.com.lsd25.jms.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import ua.com.lsd25.jms.PlayMusicMessage;
import ua.com.lsd25.service.ApplicationException;
import ua.com.lsd25.service.WifiTransferService;

/**
 * @author vzagnitko
 */
@Component
public class PlayMusicMessageImpl implements PlayMusicMessage {

    private static final Logger LOG = Logger.getLogger(PlayMusicMessageImpl.class);

    @Autowired
    private WifiTransferService wifiTransferService;

    @Override
    @JmsListener(destination = "music-queue")
    public void sendMessageToPlayMusic(long musicId) throws ApplicationException {
        LOG.info("Play music by music id: " + musicId);
        wifiTransferService.sendMusicStream(musicId);
    }

}
