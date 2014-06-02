package net.leludo.gtrchamp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import net.leludo.gtrchamp.stat.Stat;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

public class StatDao {

	private EntityManagerFactory emf;
	protected EntityManager em ;
	
	public List findNbVictories() {
		Session session = this.em.unwrap(org.hibernate.Session.class);
		SQLQuery query = session.createSQLQuery("select pilotes.nom, sum(!isnull(resultats.place)) as winner from pilotes left join resultats on pilotes.id = resultats.idPilote and resultats.place=1 group by pilotes.nom order by winner desc;");
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
}
