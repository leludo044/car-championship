package net.leludo.gtrchamp.ws;

import java.util.List;

import javax.persistence.EntityManagerFactory;
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

import net.leludo.gtrchamp.Circuit;
import net.leludo.gtrchamp.Country;
import net.leludo.gtrchamp.dao.CircuitDao;
import net.leludo.gtrchamp.dao.CountryDao;

/**
 * Classe de service prenant en charge les opérations d'administration
 * concernant la gestion des circuits.
 * 
 */
@Path("/track")
public class AdminCircuit {

    /** Le contexte d'exécution web */
    @Context
    private ServletContext servletContext;

    /** L'usine JPA */
    private EntityManagerFactory emf;

    /** DAO d'accès au pilote en base */
    private CircuitDao dao = new CircuitDao();

    private CountryDao paysDao = new CountryDao();

    /**
     * Initialisation JPA
     */
    public void init() {
        emf = (EntityManagerFactory) servletContext
                .getAttribute(EntityManagerFactory.class.getName());
        dao.setEntityManager(emf);
        paysDao.setEntityManager(emf);
    }

    /**
     * Retourne la liste des circuits
     * 
     * @return La liste des circuits
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Circuit> tracks() {
        init();
        List<Circuit> circuits = dao.findAll();
        dao.close();
        return circuits;
    }

    /**
     * Retourne un circuit en fonction de son id
     * 
     * @param id
     *            L'id du circuit concerné
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Circuit track(@PathParam("id") final int id) {
        init();
        Circuit circuit = dao.find(id);
        dao.close();
        return circuit;
    }

    /**
     * Ajoute un circuit en base
     *
     * @param params
     *            Les paramètres d'entrée de la requête correspondant à un
     *            circuit
     * @return HTTP 200 si la création du circuit est un succès, HTTP 406 (not
     *         acceptable) si un des paramètre attendu est incorrect.
     */
    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(final CircuitParams params) {
        init();

        String nom = params.getNom();
        String idPays = params.getIdPays();

        Response response;
        String message = validate(params);

        if (StringUtils.isNotEmpty(message)) {
            response = Response.status(Status.NOT_ACCEPTABLE)
                    .entity(new WsReturn(Status.NOT_ACCEPTABLE.getStatusCode(), message)).build();
        } else {
            Country pays = paysDao.find(Integer.valueOf(idPays));
            Circuit circuit = new Circuit(nom, params.getLongueur(), pays);
            dao.create(circuit);
            response = Response
                    .ok(new WsReturn(circuit.getId(), "Circuit " + circuit.getNom() + " ajouté !"))
                    .build();
        }

        dao.close();
        return response;

    }

    /**
     * Valide les données d'entrée d'un circuit
     * 
     * @param params
     *            Les données d'entrée
     * @return Le message d'erreur ou "" si tout est ok
     */
    private String validate(final CircuitParams params) {
        String nom = params.getNom();
        String idPays = params.getIdPays();
        Float longueur = 0f;
        String message = "";

        if (nom == null || nom.equals("")) {
            message = "Le nom du circuit doit être renseigné !";
        } else if (params.getLongueur() == null) {
            message = "La longueur du circuit doit être renseignée !";
        } else if (idPays == null || idPays.equals("")) {
            message = "Le pays doit être renseigné !";
        } else if (paysDao.find(Integer.valueOf(idPays)) == null) {
            message = "Le pays renseigné avec l'id " + idPays + " n'existe pas !";
        } else {
            try {
                longueur = Float.valueOf(params.getLongueur());
            } catch (final NumberFormatException nfe) {
                message = "La longueur du circuit doit être numérique !";
            }
        }

        return message;
    }

    /**
     * Modifie un circuit en base
     *
     * @param params
     *            Les paramètres d'entrée de la requête correspondant à un
     *            circuit
     * @return HTTP 200 si la modification du circuit est un succès, HTTP 404 si
     *         le circuit à modifier est introuvable, HTTP 406 (not acceptable)
     *         si un des paramètre attendu est incorrect.
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(final CircuitParams params) {
        init();

        String nom = params.getNom();
        String idPays = params.getIdPays();

        Response response;
        String message = validate(params);

        if (StringUtils.isNotEmpty(message)) {
            response = Response.status(Status.NOT_ACCEPTABLE)
                    .entity(new WsReturn(Status.NOT_ACCEPTABLE.getStatusCode(), message)).build();
        } else {
            Country pays = paysDao.find(Integer.valueOf(idPays));
            Circuit circuit = dao.find(params.getId().intValue());
            if (circuit != null) {
                circuit.setNom(nom);
                circuit.setLongueur(params.getLongueur());
                circuit.setPays(pays);
                dao.update(circuit);
                response = Response.ok(new WsReturn(Status.OK.getStatusCode(),
                        "Circuit " + circuit.getNom() + " modifié !")).build();
            } else {
                response = Response.status(Status.NOT_FOUND)
                        .entity(new WsReturn(Status.NOT_FOUND.getStatusCode(),
                                "Circuit #" + params.getId() + " introuvable !"))
                        .build();
            }
        }
        dao.close();
        return response;
    }

    /**
     * Supprime un circuit en base
     *
     * @param id
     *            Id du circuit à supprimer
     * @return HTTP 200 si la suppression s'est bien déroulée, HTTP 404 si le
     *         circuit à supprimer est introuvable.
     */
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") final int id) {
        init();

        Response response;

        Circuit circuit = dao.find(id);
        if (circuit != null) {
            dao.delete(circuit);
            response = Response.ok(new WsReturn(Status.OK.getStatusCode(),
                    "Circuit " + circuit.getNom() + " supprimé !")).build();
        } else {
            response = Response.status(Status.NOT_FOUND)
                    .entity(new WsReturn(Status.NOT_FOUND.getStatusCode(),
                            "Circuit #" + id + " introuvable !"))
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
    @Path("/{id}/estcouru")
    @Produces(MediaType.APPLICATION_JSON)
    public Response aCouru(@PathParam("id") final Integer id) {
        init();

        boolean estCouru = dao.estCouru(id);
        return Response.ok().entity(new WsReturn(estCouru ? 1 : 0, "")).build();
    }
}
