package net.leludo.gtrchamp.dao;

import javax.persistence.EntityManagerFactory;

public class DataAccess {

    private static DataAccess instance;
    private EntityManagerFactory emf;

    private DataAccess() {
        emf = null;
    }

    public static final DataAccess getInstance() {
        if (instance == null) {
            instance = new DataAccess();
        }
        return instance;
    }

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
