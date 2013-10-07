package net.leludo.gtrchamp.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class DefaultDao<T, PK> {
	private EntityManagerFactory emf;
	private EntityManager em ;
	Class<T> entityClass ;
	
	public DefaultDao(Class<T> entity) {
		em = emf.createEntityManager();
		entityClass = entity ;
	}
	
	public T find(PK id) {
		Class<T> type ;
		return em.find(entityClass, id);
	}
}
