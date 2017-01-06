package net.leludo.gtrchamp.ws;

import java.util.List;

import javax.servlet.ServletContext;
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

import org.apache.commons.lang3.StringUtils;

import net.leludo.gtrchamp.Country;
import net.leludo.gtrchamp.Track;
import net.leludo.gtrchamp.dao.CountryDao;
import net.leludo.gtrchamp.dao.DaoFactory;
import net.leludo.gtrchamp.dao.DataManager;
import net.leludo.gtrchamp.dao.TrackDao;

/**
 * Track web service.
 *
 * <ul>
 * <li>GET /track/</li>
 * <li>GET /track/:id</li>
 * <li>POST /track/</li>
 * <li>PUT /track/:id</li>
 * <li>DELETE /track/:id</li>
 * </ul>
 *
 */
@Path("/track")
public class TrackWebService {

    /** Servlet context. */
    @Context
    private ServletContext servletContext;

    /**
     * @return the track list in JSON format
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Track> tracks() {
        TrackDao dao = DataManager.getInstance().getManager().trackDao();
        List<Track> tracks = dao.findAll();
        return tracks;
    }

    /**
     * Return the track from his id.
     *
     * @param id
     *            the id of the track to search
     *
     * @return the track corresponding to the id
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Track track(@PathParam("id") final int id) {
        TrackDao dao = DataManager.getInstance().getManager().trackDao();
        Track track = dao.find(id);
        return track;
    }

    /**
     * Create a new track.
     *
     * @param params
     *            The track request parameters needed to create the track
     * @return HTTP 200 if the track has been created, HTTP 406 (not acceptable)
     *         if one of the request parameters is wrong.
     */
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(final TrackParams params) {
        String name = params.getName();
        String countryId = params.getCountryId();

        Response response;
        String message = validate(params);

        if (StringUtils.isNotEmpty(message)) {
            response = Response.status(Status.NOT_ACCEPTABLE)
                    .entity(new WsReturn(Status.NOT_ACCEPTABLE.getStatusCode(), message)).build();
        } else {
            DaoFactory daoFactory = DataManager.getInstance().getManager();
            TrackDao dao = daoFactory.trackDao();
            CountryDao countryDao = daoFactory.countryDao();
            Country country = countryDao.find(Integer.valueOf(countryId));
            Track track = new Track(name, params.getLength(), country);
            dao.create(track);
            response = Response
                    .ok(new WsReturn(track.getId(), "Track " + track.getName() + " created !"))
                    .build();
            daoFactory.close();
        }

        return response;

    }

    /**
     * Validate the informations of a track.
     *
     * @param params
     *            The track request parameters received
     * @return An error message of a blank string if all is right
     */
    private String validate(final TrackParams params) {
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        CountryDao countryDao = daoFactory.countryDao();

        String name = params.getName();
        String countryId = params.getCountryId();
        String message = "";

        if (name == null || name.equals("")) {
            message = "Track name is missing !";
        } else if (params.getLength() == null) {
            message = "Track length is missing !";
        } else if (countryId == null || countryId.equals("")) {
            message = "Country is missing !";
        } else if (countryDao.find(Integer.valueOf(countryId)) == null) {
            message = "Country #" + countryId + " not found !";
        } else {
            try {
                Float.valueOf(params.getLength());
            } catch (final NumberFormatException nfe) {
                message = "Track length must be a numeric value !";
            }
        }

        daoFactory.close();

        return message;
    }

    /**
     * Update an existing track.
     *
     * @param params
     *            The track request parameters needed to update the track
     * @return HTTP 200 if the track has been updated, HTTP 404 (not found) if
     *         the track to update doesn't exists, HTTP 406 (not acceptable) if
     *         one of the request parameters is wrong.
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(final TrackParams params) {

        String name = params.getName();
        String countryId = params.getCountryId();

        Response response;
        String message = validate(params);

        if (StringUtils.isNotEmpty(message)) {
            response = Response.status(Status.NOT_ACCEPTABLE)
                    .entity(new WsReturn(Status.NOT_ACCEPTABLE.getStatusCode(), message)).build();
        } else {
            DaoFactory daoFactory = DataManager.getInstance().getManager();
            TrackDao dao = daoFactory.trackDao();
            CountryDao countryDao = daoFactory.countryDao();
            Country country = countryDao.find(Integer.valueOf(countryId));
            Track track = dao.find(params.getId().intValue());
            if (track != null) {
                track.setName(name);
                track.setLength(params.getLength());
                track.setCountry(country);
                dao.update(track);
                response = Response.ok(new WsReturn(Status.OK.getStatusCode(),
                        "TRack " + track.getName() + " updated !")).build();
            } else {
                response = Response.status(Status.NOT_FOUND)
                        .entity(new WsReturn(Status.NOT_FOUND.getStatusCode(),
                                "Track #" + params.getId() + " not found !"))
                        .build();
            }
            daoFactory.close();
        }
        return response;
    }

    /**
     * Delete an existing track.
     *
     * @param id
     *            The id of the track to delete
     * @return HTTP 200 if the track has been deleted, HTTP 404 (not found) if
     *         the track to delete doesn't exists
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") final int id) {
        Response response;

        DaoFactory daoFactory = DataManager.getInstance().getManager();
        TrackDao dao = daoFactory.trackDao();

        Track track = dao.find(id);
        if (track != null) {
            dao.delete(track);
            response = Response.ok(new WsReturn(Status.OK.getStatusCode(),
                    "Track " + track.getName() + " deleted !")).build();
        } else {
            response = Response.status(Status.NOT_FOUND).entity(
                    new WsReturn(Status.NOT_FOUND.getStatusCode(), "Track #" + id + " not found !"))
                    .build();
        }
        daoFactory.close();
        return response;
    }

    /**
     * Say if the track has been run.
     *
     * @param id
     *            The id of the track to ask
     * @return {code:1,message:""} if the track has been run as least once
     *         {code:0,message:""} if the track has not been run
     */
    @GET
    @Path("/{id}/wasrun")
    @Produces(MediaType.APPLICATION_JSON)
    public Response wasRun(@PathParam("id") final Integer id) {
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        TrackDao dao = daoFactory.trackDao();
        boolean wasRun = dao.wasRun(id);
        daoFactory.close();
        return Response.ok().entity(new WsReturn(wasRun ? 1 : 0, "")).build();
    }
}
