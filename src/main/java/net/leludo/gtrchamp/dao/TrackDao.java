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
        javax.persistence.Query query = this.getSession().createQuery(queryString);
        this.getSession().clear();
        return query.getResultList();
    }

    /**
     * Create a new track.
     *
     * @param track
     *            The track to create
     */
    public void create(final Track track) {
        this.getSession().getTransaction().begin();
        this.getSession().persist(track);
        this.getSession().getTransaction().commit();
        this.getSession().clear();
    }

    /**
     * Update an existing track.
     *
     * @param track
     *            The track to update
     */
    public void update(final Track track) {
        this.getSession().getTransaction().begin();
        this.getSession().merge(track);
        this.getSession().getTransaction().commit();
        this.getSession().clear();
    }

    /**
     * Delete a track
     *
     * @param track
     *            The track to delete
     */
    public void delete(final Track track) {
        this.getSession().getTransaction().begin();
        this.getSession().remove(track);
        this.getSession().getTransaction().commit();
        this.getSession().clear();
    }

    /**
     * Say if the track has been run
     *
     * @param trackId
     *            The id of the track to ask
     * @return true ou false
     */
    public boolean wasRun(final int trackId) {
        this.getSession().getTransaction().begin();
        Query query = this.getSession()
                .createQuery("select count(*) from Competitor where race.track.id=:id");
        query.setParameter("id", trackId);
        Long raceCount = (Long) query.getSingleResult();
        this.getSession().getTransaction().commit();

        return raceCount > 0;
    }
}
