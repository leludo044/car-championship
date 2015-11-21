package net.leludo.gtrchamp.ws;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

import net.leludo.gtrchamp.Pilote;
import net.leludo.gtrchamp.dao.PiloteDao;

/**
 * Classe de service prenant en charge les opérations d'administration
 * concernant la gestion des pilotes.
 * 
 */
@Path("admin/pilote")
public class Administration {

	/** Le contexte d'exécution web */
	@Context
	private ServletContext servletContext;

	/** L'usine JPA */
	private EntityManagerFactory emf;

	/** DAO d'accès au pilote en base */
	private PiloteDao dao = new PiloteDao();

	/**
	 * Initialisation JPA
	 */
	public void init() {
		emf = (EntityManagerFactory) servletContext.getAttribute(EntityManagerFactory.class.getName());
		dao.setEntityManager(emf);
	}

	/**
	 * Retourne la liste des pilotes
	 * 
	 * @return La liste des pilotes
	 */
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Pilote> pilote() {
		init();
		List<Pilote> pilotes = dao.findAll();
		dao.close();
		return pilotes;
	}

	/**
	 * Ajoute un pilote en base
	 * 
	 * @param prenom
	 *            Le prénom du pilote à ajouter. Ne doit pas être null.
	 * @param dateNaissance
	 *            La date de naissance du pilote à ajouter. Ne doit pas être
	 *            null.
	 * @return HTTP 200 si la création du pilote est un succès, HTTP 406 (not
	 *         acceptable) si un des paramètre attendu est incorrect.
	 */
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(PiloteParams params) {
		init();

		Response response;

		String prenom = params.getNom();
		String dateNaissance = params.getDateNaissance();

		if (prenom == null || prenom.equals("")) {
			response = Response.status(Status.NOT_ACCEPTABLE).entity(
					new WsReturn(Status.NOT_ACCEPTABLE.getStatusCode(), "Le prénom du pilote doit être renseigné !"))
					.build();
		} else if (dateNaissance == null) {
			response = Response.status(Status.NOT_ACCEPTABLE).entity(new WsReturn(Status.NOT_ACCEPTABLE.getStatusCode(),
					"La date de naissance du pilote doit être renseignée !")).build();
		} else {
			LocalDate date = LocalDate.parse(dateNaissance, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			Pilote pilote = new Pilote(prenom, date);
			dao.create(pilote);
			response = Response.ok(new WsReturn(pilote.getId(), "Pilote " + pilote.getNom() + " ajouté !")).build();
		}

		dao.close();
		return response;
	}

	/**
	 * Modifie un pilote en base
	 * 
	 * @param prenom
	 *            Le prénom du pilote à modifier. Ne doit pas être null.
	 * @param dateNaissance
	 *            La date de naissance du pilote à modifier. Ne doit pas être
	 *            null.
	 * @return HTTP 200 si la modification du pilote est un succès, HTTP 404 si
	 *         le pilote à modifier est introuvable, HTTP 406 (not acceptable)
	 *         si un des paramètre attendu est incorrect.
	 */
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(PiloteParams params) {
		init();

		Response response;

		String prenom = params.getNom();
		String dateNaissance = params.getDateNaissance();

		if (prenom == null || prenom.equals("")) {
			response = Response.status(Status.NOT_ACCEPTABLE).entity(
					new WsReturn(Status.NOT_ACCEPTABLE.getStatusCode(), "Le prénom du pilote doit être renseigné !"))
					.build();
		} else if (dateNaissance == null) {
			response = Response.status(Status.NOT_ACCEPTABLE).entity(new WsReturn(Status.NOT_ACCEPTABLE.getStatusCode(),
					"La date de naissance du pilote doit être renseignée !")).build();
		} else {
			LocalDate date = LocalDate.parse(dateNaissance, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			Pilote pilote = dao.find(params.getId().intValue());
			if (pilote != null) {
				pilote.setPrenom(prenom);
				pilote.setDateNaissance(date);
				dao.update(pilote);
				response = Response
						.ok(new WsReturn(Status.OK.getStatusCode(), "Pilote " + pilote.getNom() + " modifié !"))
						.build();
			} else {
				response = Response.status(Status.NOT_FOUND).entity(
						new WsReturn(Status.NOT_FOUND.getStatusCode(), "Pilote #" + params.getId() + " introuvable !"))
						.build();
			}
		}
		dao.close();
		return response;
	}

	/**
	 * Ajoute un pilote en base
	 * 
	 * @param prenom
	 *            Le prénom du pilote à ajouter
	 * @param dateNaissanceLa
	 *            date de naissance du pilote à ajotter
	 * @return HTTP 200 si la suppression s'est bien déroulée, HTTP 404 si le
	 *         pilote à supprimer est introuvable.
	 */
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") int id) {
		init();

		Response response;

		Pilote pilote = dao.find(id);
		if (pilote != null) {
			dao.delete(pilote);
			response = Response.ok(new WsReturn(Status.OK.getStatusCode(), "Pilote " + pilote.getNom() + " supprimé !"))
					.build();
		} else {
			response = Response.status(Status.NOT_FOUND)
					.entity(new WsReturn(Status.NOT_FOUND.getStatusCode(), "Pilote #" + id + " introuvable !")).build();
		}
		dao.close();
		return response;
	}

	/**
	 * Indique si un pilote à déjà couru au moins un grnd prix
	 * 
	 * @param id
	 *            L'ID du pilote concerné
	 * @return {code:1,message:""} s'il a couru ou {code:0,message:""} s'il n'
	 *         pas couru
	 */
	@GET
	@Path("/{id}/acouru")
	@Produces(MediaType.APPLICATION_JSON)
	public Response aCouru(@PathParam("id") Integer id) {
		init();

		boolean aCouru = dao.aCouru(id);
		return Response.ok().entity(new WsReturn(aCouru ? 1 : 0, "")).build();
	}
}
