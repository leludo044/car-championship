package net.leludo.gtrchamp.dao;

import java.util.List;

import javax.inject.Singleton;

import net.leludo.gtrchamp.Championnat;
import net.leludo.gtrchamp.Concurrent;

@Singleton
public class ChampionnatDao extends DefaultDao<Championnat, Integer> {

	public ChampionnatDao() {
		super(Championnat.class);
	}
	
	public List<Championnat> findAll() {
		String queryString = "from Championnat" ;
		javax.persistence.Query query = this.em.createQuery(queryString) ;
		return query.getResultList() ;
	}
	
	public List<Concurrent> findResultats(final int idGrandPrix) {
		String queryString = "from Concurrent where grandPrix.id=:id order by numCourse, place" ;
		javax.persistence.Query query = this.em.createQuery(queryString) ;
		query.setParameter("id", idGrandPrix);
		return query.getResultList() ;	
	}
	
	public List findClassement(final int idChampionnat) {
		String queryString = "select pilote, sum(c.points.points) from Concurrent c	where c.grandPrix.championnat.id = :id group by c.pilote order by sum(c.points.points) desc";

		javax.persistence.Query query = this.em.createQuery(queryString) ;
		query.setParameter("id", idChampionnat);
		List toto = query.getResultList() ;
		return toto ;
	}
	
}
