package net.leludo.gtrchamp.dao;

import javax.persistence.EntityManager;

/**
 * Factory for DAO creation.
 */
public final class DaoManager {

    /** EntotyManagerFactory used by all the DAO. */
    private EntityManager em = null;

    /**
     * Constructor. Private to avoid the factory creations.
     */
    public DaoManager(final EntityManager em) {
        this.em = em;
    }

    /**
     * Create a championship DAO.
     *
     * @return a new championship DAO
     */
    public ChampionshipDao championshipDao() {
        return new ChampionshipDao(this.em);
    }

    /**
     * Create a country DAO.
     *
     * @return a new country DAO
     */
    public CountryDao countryDao() {
        return new CountryDao(this.em);
    }

    /**
     * Create a track DAO.
     *
     * @return a new track DAO
     */
    public TrackDao trackDao() {
        return new TrackDao(this.em);
    }

    /**
     * Create a driver DAO.
     *
     * @return a new driver DAO
     */
    public DriverDao driverDao() {
        return new DriverDao(this.em);
    }

    /**
     * Create a race DAO.
     *
     * @return a new race DAO
     */
    public static RaceDao raceDao() {
        return new RaceDao();
    }

    /**
     * Create a result DAO.
     *
     * @return a new result DAO
     */
    public static ResultDao resultDao() {
        return new ResultDao();
    }

    /**
     * Create a scoring system DAO.
     *
     * @return a new scoring system DAO
     */
    public static ScoringDao scoringDao() {
        return new ScoringDao();
    }

    public final EntityManager getEntityManager() {
        return this.em;
    }

    /**
     * Close the entity manager.
     */
    public void close() {
        this.em.close();
    }
}
