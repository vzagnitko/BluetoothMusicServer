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

    private final WifiTransferService wifiTransferService;

    @Autowired
    public PlayMusicMessageImpl(WifiTransferService wifiTransferService) {
        this.wifiTransferService = wifiTransferService;
    }

    @Override
    @JmsListener(destination = "play-music")
    public void sendMessageToPlayMusic(long musicId) throws ApplicationException {
        LOG.info(String.format("Play music id: %d", musicId));
        wifiTransferService.sendMusicStream(musicId);
    }

    @Override
    @JmsListener(destination = "stop-music")
    public void sendMessageToStopMusic(long musicId) throws ApplicationException {
        LOG.info(String.format("Stop music id: %d", musicId));
        wifiTransferService.sendStopMusic(musicId);
    }

    @Override
    @JmsListener(destination = "resume-music")
    public void sendMessageToSuspendMusic(long musicId) throws ApplicationException {
        LOG.info(String.format("Resume music id: %d", musicId));
        wifiTransferService.sendResumeMusic(musicId);
    }

}
