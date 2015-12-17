package net.leludo.gtrchamp.jersey;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("ws")
public class GtrChampApplication extends ResourceConfig {
    public GtrChampApplication() {
        packages("net.leludo.gtrchamp.ws").register(JacksonFeature.class);
    }
}
