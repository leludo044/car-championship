package net.leludo.gtrchamp.ws;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import net.leludo.gtrchamp.CompetitorId;
import net.leludo.gtrchamp.Driver;
import net.leludo.gtrchamp.Race;
import net.leludo.gtrchamp.dao.DaoFactory;
import net.leludo.gtrchamp.dao.DataManager;
import net.leludo.gtrchamp.dao.DriverDao;
import net.leludo.gtrchamp.dao.RaceDao;
import net.leludo.gtrchamp.dao.ResultDao;
import net.leludo.gtrchamp.dao.ScoringDao;

/**
 * Race web service. Provides functionality for results management
 *
 * <ul>
 * <li>GET /race/results/:raceId/:raceNumber</li>
 * <li>POST /result</li>
 * <li>PUT /result</li>
 * <li>DELETE /result/:raceId/:driverId/:raceNumber</li>
 * </ul>
 */
@Path("/race")
public class RaceWebService {

    /** Servlet context. */
    @Context
    private ServletContext servletContext;

    /**
     * Provide the track list for a particular race and his race number.
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
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        ResultDao resultDao = daoFactory.resultDao();
        List<Competitor> competitors = resultDao.find(id, raceNumber);
        daoFactory.close();
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
        Response response;

        try {

            Driver driver = checkDriver(params.getDriverId());
            Race race = checkRace(params.getRaceId());
            checkStartingPosition(params.getStartingPosition());
            checkArrivalPosition(params);
            checkRaceNumber(params.getRaceNumber());

            Competitor competitor = new Competitor();
            competitor.setDriver(driver);
            competitor.setRace(race);
            competitor.setRaceNumber(params.getRaceNumber());
            competitor.setStartingPosition(params.getStartingPosition());
            competitor.setArrivalPosition(params.getArrivalPosition());
            checkExistingResult(params.getRaceId(), params.getRaceNumber(), competitor);
            checkAgainstOtherCompetitor(params.getRaceId(), params.getRaceNumber(), competitor);

            DaoFactory daoFactory = DataManager.getInstance().getManager();
            ResultDao resultDao = daoFactory.resultDao();
            resultDao.create(competitor);
            response = Response
                    .ok(new WsReturn(Status.OK.getStatusCode(),
                            "Results for driver " + competitor.getDriver().getName() + " saved !"))
                    .build();
            daoFactory.close();
        } catch (final ChampionshipException ce) {
            response = Response.status(ce.status())
                    .entity(new WsReturn(ce.status().getStatusCode(), ce.getMessage())).build();
        }

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
        Response response;

        try {
            Driver driver = checkDriver(params.getDriverId());
            Race race = checkRace(params.getRaceId());
            checkStartingPosition(params.getStartingPosition());
            checkArrivalPosition(params);
            checkRaceNumber(params.getRaceNumber());

            Competitor competitor = new Competitor();
            competitor.setDriver(driver);
            competitor.setRace(race);
            competitor.setRaceNumber(params.getRaceNumber());
            competitor.setStartingPosition(params.getStartingPosition());
            competitor.setArrivalPosition(params.getArrivalPosition());

            checkAgainstOtherCompetitor(params.getRaceId(), params.getRaceNumber(), competitor);

            DaoFactory daoFactory = DataManager.getInstance().getManager();
            ResultDao resultDao = daoFactory.resultDao();
            Competitor competitorToUpdate = resultDao
                    .find(new CompetitorId(competitor.getDriver().getId(),
                            competitor.getRace().getId(), competitor.getRaceNumber()));
            competitorToUpdate.setStartingPosition(competitor.getStartingPosition());
            competitorToUpdate.setArrivalPosition(competitor.getArrivalPosition());
            resultDao.update(competitorToUpdate);
            response = Response.ok(new WsReturn(Status.OK.getStatusCode(),
                    "Results for driver " + competitor.getDriver().getName() + " updated !"))
                    .build();
            daoFactory.close();
        } catch (final ChampionshipException ce) {
            response = Response.status(ce.status())
                    .entity(new WsReturn(ce.status().getStatusCode(), ce.getMessage())).build();
        }

        return response;
    }

    /**
     * Update an existing result.
     *
     * @param raceId
     *            The race id of the result to delete
     * @param driverId
     *            The driver id of the result to delete
     * @param raceNumber
     *            The race number of the result to delete
     * @return HTTP 200 if the results has been updated, HTTP 406 (not
     *         acceptable) if the starting position or arrival position is
     *         wrong, HTTP 404 (not found) if the driver or the race cannot be
     *         found.
     */
    @DELETE
    @Path("/result/{raceId}/{driverId}/{raceNumber}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeResult(@PathParam("raceId") final int raceId,
            @PathParam("driverId") final int driverId,
            @PathParam("raceNumber") final int raceNumber) {
        Response response;

        DaoFactory daoFactory = DataManager.getInstance().getManager();
        ResultDao resultDao = daoFactory.resultDao();

        int rowCount = resultDao.delete(raceId, driverId, raceNumber);
        if (rowCount == 1) {
            response = Response.ok(new WsReturn(Status.OK.getStatusCode(), "Result removed !"))
                    .build();
        } else if (rowCount == 0) {
            response = Response.status(Status.BAD_REQUEST).entity(
                    new WsReturn(Status.BAD_REQUEST.getStatusCode(), "No result to remove !"))
                    .build();
        } else {
            response = Response.status(Status.BAD_REQUEST).entity(
                    new WsReturn(Status.BAD_REQUEST.getStatusCode(), "To many results removed  !"))
                    .build();
        }
        daoFactory.close();
        return response;
    }

    /**
     * Check for correct arrival position. Must be above 0 and under the max
     * value corresponding to the scoring system.
     *
     * @param params
     *            The competitor request parameters received by the request
     * @throws ChampionshipException
     *             Raised exception if the check fails
     */
    private void checkArrivalPosition(final ResultParams params) throws ChampionshipException {
        if (params.getArrivalPosition() <= 0) {
            throw new ChampionshipException(Status.NOT_ACCEPTABLE,
                    "Incorrect arrival position ! Must be > 0 !");
        } else {
            DaoFactory daoFactory = DataManager.getInstance().getManager();
            ScoringDao scoringDao = daoFactory.scoringDao();
            Integer maxArrivalPosition = scoringDao.max(params.getScoringSystem());
            daoFactory.close();
            if (params.getArrivalPosition() > maxArrivalPosition) {
                throw new ChampionshipException(Status.NOT_ACCEPTABLE, String.format(
                        "Incorrect arrival position ! Must be <= %s !", maxArrivalPosition));
            }
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
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        DriverDao driverDao = daoFactory.driverDao();
        Driver driver = driverDao.find(id);
        if (driver == null) {
            throw new ChampionshipException(Status.NOT_FOUND, "Driver #" + id + " not found !");
        }
        daoFactory.close();
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
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        RaceDao raceDao = daoFactory.raceDao();
        Race race = raceDao.find(id);
        if (race == null) {
            throw new ChampionshipException(Status.NOT_FOUND, "Race #" + id + " not found !");
        }
        daoFactory.close();
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

        DaoFactory daoFactory = DataManager.getInstance().getManager();
        ResultDao resultDao = daoFactory.resultDao();
        List<Competitor> competitors = resultDao.find(id, raceNumber);
        daoFactory.close();
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
     * Check if the competitor has already a result.
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

        DaoFactory daoFactory = DataManager.getInstance().getManager();
        ResultDao resultDao = daoFactory.resultDao();
        List<Competitor> competitors = resultDao.find(id, raceNumber);
        daoFactory.close();
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
