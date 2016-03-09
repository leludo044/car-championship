package net.leludo.gtrchamp.dao;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.Query;

import net.leludo.gtrchamp.Driver;

@Singleton
public class DriverDao extends DefaultDao<Driver, Integer> {

	/**
	 * Constructor.
	 */
	public DriverDao() {
		super(Driver.class);
	}

	/**
	 * @return all the drivers
	 */
	public List<Driver> findAll() {
		String queryString = "from Driver";
		javax.persistence.Query query = this.em.createQuery(queryString);
		this.em.clear();
		return query.getResultList();
	}

	/**
	 * Create a new driver.
	 * 
	 * @param pilote
	 *            The driver to crate
	 */
	public void create(final Driver pilote) {
		this.em.getTransaction().begin();
		this.em.persist(pilote);
		this.em.getTransaction().commit();
		this.em.clear();
	}

	/**
	 * Update an existing driver.
	 * 
	 * @param pilote
	 *            The driver to update
	 */
	public void update(final Driver pilote) {
		this.em.getTransaction().begin();
		this.em.merge(pilote);
		this.em.getTransaction().commit();
		this.em.clear();
	}

	/**
	 * Delete a driver.
	 * 
	 * @param pilote
	 *            The driver to delete
	 */
	public void delete(final Driver pilote) {
		this.em.getTransaction().begin();
		this.em.remove(pilote);
		this.em.getTransaction().commit();
		this.em.clear();
	}

	/**
	 * Say if a driver ran a race.
	 * 
	 * @param piloteId
	 *            The id of the driver to ask
	 * @return true ou false
	 */
	public boolean ran(final int piloteId) {
		this.em.getTransaction().begin();
		Query query = this.em.createQuery("select count(*) from Concurrent where pilote.id=:id");
		query.setParameter("id", piloteId);
		Long raceNumber = (Long) query.getSingleResult();
		this.em.getTransaction().commit();

		return raceNumber > 0;
	}
}
