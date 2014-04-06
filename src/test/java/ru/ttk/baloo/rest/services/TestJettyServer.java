package ru.ttk.baloo.rest.services;

import org.junit.Test;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 *
 */
public class TestJettyServer {

    private Server server;

    @Test
    public void testRunServer() throws Exception {
        // rest-oauth-0.0.1-SNAPSHOT.war

        server = new Server(8080);
        server.setStopAtShutdown(true);
        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath("/rest-oauth");
        webAppContext.setResourceBase("src/main/webapp");
        webAppContext.setClassLoader(getClass().getClassLoader());
        server.addHandler(webAppContext);
        server.start();

        Thread.sleep(30000);

        server.stop();
    }

}
