package ua.com.lsd25.jms;

import ua.com.lsd25.service.ApplicationException;

/**
 * @author vzagnitko
 */
public interface PlayMusicMessage {

    void sendMessageToPlayMusic(long music) throws ApplicationException;

}
