package ca.ulaval.glo4002.features.fixtures.large;

import ca.ulaval.glo4002.atm_api.AtmServer;

public class JettyRunner {
    public static final int JETTY_TEST_PORT = 15146;

    private static boolean isFirstFeature = true;
    private AtmServer server;

    public void startJettyServer() {
        if (isFirstFeature) {
            Runtime.getRuntime().addShutdownHook(new JettyServerShutdown());
            startAtmServerInJetty();
            isFirstFeature = false;
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