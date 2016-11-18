package net.leludo.gtrchamp.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import net.leludo.gtrchamp.stat.Stat;

public class StatDao extends Dao {

    /**
     * Constructor.
     *
     * @param entityManager
     *            The JPA entity manager affected to this DAO
     */
    protected StatDao(final EntityManager entityManager) {
        super();
        super.entityManager(entityManager);
    }

    public List<Stat> findNbPoles() {
        Session session = this.getSession().unwrap(org.hibernate.Session.class);
        Query query = session.getNamedQuery("poleCount");
        query.setResultTransformer(Transformers.aliasToBean(Stat.class));
        return query.list();
    }

    public List<Stat> findNbPoles(int id) {
        Session session = this.getSession().unwrap(org.hibernate.Session.class);
        Query query = session.getNamedQuery("filteredPoleCount");
        query.setInteger("id", id);
        query.setResultTransformer(Transformers.aliasToBean(Stat.class));
        return query.list();
    }

    public List<Stat> findNbPodium() {
        Session session = this.getSession().unwrap(org.hibernate.Session.class);
        Query query = session.getNamedQuery("podiumCount");
        query.setResultTransformer(Transformers.aliasToBean(Stat.class));
        return query.list();
    }

    public List<Stat> findNbPodium(int id) {
        Session session = this.getSession().unwrap(org.hibernate.Session.class);
        Query query = session.getNamedQuery("filteredPodiumCount");
        query.setInteger("id", id);
        query.setResultTransformer(Transformers.aliasToBean(Stat.class));
        return query.list();
    }

    public List<Stat> findNbVictories() {
        Session session = this.getSession().unwrap(org.hibernate.Session.class);
        Query query = session.getNamedQuery("victoryCount");
        query.setResultTransformer(Transformers.aliasToBean(Stat.class));
        return query.list();
    }

    public List<Stat> findNbVictories(int id) {
        Session session = this.getSession().unwrap(org.hibernate.Session.class);
        Query query = session.getNamedQuery("filteredVictoryCount");
        query.setInteger("id", id);
        query.setResultTransformer(Transformers.aliasToBean(Stat.class));
        return query.list();
    }
}
