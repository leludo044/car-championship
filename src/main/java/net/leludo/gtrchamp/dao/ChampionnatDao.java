package net.leludo.gtrchamp.dao;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.Query;

import net.leludo.gtrchamp.Championnat;
import net.leludo.gtrchamp.Circuit;
import net.leludo.gtrchamp.Concurrent;
import net.leludo.gtrchamp.Pilote;

@Singleton
public class ChampionnatDao extends DefaultDao<Championnat, Integer> {

	public ChampionnatDao() {
		super(Championnat.class);
	}

	public List<Championnat> findAll() {
		String queryString = "from Championnat";
		javax.persistence.Query query = this.em.createQuery(queryString);
		return query.getResultList();
	}

	public List<Concurrent> findResultats(final int idGrandPrix) {
		String queryString = "from Concurrent where grandPrix.id=:id order by numCourse, place";
		javax.persistence.Query query = this.em.createQuery(queryString);
		query.setParameter("id", idGrandPrix);
		return query.getResultList();
	}

	public List<Object[]> findClassement(final int idChampionnat) {
		String queryString = "select pilote, sum(c.points.points) from Concurrent c	where c.grandPrix.championnat.id = :id group by c.pilote order by sum(c.points.points) desc";

		javax.persistence.Query query = this.em.createQuery(queryString);
		query.setParameter("id", idChampionnat);
		List<Object[]> toto = query.getResultList();
		return toto;
	}

	/**
	 * Crée un championnat en base
	 * 
	 * @param championnat
	 *            Le championnat à sauvegarder
	 */
	public void create(Championnat championnat) {
		this.em.getTransaction().begin();
		this.em.persist(championnat);
		this.em.getTransaction().commit();
		this.em.clear();
	}

	/**
	 * Modifie un championnat en base
	 * 
	 * @param championnat
	 *            Le championnat à modifier
	 */
	public void update(Championnat championnat) {
		this.em.getTransaction().begin();
		this.em.merge(championnat);
		this.em.getTransaction().commit();
		this.em.clear();
	}

	/**
	 * Supprime un championnat en base
	 * 
	 * @param championnat
	 *            Le championnat à supprimer
	 */
	public void delete(Championnat championnat) {
		this.em.getTransaction().begin();
		this.em.remove(championnat);
		this.em.getTransaction().commit();
		this.em.clear();
	}

	/**
	 * Indique si un championnat est commencé
	 * 
	 * @param id
	 *            L'ID du championnat concerné
	 * @return true ou false
	 */
	public boolean estCommence(int id) {
		this.em.getTransaction().begin();
		Query query = this.em.createQuery("select count(*) from GrandPrix where championnat.id=:id");
		query.setParameter("id", id);
		Long nbGrandsPrix = (Long) query.getSingleResult();
		this.em.getTransaction().commit();

		return nbGrandsPrix > 0;
	}

}
