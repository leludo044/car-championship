package net.leludo.gtrchamp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import net.leludo.gtrchamp.Driver;
import org.springframework.stereotype.Component;

/**
 * DAO for driver access.
 */
public class DriverDao extends DefaultDao<Driver, Integer> {

    /**
     * Constructor.
     *
     * @param entityManager
     *            The JPA entity manager affected to this DAO
     */
    protected DriverDao(final EntityManager entityManager) {
        super(Driver.class);
        super.entityManager(entityManager);
    }

    /**
     * Return all the drivers.
     *
     * @return all the drivers
     */
    public List<Driver> findAll() {
        EntityManager session = this.getSession();
        String queryString = "from Driver";
        javax.persistence.Query query = session.createQuery(queryString);
        return query.getResultList();
    }

    /**
     * Create a new driver.
     *
     * @param pilote
     *            The driver to crate
     */
    public void create(final Driver pilote) {
        EntityManager session = this.getSession();
        session.getTransaction().begin();
        this.getSession().persist(pilote);
        session.getTransaction().commit();
    }

    /**
     * Update an existing driver.
     *
     * @param pilote
     *            The driver to update
     */
    public void update(final Driver pilote) {
        EntityManager session = this.getSession();
        session.getTransaction().begin();
        this.getSession().merge(pilote);
        session.getTransaction().commit();
    }

    /**
     * Delete a driver.
     *
     * @param pilote
     *            The driver to delete
     */
    public void delete(final Driver pilote) {
        EntityManager session = this.getSession();
        session.getTransaction().begin();
        this.getSession().remove(pilote);
        session.getTransaction().commit();
    }

    /**
     * Say if a driver ran a race.
     *
     * @param piloteId
     *            The id of the driver to ask
     * @return true ou false
     */
    public boolean ran(final int piloteId) {
        EntityManager session = this.getSession();
        Query query = session.createQuery("select count(*) from Competitor where driver.id=:id");
        query.setParameter("id", piloteId);
        Long raceNumber = (Long) query.getSingleResult();

        return raceNumber > 0;
    }
}
