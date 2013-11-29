package net.leludo.gtrchamp.ws;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.leludo.gtrchamp.Championnat;
import net.leludo.gtrchamp.dao.ChampionnatDao;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;

/**
 * Classe de service cocnernant les championnats. Permet de lister tous les
 * championnats ou de récupérer un championnat en particulier via son ID
 */
@Path("championnat")
public class JsonChampionnat {

	@Context
	ServletContext servletContext;

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

	@GET
	@Path("/getjson/{id}")
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
			StringWriter sw = new StringWriter() ;
			try{
				JsonGenerator g = jsonFactory.createJsonGenerator(sw);
				g.writeStartObject();
				g.writeObjectFieldStart("championnat");
				g.writeNumberField("id", chp.getId());
				g.writeStringField("libelle", chp.getLibelle());
				g.writeEndObject(); // for field 'name'
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

}
