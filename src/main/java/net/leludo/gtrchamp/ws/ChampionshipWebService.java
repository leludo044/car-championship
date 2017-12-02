package net.leludo.gtrchamp.ws;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.inject.Singleton;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import net.leludo.gtrchamp.Championship;
import net.leludo.gtrchamp.Competitor;
import net.leludo.gtrchamp.Driver;
import net.leludo.gtrchamp.Race;
import net.leludo.gtrchamp.Track;
import net.leludo.gtrchamp.dao.ChampionshipDao;
import net.leludo.gtrchamp.dao.DaoFactory;
import net.leludo.gtrchamp.dao.DataManager;
import net.leludo.gtrchamp.dao.TrackDao;

/**
 * Championship web service.
 */
@Singleton
@Path("/championship")
public class ChampionshipWebService {

    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(ChampionshipWebService.class);

    /** Servlet context. */
    @Context
    private ServletContext servletContext;

    /** Servlet response. */
    private HttpServletResponse servletResponse;

    /**
     * Constructor.
     */
    public ChampionshipWebService() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Instanciate " + this.getClass());
        }
    }

    /**
     * Return the championships list.
     *
     * @return The championships list to JSON format
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public String championships() {
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        ChampionshipDao championshipDao = daoFactory.championshipDao();
        List<Championship> chps = championshipDao.all();
        daoFactory.close();
        if (chps == null) {
            return "Unknown !";
        } else {
            JsonFactory jsonFactory = new JsonFactory();
            StringWriter sw = new StringWriter();
            try {
                JsonGenerator g = jsonFactory.createGenerator(sw);
                g.writeStartArray();
                for (Championship chp : chps) {
                    g.writeStartObject();
                    g.writeNumberField("id", chp.getId());
                    g.writeStringField("name", chp.getName());
                    g.writeStringField("type", chp.getType());
                    g.writeNumberField("mode", chp.getMode());
                    g.writeEndObject();
                }
                g.writeEndArray();
                g.close();
            } catch (IOException e) {
                LOG.error("Unable to generate response.", e);
            }
            return sw.toString();
        }
    }

    /**
     * Return a championship.
     *
     * @param id
     *            Id of the Championship to return
     * @return A championship representation to JSON format
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String championship(@PathParam("id") final int id) {
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        ChampionshipDao championshipDao = daoFactory.championshipDao();
        Championship chp = championshipDao.find(new Integer(id));
        daoFactory.close();
        if (chp == null) {
            return "Unknown !";
        } else {
            JsonFactory jsonFactory = new JsonFactory();
            StringWriter sw = new StringWriter();
            try {
                JsonGenerator g = jsonFactory.createGenerator(sw);
                g.writeStartObject();
                // g.writeObjectFieldStart("championnat");
                g.writeNumberField("id", chp.getId());
                g.writeStringField("name", chp.getName());
                g.writeStringField("type", chp.getType());
                g.writeNumberField("mode", chp.getMode());
                // g.writeEndObject(); // for field 'name'
                // 9 g.writeStringField("gender", Gender.MALE);
                // 10 g.writeBooleanField("verified", false);
                // 11 g.writeFieldName("userImage"); // no 'writeBinaryField'
                // (yet?)
                // 12 byte[] binaryData = ...;
                // 13 g.writeBinary(binaryData);
                g.writeEndObject();
                g.close();
            } catch (IOException e) {
                LOG.error("Unable to generate response.", e);
            }
            return sw.toString();
        }
    }

    /**
     * Create a new championship.
     *
     * @param params
     *            The championship request parameters needed to create the
     *            championship
     * @return HTTP 200 if the championship has been created, HTTP 406 (not
     *         acceptable) if one of the request parameters is wrong.
     */
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(final ChampionshipParams params) {
        Response response;

        String name = params.getName();
        String type = params.getType();
        int mode = params.getMode();

        if (name == null || name.equals("")) {
            response = Response.status(Status.NOT_ACCEPTABLE)
                    .entity(new WsReturn(Status.NOT_ACCEPTABLE.getStatusCode(),
                            "Championship name is missing !"))
                    .build();
        } else if (type == null || type.equals("")) {
            response = Response.status(Status.NOT_ACCEPTABLE)
                    .entity(new WsReturn(Status.NOT_ACCEPTABLE.getStatusCode(),
                            "Championship type is missing !"))
                    .build();
        } else if (mode != 1 && mode != 2) {
            response = Response.status(Status.NOT_ACCEPTABLE)
                    .entity(new WsReturn(Status.NOT_ACCEPTABLE.getStatusCode(),
                            "Championship mode is wrong !"))
                    .build();
        } else {
            DaoFactory daoFactory = DataManager.getInstance().getManager();
            ChampionshipDao championshipDao = daoFactory.championshipDao();
            Championship championship = new Championship(name, type, mode);
            championshipDao.save(championship);
            response = Response.ok(new WsReturn(championship.getId(),
                    "Championship " + championship.getName() + " created !")).build();
            daoFactory.close();
        }

        return response;
    }

    /**
     * Update an existing championship.
     *
     * @param params
     *            The championship request parameters needed to update the
     *            championship
     * @return HTTP 200 if the championship has been updated, HTTP 404 (not
     *         found) if the championship to update doesn't exists, HTTP 406
     *         (not acceptable) if one of the request parameters is wrong.
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(final ChampionshipParams params) {
        Response response;

        String name = params.getName();
        String type = params.getType();
        int mode = params.getMode();

        if (name == null || name.equals("")) {
            response = Response.status(Status.NOT_ACCEPTABLE)
                    .entity(new WsReturn(Status.NOT_ACCEPTABLE.getStatusCode(),
                            "Championship name is missing !"))
                    .build();
        } else if (type == null || type.equals("")) {
            response = Response.status(Status.NOT_ACCEPTABLE)
                    .entity(new WsReturn(Status.NOT_ACCEPTABLE.getStatusCode(),
                            "Championship type is missing !"))
                    .build();
        } else if (mode != 1 && mode != 2) {
            response = Response.status(Status.NOT_ACCEPTABLE)
                    .entity(new WsReturn(Status.NOT_ACCEPTABLE.getStatusCode(),
                            "Championship mode is wrong !"))
                    .build();
        } else {
            DaoFactory daoFactory = DataManager.getInstance().getManager();
            ChampionshipDao championshipDao = daoFactory.championshipDao();
            Championship championship = championshipDao.find(params.getId().intValue());
            if (championship != null) {
                championship.setName(name);
                championship.setType(type);
                championship.setMode(mode);
                championshipDao.update(championship);
                response = Response.ok(new WsReturn(Status.OK.getStatusCode(),
                        "Championship " + championship.getName() + " updated !")).build();
            } else {
                response = Response.status(Status.NOT_FOUND)
                        .entity(new WsReturn(Status.NOT_FOUND.getStatusCode(),
                                "Driver #" + params.getId() + " not found !"))
                        .build();
            }
            daoFactory.close();
        }
        return response;
    }

    /**
     * Delete an existing championship.
     *
     * @param id
     *            The id of the championship to delete
     * @return HTTP 200 if the championship has been deleted, HTTP 404 (not
     *         found) if the championship to delete doesn't exists
     */

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") final int id) {
        Response response;

        DaoFactory daoFactory = DataManager.getInstance().getManager();
        ChampionshipDao championshipDao = daoFactory.championshipDao();

        Championship championship = championshipDao.find(id);
        if (championship != null) {
            championshipDao.delete(championship);
            response = Response.ok(new WsReturn(Status.OK.getStatusCode(),
                    "Championship " + championship.getName() + " deleted !")).build();
        } else {
            response = Response.status(Status.NOT_FOUND)
                    .entity(new WsReturn(Status.NOT_FOUND.getStatusCode(),
                            "Championship #" + id + " not found !"))
                    .build();
        }
        daoFactory.close();
        return response;
    }

    /**
     * Say if a championship is started (at least one race has been ran).
     *
     * @param id
     *            The id of the championship to ask
     * @return {code:1,message:""} if the championship is started
     *         {code:0,message:""} if the championship is not started
     */
    @GET
    @Path("/{id}/isstarted")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isStarted(@PathParam("id") final Integer id) {
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        ChampionshipDao championshipDao = daoFactory.championshipDao();
        boolean isStarted = championshipDao.isStarted(id);
        daoFactory.close();
        return Response.ok().entity(new WsReturn(isStarted ? 1 : 0, "")).build();
    }

    /**
     * Return the scheduled races for a championship.
     *
     * @param championshipId
     *            The id of the championship to ask
     * @return Return the scheduled races for a championship in JSON format
     */
    @GET
    @Path("/{championshipId}/race/list")
    @Produces(MediaType.APPLICATION_JSON)
    public String races(@PathParam("championshipId") final int championshipId) {
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        ChampionshipDao championshipDao = daoFactory.championshipDao();
        Championship chp = championshipDao.find(new Integer(championshipId));
        daoFactory.close();
        if (chp == null) {
            return "Unknown !";
        } else {
            JsonFactory jsonFactory = new JsonFactory();
            StringWriter sw = new StringWriter();
            try {
                JsonGenerator g = jsonFactory.createGenerator(sw);
                g.writeStartArray();
                for (Race gp : chp.getPlannedRaces()) {
                    g.writeStartObject();
                    g.writeNumberField("id", gp.getId());
                    g.writeStringField("name", gp.getTrack().getName());
                    g.writeNumberField("length", gp.getTrack().getLength());
                    g.writeStringField("date", gp.getDateFr());
                    g.writeStringField("country", gp.getTrack().getCountry().getName());
                    g.writeEndObject();
                }
                g.writeEndArray();
                g.close();
            } catch (IOException e) {
                LOG.error("Unable to generate response.", e);
            }
            servletResponse.setHeader("Access-Control-Allow-Origin", "*");
            return sw.toString();
        }
    }

    /**
     * Return the standings of a championship.
     *
     * @param championshipId
     *            The id of the championship to ask
     * @return The standings of a championship in JSON format
     */
    @GET
    @Path("/{championshipId}/standings")
    @Produces(MediaType.APPLICATION_JSON)
    public String standings(@PathParam("championshipId") final int championshipId) {
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        ChampionshipDao championshipDao = daoFactory.championshipDao();
        List<Object[]> standings = championshipDao.standings(championshipId);
        daoFactory.close();
        if (standings == null) {
            return "Unknown !";
        } else {
            JsonFactory jsonFactory = new JsonFactory();
            StringWriter sw = new StringWriter();
            try {
                JsonGenerator g = jsonFactory.createGenerator(sw);
                g.writeStartArray();
                int rang = 1;
                for (Object[] pilote : standings) {
                    g.writeStartObject();
                    g.writeNumberField("rang", rang++);
                    g.writeStringField("name", ((Driver) pilote[0]).getName());
                    g.writeNumberField("points", (Long) pilote[1]);
                    g.writeEndObject();
                }
                g.writeEndArray();
                g.close();
            } catch (IOException e) {
                LOG.error("Unable to generate response.", e);
            }
            return sw.toString();
        }
    }

    /**
     * Return the scheduled races for a championship.
     *
     * @param id
     *            The id of the championship to ask
     * @return The scheduled races for a championship in JSON format
     */
    @GET
    @Path("/{id}/tracks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") final Integer id) {
        List<Race> races;
        Response response;
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        ChampionshipDao championshipDao = daoFactory.championshipDao();
        Championship championship = championshipDao.find(id);
        if (championship != null) {
            races = championship.getPlannedRaces();
            response = Response.ok(races).build();
        } else {
            response = Response.status(Status.NOT_FOUND)
                    .entity(new WsReturn(Status.NOT_FOUND.getStatusCode(),
                            "Championship #" + id + " not found !"))
                    .build();
        }
        daoFactory.close();

        return response;
    }

    /**
     * Schedule a race for a championship.
     *
     * @param id
     *            The id of the championship for which to schedule the race
     * @param trackId
     *            The id of the track to schedule
     * @param params
     *            The race request parameters needed to schedule it for the
     *            championship
     * @return HTTP 200 if the race has been scheduled, HTTP 404 (not found) if
     *         the championship or the track to schedule doesn't exists
     */
    @PUT
    @Path("/{id}/{trackId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") final Integer id,
            @PathParam("trackId") final Integer trackId, final RaceParams params) {
        Response response;
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        ChampionshipDao championshipDao = daoFactory.championshipDao();
        TrackDao trackDao = daoFactory.trackDao();
        Championship championship = championshipDao.find(id);
        if (championship != null) {
            Track track = trackDao.find(trackId);
            if (track != null) {
                LocalDate date = null;
                if (params.getDate() != null && !params.getDate().equals("")) {
                    date = LocalDate.parse(params.getDate(),
                            DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                }
                championship.planRace(track, date);
                championshipDao.update(championship);
                response = Response
                        .ok(new WsReturn(championship.lastRace().getId(), "Track " + track.getName()
                                + " added to championship " + championship.getName() + "."))
                        .build();
            } else {
                response = Response.status(Status.NOT_FOUND)
                        .entity(new WsReturn(Status.NOT_FOUND.getStatusCode(),
                                "Track #" + trackId + " not found !"))
                        .build();
            }
        } else {
            response = Response.status(Status.NOT_FOUND)
                    .entity(new WsReturn(Status.NOT_FOUND.getStatusCode(),
                            "Championship #" + id + " not found !"))
                    .build();
        }
        daoFactory.close();
        return response;
    }

    /**
     * Cancel a race for a championship.
     *
     * @param id
     *            The id of the championship for which to cancel the race
     * @param trackId
     *            The id of the track to cancel
     * @return HTTP 200 if the race has been canceled, HTTP 404 (not found) if
     *         the championship or the track to cancel doesn't exists
     */
    @DELETE
    @Path("/{id}/{trackId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(@PathParam("id") final Integer id,
            @PathParam("trackId") final Integer trackId) {
        Response response;
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        ChampionshipDao championshipDao = daoFactory.championshipDao();
        Championship championship = championshipDao.find(id);
        if (championship != null) {
            if (trackId != null) {
                Race race = championship.cancelRace(trackId);
                championshipDao.update(championship);
                response = Response.ok(
                        new WsReturn(Status.OK.getStatusCode(), "Track " + race.getTrack().getName()
                                + " deleted from championship " + championship.getName() + "."))
                        .build();
            } else {
                response = Response.status(Status.NOT_FOUND)
                        .entity(new WsReturn(Status.NOT_FOUND.getStatusCode(),
                                "Undefined track to delete !"))
                        .build();
            }
        } else {
            response = Response.status(Status.NOT_FOUND)
                    .entity(new WsReturn(Status.NOT_FOUND.getStatusCode(),
                            "Championship #" + id + " not found !"))
                    .build();
        }
        daoFactory.close();

        return response;
    }

    /**
     * Return the results of a race.
     *
     * @param raceId
     *            The id of the race to ask
     * @return The results of a race in JSON format
     */

    @GET
    @Path("/race/{raceId}/results")
    @Produces(MediaType.APPLICATION_JSON)
    public String results(@PathParam("raceId") final int raceId) {
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        ChampionshipDao championshipDao = daoFactory.championshipDao();
        List<Object[]> results = championshipDao.results(raceId);
        daoFactory.close();
        if (results == null) {
            return "Unknown !";
        } else {
            JsonFactory jsonFactory = new JsonFactory();
            StringWriter sw = new StringWriter();
            try {
                JsonGenerator g = jsonFactory.createGenerator(sw);
                g.writeStartArray();
                for (Object[] concurrent : results) {
                    g.writeStartObject();
                    g.writeNumberField("piloteId",
                            ((Competitor) concurrent[0]).getDriver().getId());
                    g.writeStringField("name", ((Competitor) concurrent[0]).getDriver().getName());
                    g.writeNumberField("startingPosition",
                            ((Competitor) concurrent[0]).getStartingPosition());
                    g.writeNumberField("arrivalPosition",
                            ((Competitor) concurrent[0]).getArrivalPosition());
                    g.writeNumberField("raceNumber", ((Competitor) concurrent[0]).getRaceNumber());
                    g.writeBooleanField("pole",
                            ((Competitor) concurrent[0]).getStartingPosition() == 1);
                    g.writeNumberField("points", (Integer) concurrent[1]);
                    g.writeEndObject();
                }
                g.writeEndArray();
                g.close();
            } catch (IOException e) {
                LOG.error("Unable to generate response.", e);
            }
            return sw.toString();
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
        this.servletResponse = pServletResponse;
        this.servletResponse.setHeader("Access-Control-Allow-Origin", "*");
    }
}
