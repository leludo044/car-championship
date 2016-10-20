package net.leludo.gtrchamp.dao;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for all the application DAO
 *
 * @param <T>
 *            The class of the entity to manage
 * @param <PK>
 *            The type of her primary key
 */
public class DefaultDao<T, PK> {

    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(DefaultDao.class);

    /** Entity manager to use. */
    private EntityManager em;

    /** The class of the entity to manage. */
    private Class<T> entityClass;

    /**
     * Constructor.
     *
     * @param entity
     *            The class of the entity to manage
     */
    public DefaultDao(final Class<T> entity) {
        // FIXME Avoir entity parameter. use dynamic instanciation
        entityClass = entity;
        em = null;
        if (LOG.isDebugEnabled()) {
            LOG.debug("Instanciate repository " + super.getClass());
        }
    }

    /**
     * Get an entity manager. Must be called by all the derived class to obtain
     * access to the database.
     *
     * @return The entity manager to use
     */
    protected EntityManager getSession() {
        if (em == null) {
            em = DaoFactory.getInstance().entityManagerFactory().createEntityManager();
        }
        return em;
    }

    /**
     * Close the entity manager.
     */
    public void close() {
        if (em != null) {
            em.close();
            em = null;
        }
    }

    /**
     * Default method to find an entity by his id.
     *
     * @param id
     *            The id
     * @return The corresponding entity
     */
    public T find(final PK id) {
        T entity = this.getSession().find(entityClass, id);
        return entity;
    }

}
