package net.leludo.gtrchamp.dao;

import javax.persistence.EntityManagerFactory;

public class DaoManager {

    private static DaoManager instance = null ;
    EntityManagerFactory emf = null ;
    
    private DaoManager() {
    }
    
    public static final DaoManager getInstance() {
        if (instance == null) {
            instance = new DaoManager() ;
        }
        return instance ;
    }

    public void setEntityManagerfactory(final EntityManagerFactory emf) {
        this.emf = emf ;
    }

    public EntityManagerFactory entityManagerFactory() { 
        return this.emf ;
    }
}
