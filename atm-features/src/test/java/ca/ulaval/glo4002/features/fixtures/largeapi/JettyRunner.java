package ca.ulaval.glo4002.features.fixtures.largeapi;

import ca.ulaval.glo4002.atm_api.AtmServer;

public class JettyRunner {
    public static final int JETTY_TEST_PORT = 15146;

    private static boolean alreadyStarted = false;
    private AtmServer server;

    public void startJettyServer() {
        if (!alreadyStarted) {
            alreadyStarted = true;
            Runtime.getRuntime().addShutdownHook(new JettyServerShutdown());
            startAtmServerInJetty();
        }
    }

    private void startAtmServerInJetty() {
        server = new AtmServer();
        server.start(JETTY_TEST_PORT, new AcceptanceLargeContext());
    }

    private class JettyServerShutdown extends Thread {
        public void run() {
            try {
                server.stop();
            } catch (Exception e) {
                // Nothing do to anyways
            }
        }
    }
}