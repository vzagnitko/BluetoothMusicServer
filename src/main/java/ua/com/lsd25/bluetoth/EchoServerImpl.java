package ua.com.lsd25.bluetoth;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author vzagnitko
 */
@Service
public class EchoServerImpl implements EchoServer {

    private static final Logger LOG = Logger.getLogger(EchoServerImpl.class);

    private static final String UUID_STRING = "11111111111111111111111111111111"; // 32 hex digits
    private static final String SERVICE_NAME = "echoserver";

    private StreamConnectionNotifier server;
    // globals
    private ArrayList<ThreadedEchoHandler> handlers;
    private volatile boolean isRunning = false;

    @PostConstruct
    public void onStart() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> closeDown()));

        initDevice();
        createRFCOMMConnection();
        processClients();
    }

    private void initDevice() {
        try { // make the server's bluetooth device discoverable
            LocalDevice local = LocalDevice.getLocalDevice();

            LOG.info("Device name: " + local.getFriendlyName());
            LOG.info("Bluetooth Address: " + local.getBluetoothAddress());

            boolean res = local.setDiscoverable(DiscoveryAgent.GIAC);

            LOG.info("Discoverability set: " + res);
        } catch (BluetoothStateException exc) {
            throw new RuntimeException(exc);
        }
    } // end of initDevice()

    private void createRFCOMMConnection() {
        try {
            LOG.info("Start advertising " + SERVICE_NAME + "...");

            server = (StreamConnectionNotifier) Connector.open(
                    "btspp://localhost:" + UUID_STRING +
                            ";name=" + SERVICE_NAME + ";authenticate=false");
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    } // end of createRFCOMMConnection()

    private void processClients() {
        new Thread(() -> {
            isRunning = true;
            try {
                while (isRunning) {
                    LOG.info("Waiting for incoming connection...");
                    StreamConnection conn = server.acceptAndOpen();
                    // wait for a client connection
                    ThreadedEchoHandler handler = new ThreadedEchoHandler(conn);
                    handlers.add(handler);
                    handler.start();
                }
            } catch (IOException exc) {
                LOG.error(exc);
            }
        }).start();
    } // end of processClients

    private void closeDown() {
        LOG.info("closing down server");
        if (isRunning) {
            isRunning = false;
            try {
                server.close();
            } catch (IOException exc) {
                LOG.error(exc);
            }

            // close all the handlers
            for (ThreadedEchoHandler hand : handlers) {
                hand.closeDown();
            }
            handlers.clear();
        }
    } // end of closeDown()

}
