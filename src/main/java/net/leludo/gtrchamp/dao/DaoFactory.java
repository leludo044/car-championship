package net.leludo.gtrchamp.dao;

import javax.persistence.EntityManagerFactory;

/**
 * Factory for DAO creation.
 */
public final class DaoFactory {

    /** Unique instance of the factory. */
    private static DaoFactory instance = null;

    /** EntotyManagerFactory used by all the DAO. */
    private EntityManagerFactory emf = null;

    /**
     * Constructor. Private to avoid the factory creations.
     */
    private DaoFactory() {
    }

    /**
     * Provide the unique factory instance.
     *
     * @return The factory
     */
    public static DaoFactory getInstance() {
        if (instance == null) {
            instance = new DaoFactory();
        }
        return instance;
    }

    /**
     * Set the JPA entity manager to use.
     *
     * @param entityManagerFactory
     *            The entity manager to use
     */
    public void setEntityManagerfactory(final EntityManagerFactory entityManagerFactory) {
        this.emf = entityManagerFactory;
    }

    /**
     * Get the entity manager factory in use.
     *
     * @return the entity manager factory in use.
     */
    protected EntityManagerFactory entityManagerFactory() {
        return this.emf;
    }

    /**
     * Create a championship DAO.
     *
     * @return a new championship DAO
     */
    public static ChampionshipDao championshipDao() {
        return new ChampionshipDao();
    }

    /**
     * Create a country DAO.
     *
     * @return a new country DAO
     */
    public static CountryDao countryDao() {
        return new CountryDao();
    }

    /**
     * Create a track DAO.
     *
     * @return a new track DAO
     */
    public static TrackDao trackDao() {
        return new TrackDao();
    }

    /**
     * Create a driver DAO.
     *
     * @return a new driver DAO
     */
    public static DriverDao driverDao() {
        return new DriverDao();
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
     * @return a new socring system DAO
     */
    public static ScoringDao scoringDao() {
        return new ScoringDao();
    }

}
