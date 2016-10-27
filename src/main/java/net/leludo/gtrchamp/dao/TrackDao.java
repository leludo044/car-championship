package net.leludo.gtrchamp.dao;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import net.leludo.gtrchamp.Track;

@Singleton
public class TrackDao extends DefaultDao<Track, Integer> {

    /**
     * Constructor.
     *
     * @param entityManager
     *            The JPA entity manager affected to this DAO
     */
    public TrackDao(final EntityManager entityManager) {
        super(Track.class);
        super.entityManager(entityManager);
    }

    /**
     * @return all the tracks
     */
    public List<Track> findAll() {
        EntityManager session = this.getSession();
        String queryString = "from Track order by name";
        javax.persistence.Query query = session.createQuery(queryString);
        return query.getResultList();
    }

    /**
     * Create a new track.
     *
     * @param track
     *            The track to create
     */
    public void create(final Track track) {
        EntityManager session = this.getSession();
        session.getTransaction().begin();
        session.persist(track);
        session.getTransaction().commit();
    }

    /**
     * Update an existing track.
     *
     * @param track
     *            The track to update
     */
    public void update(final Track track) {
        EntityManager session = this.getSession();
        session.getTransaction().begin();
        session.merge(track);
        session.getTransaction().commit();
    }

    /**
     * Delete a track.
     *
     * @param track
     *            The track to delete
     */
    public void delete(final Track track) {
        EntityManager session = this.getSession();
        session.getTransaction().begin();
        session.remove(track);
        session.getTransaction().commit();
    }

    /**
     * Say if the track has been run.
     *
     * @param trackId
     *            The id of the track to ask
     * @return true ou false
     */
    public boolean wasRun(final int trackId) {
        EntityManager session = this.getSession();
        Query query = session
                .createQuery("select count(*) from Competitor where race.track.id=:id");
        query.setParameter("id", trackId);
        Long raceCount = (Long) query.getSingleResult();

        return raceCount > 0;
    }
}
