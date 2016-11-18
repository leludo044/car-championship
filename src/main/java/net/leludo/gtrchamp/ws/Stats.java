package net.leludo.gtrchamp.ws;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.leludo.gtrchamp.dao.DaoFactory;
import net.leludo.gtrchamp.dao.DataManager;
import net.leludo.gtrchamp.dao.StatDao;
import net.leludo.gtrchamp.stat.Stat;

/**
 * Stat web service. Provide victories, poles and podiums statistics
 */
@Path("stat")
public class Stats {

    /**
     * Return all the drivers with their number of victories.
     *
     * @return all the driver with their number of victories
     */
    @GET
    @Path("/victory")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Stat> victory() {
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        StatDao dao = daoFactory.statDao();
        List<Stat> stats = dao.findNbVictories();
        daoFactory.close();
        return stats;
    }

    /**
     * Return all the drivers with their number of victories for a championship.
     *
     * @param id
     *            The championship id to filter
     * @return all the drivers with their number of victories
     */
    @GET
    @Path("/victory/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Stat> victory(@PathParam("id") final int id) {
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        StatDao dao = daoFactory.statDao();
        List<Stat> stats = dao.findNbVictories(id);
        daoFactory.close();
        return stats;
    }

    /**
     * Return all the drivers with their number of pole positions.
     *
     * @return all the drivers with their number of pole positions
     */
    @GET
    @Path("/pole")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Stat> pole() {
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        StatDao dao = daoFactory.statDao();
        List<Stat> stats = dao.findNbPoles();
        daoFactory.close();
        return stats;
    }

    /**
     * Return all the drivers with their number of pole positions for a championship.
     *
     * @param id
     *            The championship id to filter
     * @return all the drivers with their number of pole positions
     */
    @GET
    @Path("/pole/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Stat> pole(@PathParam("id") final int id) {
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        StatDao dao = daoFactory.statDao();
        List<Stat> stats = dao.findNbPoles(id);
        daoFactory.close();
        return stats;
    }

    /**
     * Return all the drivers with their number of podiums.
     *
     * @return all the drivers with their number of podiums
     */
    @GET
    @Path("/podium")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Stat> podium() {
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        StatDao dao = daoFactory.statDao();
        List<Stat> stats = dao.findNbPodium();
        daoFactory.close();
        return stats;
    }

    /**
     * Return all the drivers with their number of podiums for a championship.
     *
     * @param id
     *            The championship id to filter
     * @return all the drivers with their number of podiums
     */
    @GET
    @Path("/podium/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Stat> podium(@PathParam("id") final int id) {
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        StatDao dao = daoFactory.statDao();
        List<Stat> stats = dao.findNbPodium(id);
        daoFactory.close();
        return stats;
    }

}
