package net.leludo.gtrchamp.dao;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import net.leludo.gtrchamp.Competitor;
import net.leludo.gtrchamp.CompetitorId;

@Singleton
public class ResultDao extends DefaultDao<Competitor, CompetitorId> {

    /**
     * Constructor.
     */
    public ResultDao() {
        super(Competitor.class);
    }

    public void setEntityManager(EntityManager em) {
        this.em = em ;
    }

    public List<Competitor> find(final Integer raceId, final int raceNumber) {
        Query query = this.em.createQuery("from Competitor c where race.id=:raceId and id.raceNumber=:raceNumber") ;
        query.setParameter("raceId", raceId);
        query.setParameter("raceNumber", raceNumber);
        return query.getResultList();
    }


    /**
     * Create a new result.
     *
     * @param competitor
     *            The competitor to crate
     */
    public void create(final Competitor competitor) {
        if (!this.em.getTransaction().isActive()) {
            this.em.getTransaction().begin();
        }
        this.em.merge(competitor);
        //this.em.persist(competitor);
        this.em.getTransaction().commit();
        this.em.clear();
    }

    /**
     * Update an existing result.
     *
     * @param competitor
     *            The competitor to update
     */
    public void update(final Competitor competitor) {
        this.em.getTransaction().begin();
        this.em.merge(competitor);
        this.em.getTransaction().commit();
        this.em.clear();
    }

    /**
     * Delete a result.
     *
     * @param competitor
     *            The competitor to delete
     */
    public void delete(final Competitor competitor) {
        this.em.getTransaction().begin();
        this.em.remove(competitor);
        this.em.getTransaction().commit();
        this.em.clear();
    }
}
