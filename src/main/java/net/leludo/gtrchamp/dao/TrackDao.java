package net.leludo.gtrchamp.dao;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.Query;

import net.leludo.gtrchamp.Track;

@Singleton
public class TrackDao extends DefaultDao<Track, Integer> {

    public TrackDao() {
        super(Track.class);
    }

    /**
     * @return all the tracks
     */
    public List<Track> findAll() {
        String queryString = "from Track order by name";
        javax.persistence.Query query = this.em.createQuery(queryString);
        this.em.clear();
        return query.getResultList();
    }

    /**
     * Create a new track.
     *
     * @param track
     *            The track to create
     */
    public void create(final Track track) {
        this.em.getTransaction().begin();
        this.em.persist(track);
        this.em.getTransaction().commit();
        this.em.clear();
    }

    /**
     * Update an existing track.
     *
     * @param track
     *            The track to update
     */
    public void update(final Track track) {
        this.em.getTransaction().begin();
        this.em.merge(track);
        this.em.getTransaction().commit();
        this.em.clear();
    }

    /**
     * Delete a track
     *
     * @param track
     *            The track to delete
     */
    public void delete(final Track track) {
        this.em.getTransaction().begin();
        this.em.remove(track);
        this.em.getTransaction().commit();
        this.em.clear();
    }

    /**
     * Say if the track has been run
     *
     * @param trackId
     *            The id of the track to ask
     * @return true ou false
     */
    public boolean wasRun(final int trackId) {
        this.em.getTransaction().begin();
        Query query = this.em
                .createQuery("select count(*) from Competitor where race.track.id=:id");
        query.setParameter("id", trackId);
        Long raceCount = (Long) query.getSingleResult();
        this.em.getTransaction().commit();

        return raceCount > 0;
    }
}
