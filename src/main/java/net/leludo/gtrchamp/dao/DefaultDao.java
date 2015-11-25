package net.leludo.gtrchamp.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Mutualisation des fonctionnalités basique d'un DAO
 *
 * @param <T>
 *            Le type de l'entité gérée par le DAO
 * @param <PK>
 *            Le type de la clé primaire géré par le DAO
 */
public class DefaultDao<T, PK> {

	private EntityManagerFactory emf;
	protected EntityManager em;
	private Class<T> entityClass;

	/**
	 * Constructeur alimentée par le type de la classe à gérer
	 * 
	 * @param entity
	 *            La classe de l'entité à gérer
	 */
	public DefaultDao(Class<T> entity) {
		entityClass = entity;
	}

	/**
	 * Ferme le DAO en fermant le gestionnaire JPA. Permet de libérer la
	 * session.
	 */
	public void close() {
		if (em != null) {
			em.close();
		}
	}

	/**
	 * Fixe le gestionnaire JPA à utiliser
	 * 
	 * @param emf
	 *            Le gestionnaire JPA (Entity Manager)
	 */
	public void setEntityManager(EntityManagerFactory emf) {
		this.emf = emf;
		em = emf.createEntityManager();
	}

	/**
	 * Retourne une entité ayant un id particulier
	 * 
	 * @param id
	 *            L'id à rechercher
	 * @return L'entité correspondant à l'id
	 */
	public T find(PK id) {
		T entity = em.find(entityClass, id);
		return entity;
	}

}
