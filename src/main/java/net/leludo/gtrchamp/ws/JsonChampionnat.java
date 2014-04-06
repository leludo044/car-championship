package net.leludo.gtrchamp.ws;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.leludo.gtrchamp.Championnat;
import net.leludo.gtrchamp.Concurrent;
import net.leludo.gtrchamp.GrandPrix;
import net.leludo.gtrchamp.Pilote;
import net.leludo.gtrchamp.dao.ChampionnatDao;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;

/**
 * Classe de service concernant les championnats. Permet de lister tous les
 * championnats ou de récupérer un championnat en particulier via son ID
 * 
 * - /championnat/list
 * - /championnat/:idChp
 * - /championnat/:idChp/grandprix/list
 * - /championnat/:idChp/classement
 * - /grandprix/:idGrandPrix
 * - /grandprix/:idGrandPrix/resultat/list
 */
@Path("json")
public class JsonChampionnat {

	@Context
	ServletContext servletContext;
	
	private HttpServletResponse servletResponse ;

	EntityManagerFactory emf;
	ChampionnatDao dao = new ChampionnatDao();

	public void init() {
		emf = (EntityManagerFactory) servletContext
				.getAttribute(EntityManagerFactory.class.getName());
		dao.setEntityManager(emf);
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
	public String get(@PathParam("id") int id) {
		init();
		System.out.println(id);
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
	 * @param id L'id du championnat concerné
	 * @return Les données de base du championnat
	 */
	@GET
	@Path("/championnat/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getJson(@PathParam("id") int id) {
		init();
		System.out.println(id);
		Championnat chp = dao.find(new Integer(id));
		dao.close();
		if (chp == null) {
			return "Inconnu !";
		} else {
			JsonFactory jsonFactory = new JsonFactory(); // or, for data
															// binding,
															// org.codehaus.jackson.mapper.MappingJsonFactory
			StringWriter sw = new StringWriter();
			try {
				JsonGenerator g = jsonFactory.createJsonGenerator(sw);
				g.writeStartObject();
				//g.writeObjectFieldStart("championnat");
				g.writeNumberField("id", chp.getId());
				g.writeStringField("libelle", chp.getLibelle());
				//g.writeEndObject(); // for field 'name'
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
	 * @param idChp L'id du championnat concerné
	 * @returnLa liste des grands prix du championnat
	 */
	@GET
	@Path("/championnat/{idChp}/grandprix/list")
	@Produces(MediaType.APPLICATION_JSON)
	public String getJsonGrandsPrix(@PathParam("idChp") int idChp) {
		init();
		System.out.println(idChp);
		Championnat chp = dao.find(new Integer(idChp));
		dao.close();
		if (chp == null) {
			return "Inconnu !";
		} else {
			JsonFactory jsonFactory = new JsonFactory(); // or, for data
															// binding,
															// org.codehaus.jackson.mapper.MappingJsonFactory
			StringWriter sw = new StringWriter();
			try {
				JsonGenerator g = jsonFactory.createJsonGenerator(sw);
				g.writeStartArray();
				//g.writeArrayFieldStart("grandsprix");
				for (GrandPrix gp : chp.getGrandsPrix()) {
					g.writeStartObject();
					g.writeNumberField("id", gp.getId());
					g.writeStringField("nom", gp.getCircuit().getNom());
					g.writeStringField("longueur", gp.getCircuit()
							.getLongueur());
					g.writeStringField("date", gp.getDateFr());
					g.writeStringField("pays", gp.getCircuit().getPays()
							.getNom());
					g.writeEndObject();
				}
				//g.writeEndArray();
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
			JsonFactory jsonFactory = new JsonFactory(); // or, for data
															// binding,
															// org.codehaus.jackson.mapper.MappingJsonFactory
			StringWriter sw = new StringWriter();
			try {
				JsonGenerator g = jsonFactory.createJsonGenerator(sw);
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
	 * @param idGp L'id du grand prix concerné
	 * @return Les résultats du grand prix
	 */
	@GET
	@Path("/grandprix/{idGp}/resultat/list")
	@Produces(MediaType.APPLICATION_JSON)
	public String listJsonResultats(@PathParam("idGp") int idGp) {
		init();
		List<Concurrent> resultats = dao.findResultats(idGp);
		dao.close();
		if (resultats == null) {
			return "Inconnu !";
		} else {
			JsonFactory jsonFactory = new JsonFactory(); // or, for data
															// binding,
															// org.codehaus.jackson.mapper.MappingJsonFactory
			StringWriter sw = new StringWriter();
			try {
				JsonGenerator g = jsonFactory.createJsonGenerator(sw);
				g.writeStartArray();
				for (Concurrent concurrent : resultats) {
					g.writeStartObject();
					g.writeNumberField("idPilote", concurrent.getPilote().getId());
					g.writeStringField("nom", concurrent.getPilote().getNom());
					g.writeNumberField("depart", concurrent.getPositionDepart());
					g.writeNumberField("arrivee", concurrent.getPositionArrivee());
					g.writeNumberField("numCourse", concurrent.getNumeroCourse());
					g.writeBooleanField("pole", concurrent.getPositionDepart()==1);
					g.writeNumberField("points", concurrent.getPoints().getPoints());
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
	 * @param idChp L'id du championnat concerné
	 * @return Le classement général du championnat
	 */
	@GET
	@Path("/championnat/{idChp}/classement")
	@Produces(MediaType.APPLICATION_JSON)
	public String listJsonClassement(@PathParam("idChp") int idChp) {
		init();
		List<Object[]> classement = dao.findClassement(idChp);
		dao.close();
		if (classement == null) {
			return "Inconnu !";
		} else {
			JsonFactory jsonFactory = new JsonFactory(); // or, for data
															// binding,
															// org.codehaus.jackson.mapper.MappingJsonFactory
			StringWriter sw = new StringWriter();
			try {
				JsonGenerator g = jsonFactory.createJsonGenerator(sw);
				g.writeStartArray();
				int rang = 1 ;
				for (Object[] pilote : classement) {
					g.writeStartObject();
					g.writeNumberField("rang", rang++);
					g.writeStringField("nom", ((Pilote)pilote[0]).getNom());
					g.writeNumberField("points", (Long)pilote[1]);
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
	 * Fixe la réponse et y place les headers par défaut
	 * @param pServletResponse
	 */
	@Context
	public void setHttpServletResponse(HttpServletResponse pServletResponse) {
		this.servletResponse = pServletResponse ;
		this.servletResponse.setHeader("Access-Control-Allow-Origin", "*");
	}
}
