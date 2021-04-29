package net.leludo.gtrchamp.ws;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.leludo.gtrchamp.dao.DaoFactory;
import net.leludo.gtrchamp.dao.DataManager;
import net.leludo.gtrchamp.dao.StatDao;
import net.leludo.gtrchamp.stat.Stat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Stat web service. Provide victories, poles and podiums statistics
 */
@RestController
@RequestMapping("/stat")
public class Stats {

    /**
     * Return all the drivers with their number of victories.
     *
     * @return all the driver with their number of victories
     */
    @ResponseBody
    @GetMapping(path = "/victory", produces = MediaType.APPLICATION_JSON_VALUE)
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
    @ResponseBody
    @GetMapping(path = "/victory/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Stat> victory(@PathVariable("id") final int id) {
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
    @ResponseBody
    @GetMapping(path = "/pole", produces = MediaType.APPLICATION_JSON_VALUE)
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
    @ResponseBody
    @GetMapping(path = "/pole/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Stat> pole(@PathVariable("id") final int id) {
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
    @ResponseBody
    @GetMapping(path = "/podium", produces = MediaType.APPLICATION_JSON_VALUE)
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
    @ResponseBody
    @GetMapping(path = "/podium/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Stat> podium(@PathVariable("id") final int id) {
        DaoFactory daoFactory = DataManager.getInstance().getManager();
        StatDao dao = daoFactory.statDao();
        List<Stat> stats = dao.findNbPodium(id);
        daoFactory.close();
        return stats;
    }

}
