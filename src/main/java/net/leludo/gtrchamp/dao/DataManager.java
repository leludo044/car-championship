package net.leludo.gtrchamp.dao;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

/**
 * High level class for DAO management. Provide DaoFactory, each one with its
 * entity manager.
 *
 */
@Component
public final class DataManager {

    /** The instance of the manager. */
    private static DataManager instance;

    /**
     * The JPA entity manager factory to use to create the entity managers for
     * the DAO factory.
     */
    private EntityManagerFactory emf;

    /**
     * Constructor. Private to avoid instantiation
     */
    private DataManager() {
        emf = null;
    }

    /**
     * Return the unique instance of the manager.
     *
     * @return the unique instance of the manager
     */
    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    /**
     * Return a DAO factory linked with an newly created JPA entity manager.
     *
     * @return the DAO factory
     */
    public DaoFactory getManager() {
        return new DaoFactory(emf.createEntityManager());
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
