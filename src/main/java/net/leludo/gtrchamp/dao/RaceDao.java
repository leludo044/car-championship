package net.leludo.gtrchamp.dao;

import javax.inject.Singleton;

import net.leludo.gtrchamp.Race;

/**
 * DAO for race persistence
 *
 */
@Singleton
public class RaceDao extends DefaultDao<Race, Integer> {

    public RaceDao() {
        super(Race.class);
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
