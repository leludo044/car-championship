package net.leludo.gtrchamp.dao;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;

import net.leludo.gtrchamp.Pilote;

@Singleton
public class PiloteDao extends DefaultDao<Pilote, Integer> {

	public PiloteDao() {
		super(Pilote.class);
	}

	/**
	 * Retourne la liste de tous les pilotes
	 * 
	 * @return La liste des tous les pilotes
	 */
	public List<Pilote> findAll() {
		String queryString = "from Pilote";
		javax.persistence.Query query = this.em.createQuery(queryString);
		this.em.clear();
		return query.getResultList();
	}

	/**
	 * Crée un pilote en base
	 * 
	 * @param pilote
	 *            Le pilote à sauvegarder
	 */
	public void create(Pilote pilote) {
		this.em.getTransaction().begin();
		this.em.persist(pilote);
		this.em.getTransaction().commit();
		this.em.clear();
	}

	/**
	 * Modifie un pilote en base
	 * 
	 * @param pilote
	 *            Le pilote à modifier
	 */
	public void update(Pilote pilote) {
		this.em.getTransaction().begin();
		this.em.merge(pilote);
		this.em.getTransaction().commit();
		this.em.clear();
	}

	/**
	 * Supprime un pilote en base
	 * 
	 * @param pilote
	 *            Le pilote à supprimer
	 */
	public void delete(Pilote pilote) {
		this.em.getTransaction().begin();
		this.em.remove(pilote);
		this.em.getTransaction().commit();
		this.em.clear();
	}

}
