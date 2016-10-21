package net.leludo.connector;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Jersey application entry point.
 */
@ApplicationPath("api")
public class WebApplication extends ResourceConfig {

    /**
     * Constructor. Initialize the packages containing the web services.
     */
    public WebApplication() {
        packages("net.leludo.gtrchamp.ws");
    }

}
