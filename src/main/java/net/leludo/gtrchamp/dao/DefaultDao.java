package net.leludo.gtrchamp.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for all the application DAO.
 *
 * @param <T>
 *            The class of the entity to manage
 * @param <PK>
 *            The type of her primary key
 */
public class DefaultDao<T, PK> extends Dao {

    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(DefaultDao.class);

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

        if (LOG.isDebugEnabled()) {
            LOG.debug("Instanciate repository " + super.getClass());
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
