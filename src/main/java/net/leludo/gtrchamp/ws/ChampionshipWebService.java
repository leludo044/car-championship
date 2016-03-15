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
import net.leludo.gtrchamp.Track;
import net.leludo.gtrchamp.Concurrent;
import net.leludo.gtrchamp.GrandPrix;
import net.leludo.gtrchamp.Driver;
import net.leludo.gtrchamp.dao.ChampionshipDao;
import net.leludo.gtrchamp.dao.TrackDao;

/**
 * Classe de service concernant les championnats. Permet de lister tous les
 * championnats ou de récupérer un championnat en particulier via son ID
 */
@Path("/championship")
public class ChampionshipWebService {

    @Context
    private ServletContext servletContext;

    private HttpServletResponse servletResponse;

    private EntityManagerFactory emf;
    private ChampionshipDao dao = new ChampionshipDao();
    private TrackDao circuitDao = new TrackDao();

    /**
     * Ask for the entity manager registered for the application and inject it
     * in the DAO.
     */
    private void init() {
        emf = (EntityManagerFactory) servletContext
                .getAttribute(EntityManagerFactory.class.getName());
        dao.setEntityManager(emf);
        circuitDao.setEntityManager(emf);
    }

    /**
     * @return The championships list
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public String listJsonChampionnat() {
        init();
        List<Championship> chps = dao.all();
        dao.close();
        if (chps == null) {
            return "Inconnu !";
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
        Championship chp = dao.find(new Integer(id));
        dao.close();
        if (chp == null) {
            return "Inconnu !";
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
                            "Le libellé du championnat doit être renseigné !"))
                    .build();
        } else if (type == null || type.equals("")) {
            response = Response.status(Status.NOT_ACCEPTABLE)
                    .entity(new WsReturn(Status.NOT_ACCEPTABLE.getStatusCode(),
                            "Le type du championnat doit être renseigné !"))
                    .build();
        } else {
            Championship championship = new Championship(name, type);
            dao.save(championship);
            response = Response.ok(new WsReturn(championship.getId(),
                    "Championnat " + championship.getName() + " ajouté !")).build();
        }

        dao.close();
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
                            "Le libelle du chmpionnat doit être renseigné !"))
                    .build();
        } else if (type == null || type.equals("")) {
            response = Response.status(Status.NOT_ACCEPTABLE)
                    .entity(new WsReturn(Status.NOT_ACCEPTABLE.getStatusCode(),
                            "Le type du championnat doit être renseigné !"))
                    .build();
        } else {
            Championship championship = dao.find(params.getId().intValue());
            if (championship != null) {
                championship.setName(name);
                championship.setType(type);
                dao.update(championship);
                response = Response.ok(new WsReturn(Status.OK.getStatusCode(),
                        "Cahmpionnat " + championship.getName() + " modifié !")).build();
            } else {
                response = Response.status(Status.NOT_FOUND)
                        .entity(new WsReturn(Status.NOT_FOUND.getStatusCode(),
                                "Pilote #" + params.getId() + " introuvable !"))
                        .build();
            }
        }
        dao.close();
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

        Championship championship = dao.find(id);
        if (championship != null) {
            dao.delete(championship);
            response = Response.ok(new WsReturn(Status.OK.getStatusCode(),
                    "Championnat " + championship.getName() + " supprimé !")).build();
        } else {
            response = Response.status(Status.NOT_FOUND)
                    .entity(new WsReturn(Status.NOT_FOUND.getStatusCode(),
                            "Championnat #" + id + " introuvable !"))
                    .build();
        }
        dao.close();
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

        boolean isStarted = dao.isStarted(id);
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
    @Path("/{idChp}/grandprix/list")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJsonGrandsPrix(@PathParam("idChp") final int idChp) {
        init();
        Championship chp = dao.find(new Integer(idChp));
        dao.close();
        if (chp == null) {
            return "Inconnu !";
        } else {
            JsonFactory jsonFactory = new JsonFactory();
            // or, for data
            // binding,
            // org.codehaus.jackson.mapper.MappingJsonFactory
            StringWriter sw = new StringWriter();
            try {
                JsonGenerator g = jsonFactory.createGenerator(sw);
                g.writeStartArray();
                // g.writeArrayFieldStart("grandsprix");
                for (GrandPrix gp : chp.getPlannedRaces()) {
                    g.writeStartObject();
                    g.writeNumberField("id", gp.getId());
                    g.writeStringField("nom", gp.getCircuit().getName());
                    g.writeNumberField("longueur", gp.getCircuit().getLength());
                    g.writeStringField("date", gp.getDateFr());
                    g.writeStringField("pays", gp.getCircuit().getCountry().getName());
                    g.writeEndObject();
                }
                // g.writeEndArray();
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
        List<Object[]> classement = dao.standings(idChp);
        dao.close();
        if (classement == null) {
            return "Inconnu !";
        } else {
            JsonFactory jsonFactory = new JsonFactory();
            // or, for data
            // binding,
            // org.codehaus.jackson.mapper.MappingJsonFactory
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

        List<GrandPrix> races;
        Response response;
        Championship championship = dao.find(id);
        if (championship != null) {
            races = championship.getPlannedRaces();
            response = Response.ok(races).build();
        } else {
            response = Response.status(Status.NOT_FOUND)
                    .entity(new WsReturn(Status.NOT_FOUND.getStatusCode(),
                            "Championnat #" + id + " inexistant !"))
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
        Championship championship = dao.find(id);
        if (championship != null) {
            Track circuit = circuitDao.find(idCircuit);
            if (circuit != null) {
                GrandPrix gp = new GrandPrix(championship, circuit, null);
                championship.planRace(circuit, null);
                dao.update(championship);
                response = Response
                        .ok(new WsReturn(Status.OK.getStatusCode(), "Circuit " + circuit.getName()
                                + " ajouté au championnat " + championship.getName() + "."))
                        .build();
            } else {
                response = Response.status(Status.NOT_FOUND)
                        .entity(new WsReturn(Status.NOT_FOUND.getStatusCode(),
                                "Circuit #" + idCircuit + " inexistant !"))
                        .build();
            }
        } else {
            response = Response.status(Status.NOT_FOUND)
                    .entity(new WsReturn(Status.NOT_FOUND.getStatusCode(),
                            "Championnat #" + id + " inexistant !"))
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
        Championship championship = dao.find(id);
        if (championship != null) {
            if (idCircuit != null) {
                GrandPrix gp = championship.cancelRace(idCircuit);
                dao.update(championship);
                response = Response.ok(new WsReturn(Status.OK.getStatusCode(),
                        "Circuit " + gp.getCircuit().getName() + " supprimé du championnat "
                                + championship.getName() + "."))
                        .build();
            } else {
                response = Response.status(Status.NOT_FOUND)
                        .entity(new WsReturn(Status.NOT_FOUND.getStatusCode(),
                                "Circuit à supprimé indéterminé !"))
                        .build();
            }
        } else {
            response = Response.status(Status.NOT_FOUND)
                    .entity(new WsReturn(Status.NOT_FOUND.getStatusCode(),
                            "Championnat #" + id + " inexistant !"))
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
    @Path("/grandprix/{idGp}/resultat")
    @Produces(MediaType.APPLICATION_JSON)
    public String listJsonResultats(@PathParam("idGp") final int idGp) {
        init();
        List<Object[]> resultats = dao.results(idGp);
        dao.close();
        if (resultats == null) {
            return "Inconnu !";
        } else {
            JsonFactory jsonFactory = new JsonFactory();
            // or, for data
            // binding,
            // org.codehaus.jackson.mapper.MappingJsonFactory
            StringWriter sw = new StringWriter();
            try {
                JsonGenerator g = jsonFactory.createGenerator(sw);
                g.writeStartArray();
                for (Object[] concurrent : resultats) {
                    g.writeStartObject();
                    g.writeNumberField("idPilote",
                            ((Concurrent) concurrent[0]).getPilote().getId());
                    g.writeStringField("nom", ((Concurrent) concurrent[0]).getPilote().getName());
                    g.writeNumberField("depart", ((Concurrent) concurrent[0]).getPositionDepart());
                    g.writeNumberField("arrivee",
                            ((Concurrent) concurrent[0]).getPositionArrivee());
                    g.writeNumberField("numCourse", ((Concurrent) concurrent[0]).getNumeroCourse());
                    g.writeBooleanField("pole",
                            ((Concurrent) concurrent[0]).getPositionDepart() == 1);
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
