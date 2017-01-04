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
	 *
	 * @param entityManager
	 *            The JPA entity manager affected to this DAO
	 */
	protected ResultDao(final EntityManager entityManager) {
		super(Competitor.class);
		super.entityManager(entityManager);
	}

	/**
	 * Return all the results for a race
	 * 
	 * @param raceId
	 *            The race id to find
	 * @param raceNumber
	 *            The race number to find
	 * @return
	 */
	public List<Competitor> find(final Integer raceId, final int raceNumber) {
		Query query = this.getSession()
				.createQuery("from Competitor c where race.id=:raceId and id.raceNumber=:raceNumber");
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
		if (!this.getSession().getTransaction().isActive()) {
			this.getSession().getTransaction().begin();
		}
		this.getSession().merge(competitor);
		// this.getSession().persist(competitor);
		this.getSession().getTransaction().commit();
		this.getSession().clear();
	}

	/**
	 * Update an existing result.
	 *
	 * @param competitor
	 *            The competitor to update
	 */
	public void update(final Competitor competitor) {
		this.getSession().getTransaction().begin();
		this.getSession().merge(competitor);
		this.getSession().getTransaction().commit();
		this.getSession().clear();
	}

	/**
	 * Delete a known result.
	 *
	 * @param competitor
	 *            The competitor to delete
	 */
	public void delete(final Competitor competitor) {
		this.getSession().getTransaction().begin();
		this.getSession().remove(competitor);
		this.getSession().getTransaction().commit();
		this.getSession().clear();
	}

	/**
	 * Delete a result from his id.
	 *
	 * @param raceId
	 *            The raceId
	 * @param driverId
	 *            The raceId
	 * @param raceNumber
	 *            The raceId
	 * @return the number deleted entities
	 */
	public int delete(final int raceId, final int driverId, final int raceNumber) {
		this.getSession().getTransaction().begin();
		String deleteQuery = "delete from Competitor c where c.race.id=:raceId and c.driver.id=:driverId and c.id.raceNumber=:raceNumber";
		javax.persistence.Query query = this.getSession().createQuery(deleteQuery);
		query.setParameter("raceId", raceId);
		query.setParameter("driverId", driverId);
		query.setParameter("raceNumber", raceNumber);
		int rowCount = query.executeUpdate();
		this.getSession().getTransaction().commit();
		this.getSession().clear();
		return rowCount;
	}

}
