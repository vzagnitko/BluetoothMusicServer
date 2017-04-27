package ua.com.lsd25.service.impl.music.send;

import java.util.concurrent.Callable;

/**
 * @author vzagnitko
 */
public interface SendMusicDataThread extends Callable<Void> {

    void start();

    void stop();

}
