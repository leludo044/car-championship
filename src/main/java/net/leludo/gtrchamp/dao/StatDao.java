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

public class StatDao {

	private EntityManagerFactory emf;
	protected EntityManager em ;
	
	public List<Stat> findNbVictories() {
		Session session = this.em.unwrap(org.hibernate.Session.class);
		SQLQuery query = session.createSQLQuery("select pilotes.nom as name, sum(!isnull(resultats.place)) as count from pilotes left join resultats on pilotes.id = resultats.idPilote and resultats.place=1 group by pilotes.nom order by count desc;");
		query.addScalar("name", StringType.INSTANCE);
		query.addScalar("count", IntegerType.INSTANCE);
		query.setResultTransformer(Transformers.aliasToBean(Stat.class)) ;
		return query.list() ;
	}
	
	public void setEntityManager(EntityManagerFactory emf) {
		this.emf =emf ;
		em = emf.createEntityManager();
	}
	public void close() {
		if (em != null) {
			em.close();
		}
	}

	public List<Stat> findNbPoles() {
		Session session = this.em.unwrap(org.hibernate.Session.class);
		SQLQuery query = session.createSQLQuery("select pilotes.nom as name, sum(!isnull(resultats.place)) as count from pilotes left join resultats on pilotes.id = resultats.idPilote and resultats.grille=1 group by pilotes.nom order by count desc;");
		query.addScalar("name", StringType.INSTANCE);
		query.addScalar("count", IntegerType.INSTANCE);
		query.setResultTransformer(Transformers.aliasToBean(Stat.class)) ;
		return query.list() ;
	}
	
	public List<Stat> findNbPodium() {
		Session session = this.em.unwrap(org.hibernate.Session.class);
		SQLQuery query = session.createSQLQuery("select pilotes.nom as name, sum(!isnull(resultats.place)) as count from pilotes left join resultats on pilotes.id = resultats.idPilote and resultats.place >=1 and resultats.place <=3 group by pilotes.nom order by count desc;");
		query.addScalar("name", StringType.INSTANCE);
		query.addScalar("count", IntegerType.INSTANCE);
		query.setResultTransformer(Transformers.aliasToBean(Stat.class)) ;
		return query.list() ;		
	}
}
