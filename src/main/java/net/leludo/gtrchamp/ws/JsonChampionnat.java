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

import net.leludo.gtrchamp.Championnat;
import net.leludo.gtrchamp.Circuit;
import net.leludo.gtrchamp.Concurrent;
import net.leludo.gtrchamp.GrandPrix;
import net.leludo.gtrchamp.Pilote;
import net.leludo.gtrchamp.dao.ChampionnatDao;
import net.leludo.gtrchamp.dao.CircuitDao;

/**
 * Classe de service concernant les championnats. Permet de lister tous les
 * championnats ou de récupérer un championnat en particulier via son ID
 * 
 * - /championnat/list - /championnat/:idChp -
 * /championnat/:idChp/grandprix/list - /championnat/:idChp/classement -
 * /grandprix/:idGrandPrix - /grandprix/:idGrandPrix/resultat/list
 */
@Path("json")
public class JsonChampionnat {

    @Context
    private ServletContext servletContext;

    private HttpServletResponse servletResponse;

    private EntityManagerFactory emf;
    private ChampionnatDao dao = new ChampionnatDao();
    private CircuitDao circuitDao = new CircuitDao();

    public void init() {
        emf = (EntityManagerFactory) servletContext
                .getAttribute(EntityManagerFactory.class.getName());
        dao.setEntityManager(emf);
        circuitDao.setEntityManager(emf);
    }

    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_PLAIN)
    public String list() {
        init();
        List<Championnat> chps = dao.findAll();
        dao.close();
        return chps.toString();
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String get(@PathParam("id") final int id) {
        init();
        Championnat chp = dao.find(new Integer(id));
        dao.close();
        if (chp == null) {
            return "Inconnu !";
        } else {
            return chp.toString();
        }
    }

    /**
     * Obtention des données de base d'un championnat
     * 
     * @param id
     *            L'id du championnat concerné
     * @return Les données de base du championnat
     */
    @GET
    @Path("/championnat/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson(@PathParam("id") final int id) {
        init();
        Championnat chp = dao.find(new Integer(id));
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
                g.writeStringField("libelle", chp.getLibelle());
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
     * Obtention de la liste des grands prix prévus pour un championnat
     * 
     * @param idChp
     *            L'id du championnat concerné
     * @return La liste des grands prix du championnat
     */
    @GET
    @Path("/championnat/{idChp}/grandprix/list")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJsonGrandsPrix(@PathParam("idChp") final int idChp) {
        init();
        Championnat chp = dao.find(new Integer(idChp));
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
                for (GrandPrix gp : chp.getGrandsPrix()) {
                    g.writeStartObject();
                    g.writeNumberField("id", gp.getId());
                    g.writeStringField("nom", gp.getCircuit().getNom());
                    g.writeNumberField("longueur", gp.getCircuit().getLongueur());
                    g.writeStringField("date", gp.getDateFr());
                    g.writeStringField("pays", gp.getCircuit().getPays().getNom());
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
     * Obtention de la liste des championnats
     * 
     * @return La liste des championnats
     */
    @GET
    @Path("/championnat/list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listJsonChampionnat() {
        init();
        List<Championnat> chps = dao.findAll();
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
                for (Championnat chp : chps) {
                    g.writeStartObject();
                    g.writeNumberField("id", chp.getId());
                    g.writeStringField("libelle", chp.getLibelle());
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
     * Obtention des résultats d'un grand prix
     * 
     * @param idGp
     *            L'id du grand prix concerné
     * @return Les résultats du grand prix
     */
    @GET
    @Path("/grandprix/{idGp}/resultat/list")
    @Produces(MediaType.APPLICATION_JSON)
    public String listJsonResultats(@PathParam("idGp") final int idGp) {
        init();
        List<Object[]> resultats = dao.findResultats(idGp);
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
                    g.writeStringField("nom", ((Concurrent) concurrent[0]).getPilote().getNom());
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
     * Obtention du classement général d'un championnat
     * 
     * @param idChp
     *            L'id du championnat concerné
     * @return Le classement général du championnat
     */
    @GET
    @Path("/championnat/{idChp}/classement")
    @Produces(MediaType.APPLICATION_JSON)
    public String listJsonClassement(@PathParam("idChp") final int idChp) {
        init();
        List<Object[]> classement = dao.findClassement(idChp);
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
                    g.writeStringField("nom", ((Pilote) pilote[0]).getNom());
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
     * Ajoute un championnat en base
     * 
     * @param params
     *            Les paramètres d'entrée de la requête correspondant à un
     *            championnat
     * @return HTTP 200 si la création du pilote est un succès, HTTP 406 (not
     *         acceptable) si un des paramètre attendu est incorrect.
     */
    @POST
    @Path("/championnat")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(final ChampionnatParams params) {
        init();

        Response response;

        String libelle = params.getLibelle();

        if (libelle == null || libelle.equals("")) {
            response = Response.status(Status.NOT_ACCEPTABLE)
                    .entity(new WsReturn(Status.NOT_ACCEPTABLE.getStatusCode(),
                            "Le libellé du championnat doit être renseigné !"))
                    .build();
        } else {
            Championnat championnat = new Championnat(libelle);
            dao.create(championnat);
            response = Response.ok(new WsReturn(championnat.getId(),
                    "Championnat " + championnat.getLibelle() + " ajouté !")).build();
        }

        dao.close();
        return response;
    }

    /**
     * Modifie un championnat en base
     *
     * @param params
     *            Les paramètres d'entrée de la requête correspondant à un
     *            championnat
     * @return HTTP 200 si la modification du championnat est un succès, HTTP
     *         404 si le championnat à modifier est introuvable, HTTP 406 (not
     *         acceptable) si un des paramètre attendu est incorrect.
     */

    @PUT
    @Path("/championnat")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(final ChampionnatParams params) {
        init();

        Response response;

        String libelle = params.getLibelle();

        if (libelle == null || libelle.equals("")) {
            response = Response.status(Status.NOT_ACCEPTABLE)
                    .entity(new WsReturn(Status.NOT_ACCEPTABLE.getStatusCode(),
                            "Le libelle du chmpionnat doit être renseigné !"))
                    .build();
        } else {
            Championnat championnat = dao.find(params.getId().intValue());
            if (championnat != null) {
                championnat.setLibelle(libelle);
                dao.update(championnat);
                response = Response.ok(new WsReturn(Status.OK.getStatusCode(),
                        "Cahmpionnat " + championnat.getLibelle() + " modifié !")).build();
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
     * Supprime un pilote en base
     *
     * @param id
     *            Id du circuit à supprimer
     * @return HTTP 200 si la suppression s'est bien déroulée, HTTP 404 si le
     *         circuit à supprimer est introuvable.
     */
    @DELETE
    @Path("/championnat/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") final int id) {
        init();

        Response response;

        Championnat championnat = dao.find(id);
        if (championnat != null) {
            dao.delete(championnat);
            response = Response.ok(new WsReturn(Status.OK.getStatusCode(),
                    "Championnat " + championnat.getLibelle() + " supprimé !")).build();
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
     * Indique si un circuit a été couru
     *
     * @param id
     *            L'ID du circuit concerné
     * @return {code:1,message:""} s'il a été couru ou {code:0,message:""} s'il
     *         n'a pas été couru
     */
    @GET
    @Path("/championnat/{id}/estcommence")
    @Produces(MediaType.APPLICATION_JSON)
    public Response estCommence(@PathParam("id") final Integer id) {
        init();

        boolean estCommence = dao.estCommence(id);
        return Response.ok().entity(new WsReturn(estCommence ? 1 : 0, "")).build();
    }

    /**
     * Fixe la réponse et y place les headers par défaut
     * 
     * @param pServletResponse
     */
    @Context
    public void setHttpServletResponse(final HttpServletResponse pServletResponse) {
        this.servletResponse = pServletResponse;
        this.servletResponse.setHeader("Access-Control-Allow-Origin", "*");
    }

    @PUT
    @Path("/championnat/{id}/{idCircuit}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") final Integer id,
            @PathParam("idCircuit") final Integer idCircuit) {
        init();

        Response response;
        Championnat championnat = dao.find(id);
        if (championnat != null) {
            Circuit circuit = circuitDao.find(idCircuit);
            if (circuit != null) {
                GrandPrix gp = new GrandPrix(championnat, circuit, null);
                championnat.organiserGrandPrix(circuit, null);
                dao.update(championnat);
                response = Response
                        .ok(new WsReturn(Status.OK.getStatusCode(), "Circuit " + circuit.getNom()
                                + " ajouté au championnat " + championnat.getLibelle() + "."))
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

    @DELETE
    @Path("/championnat/{id}/{idCircuit}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(@PathParam("id") final Integer id,
            @PathParam("idCircuit") final Integer idCircuit) {
        init();

        Response response;
        Championnat championnat = dao.find(id);
        if (championnat != null) {
            if (idCircuit != null) {
                GrandPrix gp = championnat.supprimerGrandPrix(idCircuit);
                dao.update(championnat);
                response = Response.ok(new WsReturn(Status.OK.getStatusCode(),
                        "Circuit " + gp.getCircuit().getNom() + " supprimé du championnat "
                                + championnat.getLibelle() + "."))
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

    @GET
    @Path("/championnat/{id}/tracks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Integer id) {
        init();

        List<GrandPrix> races;
        Response response;
        Championnat championnat = dao.find(id);
        if (championnat != null) {
            races = championnat.getGrandsPrix();
            response = Response.ok(races).build();
        } else {
            response = Response.status(Status.NOT_FOUND)
                    .entity(new WsReturn(Status.NOT_FOUND.getStatusCode(),
                            "Championnat #" + id + " inexistant !"))
                    .build();
        }

        return response;
    }

}
