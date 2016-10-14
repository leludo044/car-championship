package net.leludo.gtrchamp.ws;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
import net.leludo.gtrchamp.dao.DriverDao;
import net.leludo.gtrchamp.dao.RaceDao;
import net.leludo.gtrchamp.dao.ResultDao;

/**
 * Race web service. Provides functionality for results management
 */
@Path("/race")
public class RaceWebService {

    /** Servlet context. */
    @Context
    private ServletContext servletContext;

    /** JPA entity manager. */
    private EntityManagerFactory emf;

    /** DAO for driver access. */
    private DriverDao driverDao = new DriverDao();
    /** DAO for race access. */
    private RaceDao raceDao = new RaceDao();
    /** DAO for result access. */
    private ResultDao resultDao = new ResultDao();

    /**
     * Ask for the entity manager registered for the application and inject it
     * in the DAO.
     */
    private void init() {
        emf = (EntityManagerFactory) servletContext
                .getAttribute(EntityManagerFactory.class.getName());
        driverDao.setEntityManager(emf);
        raceDao.setEntityManager(emf);
        resultDao.setEntityManager(emf);
    }

    /**
     * Provide the track list for a particular race and her race number.
     *
     * @param id
     *            The race id
     * @param raceNumber
     *            The race number
     * @return the track list in JSON format
     */
    @GET
    @Path("/results/{raceId}/{raceNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Competitor> competitors(@PathParam("raceId") final int id,
            @PathParam("raceNumber") final int raceNumber) {
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
    @Path("/result")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addResult(final ResultParams params) {
        init();

        Response response;

        try {
            EntityManager em = emf.createEntityManager();
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
            checkExistingResult(params.getRaceId(), params.getRaceNumber(), competitor);
            checkAgainstOtherCompetitor(params.getRaceId(), params.getRaceNumber(), competitor);

            resultDao.create(competitor);
            response = Response
                    .ok(new WsReturn(Status.OK.getStatusCode(),
                            "Results for driver " + competitor.getDriver().getName() + " saved !"))
                    .build();
        } catch (final ChampionshipException ce) {
            response = Response.status(ce.status())
                    .entity(new WsReturn(ce.status().getStatusCode(), ce.getMessage())).build();
        }

        resultDao.close();
        return response;
    }

    /**
     * Update an existing result.
     *
     * @param params
     *            The competitor request parameters needed to update the results
     * @return HTTP 200 if the results has been updated, HTTP 406 (not
     *         acceptable) if the starting position or arrival position is
     *         wrong, HTTP 404 (not found) if the driver or the race cannot be
     *         found.
     */
    @PUT
    @Path("/result")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateResult(final ResultParams params) {
        init();

        Response response;

        try {
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

            checkAgainstOtherCompetitor(params.getRaceId(), params.getRaceNumber(), competitor);

            resultDao.update(competitor);
            response = Response.ok(new WsReturn(Status.OK.getStatusCode(),
                    "Results for driver " + competitor.getDriver().getName() + " updated !"))
                    .build();
        } catch (final ChampionshipException ce) {
            response = Response.status(ce.status())
                    .entity(new WsReturn(ce.status().getStatusCode(), ce.getMessage())).build();
        }

        resultDao.close();
        return response;
    }

    /**
     * Check for correct arrival position. Must be above 0.
     *
     * @param arrivalPosition
     *            The arrival position to check
     * @throws ChampionshipException
     *             Raised exception if the check fails
     */
    private void checkArrivalPosition(final int arrivalPosition) throws ChampionshipException {
        if (arrivalPosition <= 0) {
            throw new ChampionshipException(Status.NOT_ACCEPTABLE,
                    "Incorrect arrival position ! Must be > 0 !");
        }
    }

    /**
     * Check for correct starting position. Must be above 0.
     *
     * @param startingPosition
     *            The starting position to check
     * @throws ChampionshipException
     *             Raised exception if the check fails
     */
    private void checkStartingPosition(final int startingPosition) throws ChampionshipException {
        if (startingPosition <= 0) {
            throw new ChampionshipException(Status.NOT_ACCEPTABLE,
                    "Incorrect starting position ! Must be > 0 !");
        }

    }

    /**
     * Check for correct race number. Must be 1 or 2.
     *
     * @param raceNumber
     *            The race number to check
     * @throws ChampionshipException
     *             Raised exception if the check fails
     */
    private void checkRaceNumber(final int raceNumber) throws ChampionshipException {
        if (raceNumber < 1 || raceNumber > 2) {
            throw new ChampionshipException(Status.NOT_ACCEPTABLE,
                    "Incorrect race number ! Must be 1 or 2 !");
        }

    }

    /**
     * Check if the driver exists.
     *
     * @param id
     *            The driver id to check
     * @return The founded driver
     * @throws ChampionshipException
     *             Raised exception if the check fails
     */
    private Driver checkDriver(final Integer id) throws ChampionshipException {
        Driver driver = driverDao.find(id);
        if (driver == null) {
            throw new ChampionshipException(Status.NOT_FOUND, "Driver #" + id + " not found !");
        }
        return driver;
    }

    /**
     * Check if the race exists.
     *
     * @param id
     *            The race id to check
     * @return The founded race
     * @throws ChampionshipException
     *             Raised exception if the check fails
     */
    private Race checkRace(final Integer id) throws ChampionshipException {
        Race race = raceDao.find(id);
        if (race == null) {
            throw new ChampionshipException(Status.NOT_FOUND, "Race #" + id + " not found !");
        }
        return race;
    }

    /**
     * Check the result with the other competitors for the same race.
     *
     * @param id
     *            The race id
     * @param raceNumber
     *            The race number
     * @param competitor
     *            The new competitor to check
     * @throws ChampionshipException
     *             Raised exception on error
     */
    private void checkAgainstOtherCompetitor(final Integer id, final int raceNumber,
            final Competitor competitor) throws ChampionshipException {

        List<Competitor> competitors = resultDao.find(id, raceNumber);
        for (Competitor other : competitors) {
            if (other.getDriver().getId() != competitor.getDriver().getId()) {
                if (other.getStartingPosition() == competitor.getStartingPosition()) {
                    throw new ChampionshipException(Status.NOT_ACCEPTABLE,
                            competitor.getDriver().getName()
                                    + " has already the same starting position than "
                                    + other.getDriver().getName());
                }
                if (other.getArrivalPosition() == competitor.getArrivalPosition()) {
                    throw new ChampionshipException(Status.NOT_ACCEPTABLE,
                            competitor.getDriver().getName()
                                    + " has already the same arrival position than "
                                    + other.getDriver().getName());
                }
            }
        }
    }

    /**
     * Check if the competitor has already a result
     *
     * @param id
     *            The race id
     * @param raceNumber
     *            The race number
     * @param competitor
     *            The new competitor to check
     * @throws ChampionshipException
     *             Raised exception on error
     */
    private void checkExistingResult(final Integer id, final int raceNumber,
            final Competitor competitor) throws ChampionshipException {

        List<Competitor> competitors = resultDao.find(id, raceNumber);
        for (Competitor other : competitors) {
            if (other.getDriver().getId() == competitor.getDriver().getId()) {
                throw new ChampionshipException(Status.NOT_ACCEPTABLE,
                        competitor.getDriver().getName() + " has already a result for this race !");
            }
        }
    }

    /**
     * Set response headers.
     *
     * @param pServletResponse
     *            Response to send to the user with the headers HTTP needed
     */
    @Context
    public void setHttpServletResponse(final HttpServletResponse pServletResponse) {
        pServletResponse.setHeader("Access-Control-Allow-Origin", "*");
    }
}
