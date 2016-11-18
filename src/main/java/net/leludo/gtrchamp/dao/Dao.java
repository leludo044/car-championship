package net.leludo.gtrchamp.dao;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;

public class Dao {

    /** Entity manager to use. */
    private EntityManager em;

    public Dao() {
        super();
        em = null;
    }

    /**
     * Return the entity manager used by this DAO. Must be called by all the derived class to obtain
     * access to the database.
     *
     * @return The entity manager to use
     */
    protected EntityManager getSession() {
        return this.em;
    }

    /**
     * Set the entityManager that will be used by the DAO.
     *
     * @param entityManager
     *            The new entity manager to use
     */
    protected void entityManager(final EntityManager entityManager) {
        this.em = entityManager;
        this.em.setFlushMode(FlushModeType.AUTO);
    }


}
