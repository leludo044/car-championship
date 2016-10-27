package net.leludo.gtrchamp.dao;

import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;

/**
 * Hig level class for DAO management.
 *
 */
@Singleton
public class DataAccess {

    /** The instance of the manager. */
    private static DataAccess instance;

    /** The JPA entity manager factory to use to create the entity managers for the DAO factory. */
    private EntityManagerFactory emf;

    /**
     * Constructor. Private to avoid instantiation
     */
    private DataAccess() {
        emf = null;
    }

    /**
     * Return the unique instance of the manager.
     * @return the unique instance of the manager
     */
    public static final DataAccess getInstance() {
        if (instance == null) {
            instance = new DataAccess();
        }
        return instance;
    }

    /**
     * Return a DAO factory linked with an newly created JPA entity manager.
     * @return the DAO factory
     */
    public DaoManager getManager() {
        return new DaoManager(emf.createEntityManager());
    }

    /**
     * Set the JPA entity manager to use.
     *
     * @param entityManagerFactory
     *            The entity manager to use
     */
    public void setEntityManagerfactory(final EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }
}
