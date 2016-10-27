package net.leludo.gtrchamp.dao;

import javax.inject.Singleton;
import javax.persistence.EntityManager;

import net.leludo.gtrchamp.Race;

/**
 * DAO for race persistence
 *
 */
@Singleton
public class RaceDao extends DefaultDao<Race, Integer> {

    /**
     * Constructor.
     *
     * @param entityManager
     *            The JPA entity manager affected to this DAO
     */
    protected RaceDao(final EntityManager entityManager) {
        super(Race.class);
        super.entityManager(entityManager);
    }

    /**
     * Return a race.
     *
     * @param id
     *            The id of the race to find
     * @return the race
     */
    public Race get(final Integer id) {
        return find(id);
    }
}
