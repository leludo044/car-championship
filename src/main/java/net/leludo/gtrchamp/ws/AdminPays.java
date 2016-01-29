package net.leludo.gtrchamp.ws;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import net.leludo.gtrchamp.Pays;
import net.leludo.gtrchamp.dao.PaysDao;

/**
 * Classe de service prenant en charge les opérations d'administration
 * concernant la gestion des pays.
 * 
 */
@Path("/country")
public class AdminPays {

    /** Le contexte d'exécution web */
    @Context
    private ServletContext servletContext;

    /** L'usine JPA */
    private EntityManagerFactory emf;

    /** DAO d'accès aux pays en base */
    private PaysDao dao = new PaysDao();

    /**
     * Initialisation JPA
     */
    public void init() {
        emf = (EntityManagerFactory) servletContext
                .getAttribute(EntityManagerFactory.class.getName());
        dao.setEntityManager(emf);
    }

    /**
     * Retourne la liste des pays
     * 
     * @return La liste des pays
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pays> countries() {
        init();
        List<Pays> pays = dao.findAll();
        dao.close();
        return pays;
    }
}
