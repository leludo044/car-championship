package net.leludo.gtrchamp.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class DefaultDao<T, PK> {
    private EntityManagerFactory emf;
    protected EntityManager em;
    private Class<T> entityClass;

    public DefaultDao(final Class<T> entity) {
        entityClass = entity;
        em = DaoManager.getInstance().entityManagerFactory().createEntityManager();
    }

    public void close() {
        if (em != null) {
            em.close();
        }
    }

    @Deprecated
    public void setEntityManager(final EntityManagerFactory emf) {
        this.emf = emf;
        em = emf.createEntityManager();
    }

    public T find(final PK id) {
        T entity = em.find(entityClass, id);
        return entity;
    }

}
