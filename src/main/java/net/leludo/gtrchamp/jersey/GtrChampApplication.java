package net.leludo.gtrchamp.jersey;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Configuration of the Jersey web service controllers.
 */
@ApplicationPath("ws")
public class GtrChampApplication extends ResourceConfig {
    /**
     * Constructor.
     */
    public GtrChampApplication() {
        packages("net.leludo.gtrchamp.ws").register(JacksonFeature.class)
                .register(CORSResponseFilter.class);
    }
}
