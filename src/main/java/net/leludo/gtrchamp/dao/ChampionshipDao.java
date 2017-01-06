package net.leludo.gtrchamp.dao;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
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
     *
     * @param entityManager
     *            The JPA entity manager affected to this DAO
     */
    protected ChampionshipDao(final EntityManager entityManager) {
        super(Championship.class);
        super.entityManager(entityManager);
    }

    /**
     * Return all the championships.
     *
     * @return all the championships
     */
    public List<Championship> all() {
        String queryString = "from Championship";
        javax.persistence.Query query = this.getSession().createQuery(queryString);
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
        String queryString = "select c, p.points from Competitor c, PointSet p where c.race.id=:id and p.rank = c.arrivalPosition and p.type = c.race.championship.type order by c.id.raceNumber, p.rank";
        javax.persistence.Query query = this.getSession().createQuery(queryString);
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
        String queryString = "select c.driver, sum(p.points) from Competitor c, PointSet p where c.race.championship.id = :id and p.rank = c.arrivalPosition and p.type = c.race.championship.type group by c.driver order by sum(p.points) desc";

        javax.persistence.Query query = this.getSession().createQuery(queryString);
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
        this.getSession().getTransaction().begin();
        this.getSession().persist(championnat);
        this.getSession().getTransaction().commit();
        this.getSession().clear();
    }

    /**
     * Update an existing championship.
     *
     * @param championnat
     *            The existing championship to update
     */
    public void update(final Championship championnat) {
        this.getSession().getTransaction().begin();
        this.getSession().merge(championnat);
        this.getSession().getTransaction().commit();
        this.getSession().clear();
    }

    /**
     * Delete an existing championship.
     *
     * @param championnat
     *            Le existing championship to delete
     */
    public void delete(final Championship championnat) {
        this.getSession().getTransaction().begin();
        this.getSession().remove(championnat);
        this.getSession().getTransaction().commit();
        this.getSession().clear();
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
        this.getSession().getTransaction().begin();
        Query query = this.getSession()
                .createQuery("select count(*) from Race where championship.id=:id");
        query.setParameter("id", id);
        Long nbGrandsPrix = (Long) query.getSingleResult();
        this.getSession().getTransaction().commit();

        return nbGrandsPrix > 0;
    }
}
