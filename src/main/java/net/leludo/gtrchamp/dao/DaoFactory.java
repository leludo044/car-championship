package net.leludo.gtrchamp.dao;

import javax.persistence.EntityManager;

/**
 * Factory for DAO creation.
 */
public final class DaoFactory {

    /** EntotyManagerFactory used by all the DAO. */
    private EntityManager em = null;

    /**
     * Constructor.
     * @param entityManager The entity manager to use for all the DAO instantiated by the factory.
     */
    public DaoFactory(final EntityManager entityManager) {
        this.em = entityManager;
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
    public RaceDao raceDao() {
        return new RaceDao(this.em);
    }

    /**
     * Create a result DAO.
     *
     * @return a new result DAO
     */
    public ResultDao resultDao() {
        return new ResultDao(this.em);
    }

    /**
     * Create a scoring system DAO.
     *
     * @return a new scoring system DAO
     */
    public ScoringDao scoringDao() {
        return new ScoringDao(this.em);
    }

    /**
     * Close the entity manager.
     */
    public void close() {
        this.em.close();
    }
}
