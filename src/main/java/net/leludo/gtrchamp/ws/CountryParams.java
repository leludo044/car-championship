package net.leludo.gtrchamp.ws;

/**
 * Wrapper for country request parameters.
 */
public class CountryParams {
    /** Country id parameter. */
    private Integer id;

    /** Country name parameter. */
    private String name;

    /**
     * Return the country id parameter.
     *
     * @return the country id parameter
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set the country id parameter.
     *
     * @param id
     *            the id to set
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Return the country name parameter.
     *
     * @return the country name parameter
     */
    public String getName() {
        return name;
    }

    /**
     * Set the country name parameter.
     *
     * @param name
     *            the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }
}
