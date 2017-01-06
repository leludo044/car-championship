package net.leludo.gtrchamp.ws;

/**
 * Wrapper for driver request parameters.
 *
 * @author Ludovic THOMAS
 *
 */
public class TrackParams {
    private Integer id;
    private String name;
    private Float length;
    private String countryId;

    /**
     * Return the track id parameter.
     *
     * @return The track id parameter
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set the track id parameter.
     *
     * @param id
     *            the id to set
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Return the track name parameter.
     *
     * @return The track name parameter
     */
    public String getName() {
        return name;
    }

    /**
     * Set the track name parameter.
     *
     * @param name
     *            the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Return the track length parameter.
     *
     * @return The track length parameter
     */
    public Float getLength() {
        return length;
    }

    /**
     * Set the track length parameter.
     *
     * @param length
     *            the length to set
     */
    public void setLength(final Float length) {
        this.length = length;
    }

    /**
     * Return the country id parameter of the track.
     *
     * @return The country id parameter of the track
     */
    public String getCountryId() {
        return countryId;
    }

    /**
     * Set the country id parameter of the track.
     *
     * @param countryId
     *            the id of the country to set
     */
    public void setCountryId(final String countryId) {
        this.countryId = countryId;
    }

}
