package net.leludo.gtrchamp.dao;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.Query;

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
		return query.getResultList();
	}

	/**
	 * CrÃ©e un pilote en base
	 * 
	 * @param pilote
	 *            Le pilote Ã  sauvegarder
	 */
	public void create(Pilote pilote) {
		this.em.getTransaction().begin();
		this.em.persist(pilote);
		this.em.getTransaction().commit();
	}
	
	/**
	 * Modifie un pilote en base
	 * 
	 * @param pilote
	 *            Le pilote Ã  modifier
	 */
	public void update(Pilote pilote) {
		this.em.getTransaction().begin();
		this.em.merge(pilote);
		this.em.getTransaction().commit();
	}

	
	/**
	 * Supprime un pilote en base
	 * 
	 * @param pilote
	 *            Le pilote Ã  supprimer
	 */
	public void delete(Pilote pilote) {
		this.em.getTransaction().begin();
		this.em.remove(pilote);
		this.em.getTransaction().commit();
	}
	
	/**
	 * Indique si un pilote à déjà participé à au moins un grnd prix
	 * @param piloteId L'ID du pilote concerné
	 * @return true ou false
	 */
	public boolean aCouru(int piloteId) {
		this.em.getTransaction().begin();
		Query query = this.em.createQuery("select count(*) from Concurrent where pilote.id=:id");
		query.setParameter("id", piloteId);
		Long nbCourses = (Long) query.getSingleResult();
		this.em.getTransaction().commit();
		
		return nbCourses > 0 ;
	}
}
