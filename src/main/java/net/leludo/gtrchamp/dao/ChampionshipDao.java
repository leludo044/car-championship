package net.leludo.gtrchamp.dao;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.Query;

import net.leludo.gtrchamp.Championship;

/**
 * Championship repository based on JPA configuration.
 *
 * @author Ludovic THOMAS
 *
 */
@Singleton
public class ChampionshipDao extends DefaultDao<Championship, Integer> {

    /**
     * Constructor.
     */
    public ChampionshipDao() {
        super(Championship.class);
    }

    /**
     * @return all the championships
     */
    public List<Championship> all() {
        String queryString = "from Championship";
        javax.persistence.Query query = this.em.createQuery(queryString);
        return query.getResultList();
    }

    /**
     * Return the results of a race.
     *
     * @param raceId
     *            The id of the race to get results
     * @return the results of the race
     */
    public List<Object[]> results(final int raceId) {
        // String queryString = "from Concurrent where grandPrix.id=:id order by
        // numCourse, place";
        String queryString = "select c, p.points from Concurrent c, PointSet p where c.grandPrix.id=:id and p.rank = c.positionArrivee and p.type = c.grandPrix.championship.type order by c.id.numCourse, p.rank";
        javax.persistence.Query query = this.em.createQuery(queryString);
        query.setParameter("id", raceId);
        return query.getResultList();
    }

    /**
     * Return the standings of a championship.
     *
     * @param idChampionnat
     *            The id of the championship to get standings
     * @return the standings of the championship
     */
    public List<Object[]> standings(final int idChampionnat) {
        // String queryString = "select pilote, sum(c.points.points) from
        // Concurrent c where c.grandPrix.championnat.id = :id group by c.pilote
        // order by sum(c.points.points) desc";
        String queryString = "select c.pilote, sum(p.points) from Concurrent c, PointSet p where c.grandPrix.championship.id = :id and p.rank = c.positionArrivee and p.type = c.grandPrix.championship.type group by c.pilote order by sum(p.points) desc";

        javax.persistence.Query query = this.em.createQuery(queryString);
        query.setParameter("id", idChampionnat);
        List<Object[]> toto = query.getResultList();
        return toto;
    }

    /**
     * Save a new championship.
     *
     * @param championnat
     *            The new championship to save
     */
    public void save(final Championship championnat) {
        this.em.getTransaction().begin();
        this.em.persist(championnat);
        this.em.getTransaction().commit();
        this.em.clear();
    }

    /**
     * Update an existing championship.
     *
     * @param championnat
     *            The existing championship to update
     */
    public void update(final Championship championnat) {
        this.em.getTransaction().begin();
        this.em.merge(championnat);
        this.em.getTransaction().commit();
        this.em.clear();
    }

    /**
     * Delete an existing championship.
     *
     * @param championnat
     *            Le existing championship to delete
     */
    public void delete(final Championship championnat) {
        this.em.getTransaction().begin();
        this.em.remove(championnat);
        this.em.getTransaction().commit();
        this.em.clear();
    }

    /**
     * Say if a championship is started or not. A championship is started if at
     * least one race is planned
     *
     * @param id
     *            The id of the championship to check
     * @return true ou false
     */
    public boolean isStarted(final int id) {
        this.em.getTransaction().begin();
        Query query = this.em
                .createQuery("select count(*) from Race where championship.id=:id");
        query.setParameter("id", id);
        Long nbGrandsPrix = (Long) query.getSingleResult();
        this.em.getTransaction().commit();

        return nbGrandsPrix > 0;
    }
}
