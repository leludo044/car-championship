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
 * Stat web service
 */
@Path("stat")
public class Stats {

    @Context
    private ServletContext servletContext;

    private EntityManagerFactory emf;
    private StatDao dao = new StatDao();

    public void init() {
        emf = (EntityManagerFactory) servletContext
                .getAttribute(EntityManagerFactory.class.getName());
        dao.setEntityManager(emf);
    }

    /**
     * Return all the drivers with their number of victories
     * 
     * @return all the driver with their number of victories
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
     * Return all the drivers with their number of pole positions
     * 
     * @return all the drivers with their number of pole positions
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
     * Return all the drivers with their number of podiums
     * 
     * @return all the drivers with their number of podiums
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
