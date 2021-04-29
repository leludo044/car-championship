package net.leludo.gtrchamp.dao;

import javax.persistence.EntityManager;

import net.leludo.gtrchamp.PointSet;
import org.springframework.stereotype.Component;

/**
 * DAO for scoring system access.
 */
public class ScoringDao extends DefaultDao<PointSet, String> {

    /**
     * Constructor.
     *
     * @param entityManager
     *            The JPA entity manager affected to this DAO
     */
    protected ScoringDao(final EntityManager entityManager) {
        super(PointSet.class);
        super.entityManager(entityManager);
    }

    /**
     * Return the maximum arrival position allowed for a scoring system.
     *
     * @param scoringSystem
     *            The scoring system concerned
     * @return The maximum arrival position allowed
     */
    public Integer max(final String scoringSystem) {
        String queryString = "select max(rank) from PointSet where type=:scoringSystem";
        javax.persistence.Query query = this.getSession().createQuery(queryString);
        query.setParameter("scoringSystem", scoringSystem);
        this.getSession().clear();
        return (Integer) query.getSingleResult();
    }
}
