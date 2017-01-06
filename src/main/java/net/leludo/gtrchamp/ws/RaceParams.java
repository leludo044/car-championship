package net.leludo.gtrchamp.ws;

/**
 * Wrapper for race request parameters.
 *
 * @author Ludovic THOMAS
 *
 */
public class RaceParams {
    private Integer championshipId;
    private Integer trackId;
    private String date;

    /**
     * Return the championship id of the race parameter.
     *
     * @return The championship id of the race parameter
     */
    public Integer getChampionshipId() {
        return championshipId;
    }

    /**
     * Set the championship id of the race parameter.
     *
     * @param id
     *            the championship id to set
     */
    public void setChampionshipId(final Integer id) {
        this.championshipId = id;
    }

    /**
     * Return the track id of the race parameter.
     *
     * @return The track id of the race parameter
     */
    public Integer getTrackId() {
        return trackId;
    }

    /**
     * Set the track id of the race parameter.
     *
     * @param id
     *            the track id to set
     */
    public void setTrackId(final Integer id) {
        this.trackId = id;
    }

    /**
     * Return the date parameter of the race.
     *
     * @return the date parameter of the race
     */
    public String getDate() {
        return date;
    }

    /**
     * Set the date parameter of the race.
     *
     * @param date
     *            the date to set
     */
    public void setDate(final String date) {
        this.date = date;
    }
}
