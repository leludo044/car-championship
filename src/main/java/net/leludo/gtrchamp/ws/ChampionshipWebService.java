package net.leludo.gtrchamp.ws;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.persistence.EntityManagerFactory;
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

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

import net.leludo.gtrchamp.Championship;
import net.leludo.gtrchamp.Competitor;
import net.leludo.gtrchamp.Driver;
import net.leludo.gtrchamp.Race;
import net.leludo.gtrchamp.Track;
import net.leludo.gtrchamp.dao.ChampionshipDao;
import net.leludo.gtrchamp.dao.TrackDao;

/**
 * Championship web service
 */
@Path("/championship")
public class ChampionshipWebService {

    @Context
    private ServletContext servletContext;

    private HttpServletResponse servletResponse;

    private EntityManagerFactory emf;
    private ChampionshipDao championshipDao = new ChampionshipDao();
    private TrackDao trackDao = new TrackDao();

    /**
     * Ask for the entity manager registered for the application and inject it
     * in the DAO.
     */
    private void init() {
        emf = (EntityManagerFactory) servletContext
                .getAttribute(EntityManagerFactory.class.getName());
        championshipDao.setEntityManager(emf);
        trackDao.setEntityManager(emf);
    }

    /**
     * @return The championships list
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public String listJsonChampionnat() {
        init();
        List<Championship> chps = championshipDao.all();
        championshipDao.close();
        if (chps == null) {
            return "Unknown !";
        } else {
            JsonFactory jsonFactory = new JsonFactory();
            // or, for data
            // binding,
            // org.codehaus.jackson.mapper.MappingJsonFactory
            StringWriter sw = new StringWriter();
            try {
                JsonGenerator g = jsonFactory.createGenerator(sw);
                g.writeStartArray();
                for (Championship chp : chps) {
                    g.writeStartObject();
                    g.writeNumberField("id", chp.getId());
                    g.writeStringField("name", chp.getName());
                    g.writeStringField("type", chp.getType());
                    g.writeEndObject();
                }
                g.writeEndArray();
                g.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return sw.toString();
        }
    }

    /**
     * Return a championship.
     *
     * @param id
     *            Championship id to return
     * @return A championship representation to JSON format
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("id") final int id) {
        init();
        Championship chp = championshipDao.find(new Integer(id));
        championshipDao.close();
        if (chp == null) {
            return "Unknown !";
        } else {
            JsonFactory jsonFactory = new JsonFactory();
            // or, for data
            // binding,
            // org.codehaus.jackson.mapper.MappingJsonFactory
            StringWriter sw = new StringWriter();
            try {
                JsonGenerator g = jsonFactory.createGenerator(sw);
                g.writeStartObject();
                // g.writeObjectFieldStart("championnat");
                g.writeNumberField("id", chp.getId());
                g.writeStringField("name", chp.getName());
                g.writeStringField("type", chp.getType());
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
                // TODO Auto-generated catch block
                e.printStackTrace();
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
        init();

        Response response;

        String name = params.getName();
        String type = params.getType();

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
        } else {
            Championship championship = new Championship(name, type);
            championshipDao.save(championship);
            response = Response.ok(new WsReturn(championship.getId(),
                    "Championship " + championship.getName() + " created !")).build();
        }

        championshipDao.close();
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
        init();

        Response response;

        String name = params.getName();
        String type = params.getType();

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
        } else {
            Championship championship = championshipDao.find(params.getId().intValue());
            if (championship != null) {
                championship.setName(name);
                championship.setType(type);
                championshipDao.update(championship);
                response = Response.ok(new WsReturn(Status.OK.getStatusCode(),
                        "Championship " + championship.getName() + " updated !")).build();
            } else {
                response = Response.status(Status.NOT_FOUND)
                        .entity(new WsReturn(Status.NOT_FOUND.getStatusCode(),
                                "Driver #" + params.getId() + " not found !"))
                        .build();
            }
        }
        championshipDao.close();
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
        init();

        Response response;

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
        championshipDao.close();
        return response;
    }

    /**
     * Say if a championship is started (at least one race has ran).
     *
     * @param id
     *            The id of the championship to ask
     * @return {code:1,message:""} if the championship is started
     *         {code:0,message:""} if the championship is not started
     */
    @GET
    @Path("/{id}/estcommence")
    @Produces(MediaType.APPLICATION_JSON)
    public Response estCommence(@PathParam("id") final Integer id) {
        init();

        boolean isStarted = championshipDao.isStarted(id);
        return Response.ok().entity(new WsReturn(isStarted ? 1 : 0, "")).build();
    }

    /**
     * Return the scheduled races for a championship.
     *
     * @param idChp
     *            The id of the championship to ask
     * @return Return the scheduled races for a championship in JSON format
     */
    @GET
    @Path("/{idChp}/race/list")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJsonGrandsPrix(@PathParam("idChp") final int idChp) {
        init();
        Championship chp = championshipDao.find(new Integer(idChp));
        championshipDao.close();
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
                    g.writeStringField("nom", gp.getTrack().getName());
                    g.writeNumberField("longueur", gp.getTrack().getLength());
                    g.writeStringField("date", gp.getDateFr());
                    g.writeStringField("pays", gp.getTrack().getCountry().getName());
                    g.writeEndObject();
                }
                g.writeEndArray();
                g.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            servletResponse.setHeader("Access-Control-Allow-Origin", "*");
            return sw.toString();
        }
    }

    /**
     * Return the standings of a championship.
     *
     * @param idChp
     *            The id of the championship to ask
     * @return The standings of a championship in JSON format
     */
    @GET
    @Path("/{idChp}/classement")
    @Produces(MediaType.APPLICATION_JSON)
    public String listJsonClassement(@PathParam("idChp") final int idChp) {
        init();
        List<Object[]> classement = championshipDao.standings(idChp);
        championshipDao.close();
        if (classement == null) {
            return "Unknown !";
        } else {
            JsonFactory jsonFactory = new JsonFactory();
            StringWriter sw = new StringWriter();
            try {
                JsonGenerator g = jsonFactory.createGenerator(sw);
                g.writeStartArray();
                int rang = 1;
                for (Object[] pilote : classement) {
                    g.writeStartObject();
                    g.writeNumberField("rang", rang++);
                    g.writeStringField("nom", ((Driver) pilote[0]).getName());
                    g.writeNumberField("points", (Long) pilote[1]);
                    g.writeEndObject();
                }
                g.writeEndArray();
                g.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
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
        init();

        List<Race> races;
        Response response;
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

        return response;
    }

    /**
     * Schedule a race for a championship.
     *
     * @param id
     *            The id of the championship for which to schedule the race
     * @param idCircuit
     *            The id of the track to schedule
     * @return HTTP 200 if the race has been scheduled, HTTP 404 (not found) if
     *         the championship or the track to schedule doesn't exists
     */
    @PUT
    @Path("/{id}/{idCircuit}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") final Integer id,
            @PathParam("idCircuit") final Integer idCircuit) {
        init();

        Response response;
        Championship championship = championshipDao.find(id);
        if (championship != null) {
            Track circuit = trackDao.find(idCircuit);
            if (circuit != null) {
                Race gp = new Race(championship, circuit, null);
                championship.planRace(circuit, null);
                championshipDao.update(championship);
                response = Response
                        .ok(new WsReturn(Status.OK.getStatusCode(), "Track " + circuit.getName()
                                + " added to championship " + championship.getName() + "."))
                        .build();
            } else {
                response = Response.status(Status.NOT_FOUND)
                        .entity(new WsReturn(Status.NOT_FOUND.getStatusCode(),
                                "Track #" + idCircuit + " not found !"))
                        .build();
            }
        } else {
            response = Response.status(Status.NOT_FOUND)
                    .entity(new WsReturn(Status.NOT_FOUND.getStatusCode(),
                            "Championship #" + id + " not found !"))
                    .build();
        }

        return response;
    }

    /**
     * Cancel a race for a championship.
     *
     * @param id
     *            The id of the championship for which to cancel the race
     * @param idCircuit
     *            The id of the track to cancel
     * @return HTTP 200 if the race has been canceled, HTTP 404 (not found) if
     *         the championship or the track to cancel doesn't exists
     */
    @DELETE
    @Path("/{id}/{idCircuit}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(@PathParam("id") final Integer id,
            @PathParam("idCircuit") final Integer idCircuit) {
        init();

        Response response;
        Championship championship = championshipDao.find(id);
        if (championship != null) {
            if (idCircuit != null) {
                Race gp = championship.cancelRace(idCircuit);
                championshipDao.update(championship);
                response = Response.ok(new WsReturn(Status.OK.getStatusCode(),
                        "Track " + gp.getTrack().getName() + " deleted from championship "
                                + championship.getName() + "."))
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

        return response;
    }

    /**
     * Return the results of a race.
     *
     * @param idGp
     *            The id of the race to ask
     * @return The results of a race in JSON format
     */

    @GET
    @Path("/race/{idGp}/resultat")
    @Produces(MediaType.APPLICATION_JSON)
    public String listJsonResultats(@PathParam("idGp") final int idGp) {
        init();
        List<Object[]> resultats = championshipDao.results(idGp);
        championshipDao.close();
        if (resultats == null) {
            return "Unknown !";
        } else {
            JsonFactory jsonFactory = new JsonFactory();
            StringWriter sw = new StringWriter();
            try {
                JsonGenerator g = jsonFactory.createGenerator(sw);
                g.writeStartArray();
                for (Object[] concurrent : resultats) {
                    g.writeStartObject();
                    g.writeNumberField("idPilote",
                            ((Competitor) concurrent[0]).getDriver().getId());
                    g.writeStringField("nom", ((Competitor) concurrent[0]).getDriver().getName());
                    g.writeNumberField("depart", ((Competitor) concurrent[0]).getSartingPosition());
                    g.writeNumberField("arrivee",
                            ((Competitor) concurrent[0]).getArrivalPosition());
                    g.writeNumberField("numCourse", ((Competitor) concurrent[0]).getRaceNumber());
                    g.writeBooleanField("pole",
                            ((Competitor) concurrent[0]).getSartingPosition() == 1);
                    g.writeNumberField("points", (Integer) concurrent[1]);
                    g.writeEndObject();
                }
                g.writeEndArray();
                g.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
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
