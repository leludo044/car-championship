package net.leludo.gtrchamp.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import net.leludo.gtrchamp.stat.Stat;

/**
 * DAO for statistics.
 */
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

    /**
     * Return the statistics about the pole position count.
     *
     * @return the statistics about the pole position count
     */
    public List<Stat> findNbPoles() {
        Session session = this.getSession().unwrap(org.hibernate.Session.class);
        Query query = session.getNamedQuery("poleCount");
        query.setResultTransformer(Transformers.aliasToBean(Stat.class));
        return query.list();
    }

    /**
     * Return the statistics about the pole position count for a championship.
     *
     * @param id
     *            The id of the targeted championship
     * @return the statistics about the pole position count
     */
    public List<Stat> findNbPoles(final int id) {
        Session session = this.getSession().unwrap(org.hibernate.Session.class);
        Query query = session.getNamedQuery("filteredPoleCount");
        query.setInteger("id", id);
        query.setResultTransformer(Transformers.aliasToBean(Stat.class));
        return query.list();
    }

    /**
     * Return the statistics about the podium count.
     *
     * @return the statistics about the podium count
     */
    public List<Stat> findNbPodium() {
        Session session = this.getSession().unwrap(org.hibernate.Session.class);
        Query query = session.getNamedQuery("podiumCount");
        query.setResultTransformer(Transformers.aliasToBean(Stat.class));
        return query.list();
    }

    /**
     * Return the statistics about the podium count for a championship.
     *
     * @param id
     *            The id of the targeted championship
     * @return the statistics about the podium count
     */
    public List<Stat> findNbPodium(final int id) {
        Session session = this.getSession().unwrap(org.hibernate.Session.class);
        Query query = session.getNamedQuery("filteredPodiumCount");
        query.setInteger("id", id);
        query.setResultTransformer(Transformers.aliasToBean(Stat.class));
        return query.list();
    }

    /**
     * Return the statistics about the victory count.
     *
     * @return the statistics about the victory count
     */
    public List<Stat> findNbVictories() {
        Session session = this.getSession().unwrap(org.hibernate.Session.class);
        Query query = session.getNamedQuery("victoryCount");
        query.setResultTransformer(Transformers.aliasToBean(Stat.class));
        return query.list();
    }

    /**
     * Return the statistics about the victory count for a championship.
     *
     * @param id
     *            The id of the targeted championship
     * @return the statistics about the victory count
     */
    public List<Stat> findNbVictories(final int id) {
        Session session = this.getSession().unwrap(org.hibernate.Session.class);
        Query query = session.getNamedQuery("filteredVictoryCount");
        query.setInteger("id", id);
        query.setResultTransformer(Transformers.aliasToBean(Stat.class));
        return query.list();
    }
}
