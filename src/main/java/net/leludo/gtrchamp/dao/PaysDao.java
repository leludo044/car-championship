package net.leludo.gtrchamp.dao;

import java.util.List;

import javax.inject.Singleton;

import net.leludo.gtrchamp.Pays;

@Singleton
public class PaysDao extends DefaultDao<Pays, Integer> {

	public PaysDao() {
		super(Pays.class);
	}

	/**
	 * Retourne la liste de tous les pays
	 * 
	 * @return La liste des tous les pays
	 */
	public List<Pays> findAll() {
		String queryString = "from Pays";
		javax.persistence.Query query = this.em.createQuery(queryString);
		this.em.clear();
		return query.getResultList();
	}

	/**
	 * Retourne un pays en fonction de son id
	 * 
	 * @param id
	 *            L'id du pays à rechercher
	 * @return Le pays correspondant
	 */
	public Pays get(Integer id) {
		return find(id);
	}
}
