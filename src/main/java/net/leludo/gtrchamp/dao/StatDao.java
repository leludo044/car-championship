package net.leludo.gtrchamp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import net.leludo.gtrchamp.stat.Stat;

/**
 * DAO d'extraction des données statistiques
 */
public class StatDao {

	private EntityManagerFactory emf;
	protected EntityManager em;

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
	 * Ferme le DAO en fermant le gestionnaire JPA. Permet de libérer la
	 * session.
	 */
	public void close() {
		if (em != null) {
			em.close();
		}
	}

	/**
	 * Retourne le nombre de victoires par pilote
	 * 
	 * @return Le nombre de victoires par pilote
	 */
	public List<Stat> findNbVictories() {
		Session session = this.em.unwrap(org.hibernate.Session.class);
		SQLQuery query = session.createSQLQuery(
				"select pilotes.nom as name, sum(!isnull(resultats.place)) as count from pilotes left join resultats on pilotes.id = resultats.idPilote and resultats.place=1 group by pilotes.nom order by count desc;");
		query.addScalar("name", StringType.INSTANCE);
		query.addScalar("count", IntegerType.INSTANCE);
		query.setResultTransformer(Transformers.aliasToBean(Stat.class));
		return query.list();
	}

	/**
	 * Retourne le nombre de poles position par pilote
	 * 
	 * @return Le nombre de poles position par pilote
	 */
	public List<Stat> findNbPoles() {
		Session session = this.em.unwrap(org.hibernate.Session.class);
		SQLQuery query = session.createSQLQuery(
				"select pilotes.nom as name, sum(!isnull(resultats.place)) as count from pilotes left join resultats on pilotes.id = resultats.idPilote and resultats.grille=1 group by pilotes.nom order by count desc;");
		query.addScalar("name", StringType.INSTANCE);
		query.addScalar("count", IntegerType.INSTANCE);
		query.setResultTransformer(Transformers.aliasToBean(Stat.class));
		return query.list();
	}

	/**
	 * Retourne le nombre de podiums réalisés par pilote
	 * 
	 * @return Le nombre de podiums réalisés par pilote
	 */
	public List<Stat> findNbPodium() {
		Session session = this.em.unwrap(org.hibernate.Session.class);
		SQLQuery query = session.createSQLQuery(
				"select pilotes.nom as name, sum(!isnull(resultats.place)) as count from pilotes left join resultats on pilotes.id = resultats.idPilote and resultats.place >=1 and resultats.place <=3 group by pilotes.nom order by count desc;");
		query.addScalar("name", StringType.INSTANCE);
		query.addScalar("count", IntegerType.INSTANCE);
		query.setResultTransformer(Transformers.aliasToBean(Stat.class));
		return query.list();
	}
}
