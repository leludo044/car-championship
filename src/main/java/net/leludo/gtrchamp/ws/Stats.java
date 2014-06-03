package net.leludo.gtrchamp.ws;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.leludo.gtrchamp.dao.StatDao;
import net.leludo.gtrchamp.stat.Stat;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

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
@Path("stat")
public class Stats {

	@Context
	ServletContext servletContext;
	
	private HttpServletResponse servletResponse ;

	EntityManagerFactory emf;
	StatDao dao = new StatDao();

	public void init() {
		emf = (EntityManagerFactory) servletContext
				.getAttribute(EntityManagerFactory.class.getName());
		dao.setEntityManager(emf);
	}

	@GET
	@Path("/victory")
	@Produces(MediaType.APPLICATION_JSON)
	public List list() throws JsonGenerationException, JsonMappingException, IOException {
		init();
		List<Stat> stats = dao.findNbVictories();
		
		//ObjectMapper mapper = new ObjectMapper() ;
		
		dao.close();
		//return mapper.writeValueAsString(stats) ;
		return stats;
	}

}
