package net.leludo.gtrchamp.dao;

import javax.inject.Singleton;

import net.leludo.gtrchamp.PointSet;

/**
 * DAO for scoring system access.
 */
@Singleton
public class ScoringDao extends DefaultDao<PointSet, String> {

    /**
     * Constructor.
     */
    public ScoringDao() {
        super(PointSet.class);
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
        javax.persistence.Query query = this.em.createQuery(queryString);
        query.setParameter("scoringSystem", scoringSystem);
        this.em.clear();
        return (Integer) query.getSingleResult();
    }
}
