package ua.com.lsd25.service.impl.music.send;

import org.apache.log4j.Logger;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author vzagnitko
 */
public class SendMusicDataThreadImpl implements SendMusicDataThread {

    public static final String THREAD_NAME = "thread_user_";

    private static final Logger LOG = Logger.getLogger(SendMusicDataThreadImpl.class);

    private final AtomicBoolean isPlayAudio = new AtomicBoolean(true);

    private String clientHost;

    private int datagramPort;

    private byte[] musicBytes;

    public SendMusicDataThreadImpl(long userId, String clientHost, int datagramPort, byte[] musicBytes) {
        Thread.currentThread().setName(String.join(THREAD_NAME, String.valueOf(userId)));
        this.clientHost = clientHost;
        this.datagramPort = datagramPort;
        this.musicBytes = musicBytes;
    }

    @Override
    public Void call() throws SendDataException {
        try (DatagramSocket clientSocket = new DatagramSocket(datagramPort)) {
            InetAddress inetAddress = InetAddress.getByName(clientHost);
            for (int i = 0; i < musicBytes.length; i += 1024) {
                synchronized (isPlayAudio) {
                    while (!isPlayAudio.get()) {
                        wait();
                    }
                }
                byte[] tempBuffer = new byte[1024];
                System.arraycopy(musicBytes, i, tempBuffer, 0, tempBuffer.length);
                DatagramPacket sendPacket = new DatagramPacket(tempBuffer, tempBuffer.length, inetAddress, datagramPort);
                clientSocket.send(sendPacket);
            }
            return null;
        } catch (Exception exc) {
            LOG.error("Cannot send data to client", exc);
            throw new SendDataException("Cannot send data to client!");
        }
    }


    @Override
    public void start() {
        isPlayAudio.compareAndSet(false, true);
        notify();
    }

    @Override
    public void stop() {
        isPlayAudio.compareAndSet(true, false);
    }

}
