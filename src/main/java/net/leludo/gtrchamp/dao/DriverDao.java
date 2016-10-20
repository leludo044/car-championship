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
	protected DriverDao() {
		super(Driver.class);
	}

	/**
	 * @return all the drivers
	 */
	public List<Driver> findAll() {
		String queryString = "from Driver";
		javax.persistence.Query query = this.getSession().createQuery(queryString);
		this.getSession().clear();
		return query.getResultList();
	}

	/**
	 * Create a new driver.
	 *
	 * @param pilote
	 *            The driver to crate
	 */
	public void create(final Driver pilote) {
		this.getSession().getTransaction().begin();
		this.getSession().persist(pilote);
		this.getSession().getTransaction().commit();
		this.getSession().clear();
	}

	/**
	 * Update an existing driver.
	 *
	 * @param pilote
	 *            The driver to update
	 */
	public void update(final Driver pilote) {
		this.getSession().getTransaction().begin();
		this.getSession().merge(pilote);
		this.getSession().getTransaction().commit();
		this.getSession().clear();
	}

	/**
	 * Delete a driver.
	 *
	 * @param pilote
	 *            The driver to delete
	 */
	public void delete(final Driver pilote) {
		this.getSession().getTransaction().begin();
		this.getSession().remove(pilote);
		this.getSession().getTransaction().commit();
		this.getSession().clear();
	}

	/**
	 * Say if a driver ran a race.
	 *
	 * @param piloteId
	 *            The id of the driver to ask
	 * @return true ou false
	 */
	public boolean ran(final int piloteId) {
		this.getSession().getTransaction().begin();
		Query query = this.getSession().createQuery("select count(*) from Competitor where driver.id=:id");
		query.setParameter("id", piloteId);
		Long raceNumber = (Long) query.getSingleResult();
		this.getSession().getTransaction().commit();

		return raceNumber > 0;
	}
}
