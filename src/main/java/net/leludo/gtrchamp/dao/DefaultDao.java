package net.leludo.gtrchamp.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import net.leludo.gtrchamp.Championnat;

public class DefaultDao<T, PK> {
	private EntityManagerFactory emf;
	protected EntityManager em ;
	Class<T> entityClass ;
	
	public DefaultDao(Class<T> entity) {
		entityClass = entity ;
	}
	
	public void close() {
		if (em != null) {
			em.close();
		}
	}
	
	public void setEntityManager(EntityManagerFactory emf) {
		this.emf =emf ;
		em = emf.createEntityManager();
	}
	public T find(PK id) {
		T entity = em.find(entityClass, id);
		return entity;
	}
}
