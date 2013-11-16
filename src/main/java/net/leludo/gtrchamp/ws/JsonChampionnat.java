package net.leludo.gtrchamp.ws;

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

}
