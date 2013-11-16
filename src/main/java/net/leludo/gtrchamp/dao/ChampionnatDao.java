package net.leludo.gtrchamp.dao;

import java.util.List;

import javax.inject.Singleton;

import net.leludo.gtrchamp.Championnat;

import org.hibernate.Query;

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
}
