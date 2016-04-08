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
     * @return the championship id
     */
    public Integer getChampionshipId() {
        return championshipId;
    }

    /**
     * @param id
     *            the championship id to set
     */
    public void setChampionshipId(final Integer id) {
        this.championshipId = id;
    }

    /**
     * @return the track id
     */
    public Integer getTrackId() {
        return trackId;
    }

    /**
     * @param id
     *            the championship id to set
     */
    public void setTrackId(final Integer id) {
        this.trackId = id;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param birthdate
     *            the date to set
     */
    public void setDate(final String date) {
        this.date = date;
    }
}
