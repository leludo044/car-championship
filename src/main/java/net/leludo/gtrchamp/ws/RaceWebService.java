package net.leludo.gtrchamp.ws;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import net.leludo.gtrchamp.ChampionshipException;
import net.leludo.gtrchamp.Competitor;
import net.leludo.gtrchamp.Driver;
import net.leludo.gtrchamp.Race;
import net.leludo.gtrchamp.dao.ChampionshipDao;
import net.leludo.gtrchamp.dao.DriverDao;
import net.leludo.gtrchamp.dao.RaceDao;
import net.leludo.gtrchamp.dao.ResultDao;

/**
 * Race web service. Provides functionality for results management
 */
@Path("/race")
public class RaceWebService {

    @Context
    private ServletContext servletContext;

    private HttpServletResponse servletResponse;

    private EntityManagerFactory emf;
    private ChampionshipDao championshipDao = new ChampionshipDao();
    private DriverDao driverDao = new DriverDao();
    private RaceDao raceDao = new RaceDao();
    private ResultDao resultDao = new ResultDao() ;

    /**
     * Ask for the entity manager registered for the application and inject it
     * in the DAO.
     */
    private void init() {
        emf = (EntityManagerFactory) servletContext
                .getAttribute(EntityManagerFactory.class.getName());
        championshipDao.setEntityManager(emf);
        driverDao.setEntityManager(emf);
        raceDao.setEntityManager(emf);
        resultDao.setEntityManager(emf);
    }

    /**
     * @return the track list in JSON format
     */
    @GET
    @Path("/results/{raceId}/{raceNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Competitor> competitors(@PathParam("raceId") final int id, @PathParam("raceNumber") final int raceNumber) {
        init();
        List<Competitor> competitors = resultDao.find(id, raceNumber);
        resultDao.close();
        return competitors;
    }

    /**
     * Create a new results.
     *
     * @param params
     *            The competitor request parameters needed to create the results
     * @return HTTP 200 if the results has been created, HTTP 406 (not
     *         acceptable) if the starting position or arrival position is
     *         wrong, HTTP 404 (not found) if the driver or the race cannot be
     *         found.
     */
    @POST
    @Path("/results")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response results(final ResultParams params) {
        init();

        Response response;

        try {
            EntityManager em = emf.createEntityManager() ;
            resultDao.setEntityManager(em);
            em.getTransaction().begin();
            Driver driver = checkDriver(params.getDriverId());
            Race race = checkRace(params.getRaceId());
            checkStartingPosition(params.getStartingPosition());
            checkArrivalPosition(params.getArrivalPosition());
            checkRaceNumber(params.getRaceNumber());

            Competitor competitor = new Competitor();
            competitor.setDriver(driver);
            competitor.setRace(race);
            competitor.setRaceNumber(params.getRaceNumber());
            competitor.setStartingPosition(params.getStartingPosition());
            competitor.setArrivalPosition(params.getArrivalPosition());
            resultDao.create(competitor);
            response = Response
                    .ok(new WsReturn(Status.OK.getStatusCode(),
                            "Results for driver " + competitor.getDriver().getName() + " saved !"))
                    .build();
        } catch (final ChampionshipException ce) {
            response = Response.status(ce.status())
                    .entity(new WsReturn(ce.status().getStatusCode(), ce.getMessage())).build();
        }

        championshipDao.close();
        return response;
    }

    private void checkArrivalPosition(final int arrivalPosition) throws ChampionshipException {
        if (arrivalPosition <= 0) {
            throw new ChampionshipException(Status.NOT_ACCEPTABLE,
                    "Incorrect arrival position ! Must be > 0 !");
        }
    }

    private void checkStartingPosition(final int startingPosition) throws ChampionshipException {
        if (startingPosition <= 0) {
            throw new ChampionshipException(Status.NOT_ACCEPTABLE,
                    "Incorrect starting position ! Must be > 0 !");
        }

    }

    private void checkRaceNumber(final int raceNumber) throws ChampionshipException {
        if (raceNumber < 1 || raceNumber > 2) {
            throw new ChampionshipException(Status.NOT_ACCEPTABLE,
                    "Incorrect race number ! Must be 1 or 2 !");
        }

    }

    private Driver checkDriver(Integer id) throws ChampionshipException {
        Driver driver = driverDao.find(id);
        if (driver == null) {
            throw new ChampionshipException(Status.NOT_FOUND, "Driver #" + id + " not found !");
        }
        return driver;
    }

    private Race checkRace(Integer id) throws ChampionshipException {
        Race race = raceDao.find(id);
        if (race == null) {
            throw new ChampionshipException(Status.NOT_FOUND, "Race #" + id + " not found !");
        }
        return race;
    }

    /**
     * Set response headers.
     *
     * @param pServletResponse
     *            Response to send to the user with the headers HTTP needed
     */
    @Context
    public void setHttpServletResponse(final HttpServletResponse pServletResponse) {
        this.servletResponse = pServletResponse;
        this.servletResponse.setHeader("Access-Control-Allow-Origin", "*");
    }
}