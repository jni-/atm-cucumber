package ca.ulaval.glo4002.atm_api;

import java.util.EnumSet;
import java.util.Optional;

import javax.servlet.DispatcherType;

import ca.ulaval.glo4002.atm_api.contexts.Context;
import ca.ulaval.glo4002.atm_api.contexts.DevContext;
import ca.ulaval.glo4002.atm_api.contexts.ProdContext;
import ca.ulaval.glo4002.atm_api.rest.filters.EntityManagerContextFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class AtmServer {

    private static final String PORT_PROPERTY = "port";
    private static final int DEFAULT_PORT = 8585;

    private static final String CONTEXT_PROPERTY = "context";
    private static final String DEFAULT_CONTEXT = "prod";
    private Server server;

    public static void main(String[] args) {
        int httpPort = Optional.ofNullable(System.getProperty(PORT_PROPERTY)).map(p -> Integer.parseInt(p)).orElse(DEFAULT_PORT);
        Context context = resolveContext(Optional.ofNullable(System.getProperty(CONTEXT_PROPERTY)).orElse(DEFAULT_CONTEXT));

        AtmServer server = new AtmServer();
        server.start(httpPort, context);
        server.join();
    }

    private static Context resolveContext(String contextName) {
        switch (contextName) {
            case "prod":
                return new ProdContext();
            case "dev":
                return new DevContext();
            default:
                throw new RuntimeException("Cannot load context " + contextName);
        }
    }

    public void start(int httpPort, Context context) {
        context.apply();

        server = new Server(httpPort);
        ServletContextHandler servletContextHandler = new ServletContextHandler(server, "/");
        configurerJersey(servletContextHandler);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tryStopServer();
        }
    }

    public void join() {
        try {
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tryStopServer();
        }
    }

    private void tryStopServer() {
        try {
            server.destroy();
        } catch (Exception e) {
            return;
        }
    }

    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configurerJersey(ServletContextHandler servletContextHandler) {
        ServletContainer container = new ServletContainer(new ResourceConfig().packages("ca.ulaval.glo4002.atm_api.rest"));
        ServletHolder jerseyServletHolder = new ServletHolder(container);
        servletContextHandler.addServlet(jerseyServletHolder, "/*");
        servletContextHandler.addFilter(EntityManagerContextFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
    }
}
