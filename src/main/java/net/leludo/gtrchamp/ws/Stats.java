package net.leludo.gtrchamp.ws;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.leludo.gtrchamp.dao.StatDao;
import net.leludo.gtrchamp.stat.Stat;

/**
 * Classe de service prenant en charge la génération des données statistiques.
 * 
 * - /stat/victory
 */
@Path("stat")
public class Stats {

	@Context
	private ServletContext servletContext;

	private EntityManagerFactory emf;
	private StatDao dao = new StatDao();

	/**
	 * Initialisation. Récupère le contexte JPA et l'injecte dans le DAO.
	 */
	public void init() {
		emf = (EntityManagerFactory) servletContext.getAttribute(EntityManagerFactory.class.getName());
		dao.setEntityManager(emf);
	}

	/**
	 * Retourne les pilotes et leur nombre de victoires en grand prix
	 * 
	 * @return Les pilotes et leur nombre de victoires en grand prix
	 */
	@GET
	@Path("/victory")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Stat> victory() {
		init();
		List<Stat> stats = dao.findNbVictories();
		dao.close();
		return stats;
	}

	/**
	 * Retourne les pilotes et leur nombre de poles position en grand prix
	 * 
	 * @return Les pilotes et leur nombre de poles position en grand prix
	 */
	@GET
	@Path("/pole")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Stat> pole() {
		init();
		List<Stat> stats = dao.findNbPoles();
		dao.close();
		return stats;
	}

	/**
	 * Retourne les pilotes et leur nombre de poles position en grand prix
	 * 
	 * @return Les pilotes et leur nombre de poles position en grand prix
	 */
	@GET
	@Path("/podium")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Stat> podium() {
		init();
		List<Stat> stats = dao.findNbPodium();
		dao.close();
		return stats;
	}

}
