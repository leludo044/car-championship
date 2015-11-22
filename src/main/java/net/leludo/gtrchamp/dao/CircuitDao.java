package net.leludo.gtrchamp.dao;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.Query;

import net.leludo.gtrchamp.Circuit;
import net.leludo.gtrchamp.Pilote;

@Singleton
public class CircuitDao extends DefaultDao<Circuit, Integer> {

	public CircuitDao() {
		super(Circuit.class);
	}

	/**
	 * Retourne la liste de tous les circuits
	 * 
	 * @return La liste des tous les circuits
	 */
	public List<Circuit> findAll() {
		String queryString = "from Circuit order by nom";
		javax.persistence.Query query = this.em.createQuery(queryString);
		this.em.clear();
		return query.getResultList();
	}

	/**
	 * Crée un circuit en base
	 * 
	 * @param circuit
	 *            Le circuit à sauvegarder
	 */
	public void create(Circuit circuit) {
		this.em.getTransaction().begin();
		this.em.persist(circuit);
		this.em.getTransaction().commit();
		this.em.clear();
	}

	/**
	 * Modifie un circuit en base
	 * 
	 * @param circuit
	 *            Le circuit à modifier
	 */
	public void update(Circuit circuit) {
		this.em.getTransaction().begin();
		this.em.merge(circuit);
		this.em.getTransaction().commit();
		this.em.clear();
	}

	/**
	 * Supprime un circuit en base
	 * 
	 * @param circuit
	 *            Le circuit à supprimer
	 */
	public void delete(Circuit circuit) {
		this.em.getTransaction().begin();
		this.em.remove(circuit);
		this.em.getTransaction().commit();
		this.em.clear();
	}

	/**
	 * Indique si un circuit a déjà été couru
	 * 
	 * @param circuitId
	 *            L'ID du circuit concerné
	 * @return true ou false
	 */
	public boolean estCouru(int circuitId) {
		this.em.getTransaction().begin();
		Query query = this.em.createQuery("select count(*) from Concurrent where grandPrix.circuit.id=:id");
		query.setParameter("id", circuitId);
		Long nbCourses = (Long) query.getSingleResult();
		this.em.getTransaction().commit();

		return nbCourses > 0;
	}
}
