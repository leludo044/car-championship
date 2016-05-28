package net.leludo.gtrchamp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import net.leludo.gtrchamp.stat.Stat;

public class StatDao {

    private EntityManagerFactory emf;
    protected EntityManager em;

    public void setEntityManager(final EntityManagerFactory emf) {
        this.emf = emf;
        em = emf.createEntityManager();
    }

    public void close() {
        if (em != null) {
            em.close();
        }
    }

    public List<Stat> findNbPoles() {
        Session session = this.em.unwrap(org.hibernate.Session.class);
        Query query = session.getNamedQuery("poleCount") ;
        query.setResultTransformer(Transformers.aliasToBean(Stat.class));
        return query.list();
    }

    public List<Stat> findNbPoles(int id) {
        Session session = this.em.unwrap(org.hibernate.Session.class);
        Query query = session.getNamedQuery("filteredPoleCount") ;
        query.setInteger("id", id);
        query.setResultTransformer(Transformers.aliasToBean(Stat.class));
        return query.list();
    }

    public List<Stat> findNbPodium() {
        Session session = this.em.unwrap(org.hibernate.Session.class);
        Query query = session.getNamedQuery("podiumCount") ;
        query.setResultTransformer(Transformers.aliasToBean(Stat.class));
        return query.list();
    }

    public List<Stat> findNbPodium(int id) {
        Session session = this.em.unwrap(org.hibernate.Session.class);
        Query query = session.getNamedQuery("filteredPodiumCount") ;
        query.setInteger("id", id);
        query.setResultTransformer(Transformers.aliasToBean(Stat.class));
        return query.list();
    }

    public List<Stat> findNbVictories() {
        Session session = this.em.unwrap(org.hibernate.Session.class);
        Query query = session.getNamedQuery("victoryCount") ;
        query.setResultTransformer(Transformers.aliasToBean(Stat.class));
        return query.list();
    }

    public List<Stat> findNbVictories(int id) {
        Session session = this.em.unwrap(org.hibernate.Session.class);
        Query query = session.getNamedQuery("filteredVictoryCount") ;
        query.setInteger("id", id);
        query.setResultTransformer(Transformers.aliasToBean(Stat.class));
        return query.list();
    }
}
