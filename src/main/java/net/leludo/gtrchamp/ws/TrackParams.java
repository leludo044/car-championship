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
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the nom to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the length
     */
    public Float getLength() {
        return length;
    }

    /**
     * @param length
     *            the length to set
     */
    public void setLength(final Float length) {
        this.length = length;
    }

    /**
     * @return the id of the country
     */
    public String getCountryId() {
        return countryId;
    }

    /**
     * @param countryId
     *            the id of the country to set
     */
    public void setCountryId(final String countryId) {
        this.countryId = countryId;
    }

}
