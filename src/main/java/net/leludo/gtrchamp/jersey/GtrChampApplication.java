package net.leludo.gtrchamp.jersey;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import net.leludo.gtrchamp.ws.ChampionshipWebService;
import net.leludo.gtrchamp.ws.CountryWebService;
import net.leludo.gtrchamp.ws.DriverWebService;
import net.leludo.gtrchamp.ws.RaceWebService;
import net.leludo.gtrchamp.ws.Stats;
import net.leludo.gtrchamp.ws.TrackWebService;

/**
 * Configuration of the Jersey web service controllers.
 */
@Component
@ApplicationPath("api")
public class GtrChampApplication extends ResourceConfig {
    /**
     * Constructor.
     */
    public GtrChampApplication() {
        /*
         * Need registration of each web service because
         * packages("net.leludo.gtrchamp.ws") doesn't work
         */
        register(ChampionshipWebService.class)
                .register(CountryWebService.class).register(DriverWebService.class)
                .register(RaceWebService.class).register(TrackWebService.class)
                .register(Stats.class)
                .register(JacksonFeature.class).register(CORSResponseFilter.class);
    }
}
