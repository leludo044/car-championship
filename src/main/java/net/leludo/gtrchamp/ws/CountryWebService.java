package net.leludo.gtrchamp.ws;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.leludo.gtrchamp.Country;
import net.leludo.gtrchamp.dao.CountryDao;

/**
 * Web service for all country requests.
 */
@Path("/country")
public class CountryWebService {

    @Context
    private ServletContext servletContext;

    private EntityManagerFactory emf;

    private CountryDao dao = new CountryDao();

    /**
     * Ask for the entity manager registered for the application and inject it
     * in the DAO.
     */
    public void init() {
        emf = (EntityManagerFactory) servletContext
                .getAttribute(EntityManagerFactory.class.getName());
        dao.setEntityManager(emf);
    }

    /**
     * @return The countries list
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Country> countries() {
        init();
        List<Country> country = dao.all();
        dao.close();
        return country;
    }
}
