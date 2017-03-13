package ua.com.lsd25.bluetoth;

import org.apache.log4j.Logger;

import javax.bluetooth.RemoteDevice;
import javax.microedition.io.StreamConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author vzagnitko
 */
public class ThreadedEchoHandler extends Thread {

    private static final Logger LOG = Logger.getLogger(ThreadedEchoHandler.class);

    private StreamConnection conn;
    private String clientName;
    private InputStream in;
    private OutputStream out;

    // globals
    private volatile boolean isRunning = false;

    public ThreadedEchoHandler(StreamConnection conn) {
        this.conn = conn;
        // store the name of the connected client
        clientName = reportDeviceName(conn);
        LOG.info("Handler spawned for client: " + clientName);
    } // end of ThreadedEchoHandler()

    private String reportDeviceName(StreamConnection conn) {
        // return the friendly name of the device being connected
        String devName;
        try {
            RemoteDevice rd = RemoteDevice.getRemoteDevice(conn);
            devName = rd.getFriendlyName(false);
        } catch (IOException e) {
            devName = "device ??";
        }
        return devName;
    }

    @Override
    public void run() {
        try {
            in = conn.openInputStream();
            out = conn.openOutputStream();

            processMsgs();

            LOG.info(" Closing " + clientName + " connection");
            if (conn != null) {
                in.close();
                out.close();
                conn.close();
            }
        } catch (IOException exc) {
            LOG.error(exc);
        }
    } // end of run()


    private void processMsgs() {
        isRunning = true;
        String line;
        while (isRunning) {
            if ((line = readData()) == null) {
                isRunning = false;
            } else { // there is some input
                LOG.info("  " + clientName + " --> " + line);

                if (line.trim().equals("bye$$")) {
                    isRunning = false;
                } else {
                    String upper = line.trim().toUpperCase();
                    if (isRunning) {
                        LOG.info("  " + clientName + " <-- " + line);
                        sendMessage(upper);
                    }
                }
            }
        }
    }


    private String readData() {
        byte[] data = null;
        try {
            int len = in.read(); // get the message length
            if (len <= 0) {
                LOG.info(clientName + ": Message Length Error");
                return null;
            } else {
                data = new byte[len];
                len = 0;
                // read the message
                while (len != data.length) {
                    int ch = in.read(data, len, data.length - len);
                    if (ch == -1) {
                        LOG.info(clientName + ": Message Read Error");
                        return null;
                    }
                    len += ch;
                }
            }
        } catch (IOException exc) {
            LOG.error(exc);
            return null;
        }
        return new String(data).trim();
    }


    private boolean sendMessage(String msg) {
        try {
            out.write(msg.length());
            out.write(msg.getBytes());
            out.flush();
            return true;
        } catch (IOException exc) {
            LOG.error(exc);
            return false;
        }

    }

    public void closeDown() {
        isRunning = false;
    }

}
