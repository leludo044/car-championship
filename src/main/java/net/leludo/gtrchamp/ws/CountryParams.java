package net.leludo.gtrchamp.ws;

/**
 * Wrapper for country request parameters.
 *
 * @author Ludovic THOMAS
 *
 */
public class CountryParams {
    private Integer id;
    private String name;

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
     *            the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }
}
