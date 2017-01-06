package net.leludo.gtrchamp.ws;

/**
 * Wrapper for championship request parameters.
 */
public class ChampionshipParams {
    /** Championship id parameter. */
    private Integer id;

    /** Championship name parameter. */
    private String name;

    /** Championship type parameter. */
    private String type;

    /** Championship mode parameter. */
    private int mode;

    /**
     * Return the championship id parameter.
     *
     * @return the championship id parameter
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set the championship id parameter.
     *
     * @param id
     *            the id to set
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Return the championship name parameter.
     *
     * @return the championship name parameter
     */
    public String getName() {
        return name;
    }

    /**
     * Set the championship name parameter.
     *
     * @param name
     *            the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Return the championship type parameter.
     *
     * @return the championship type parameter
     */
    public String getType() {
        return type;
    }

    /**
     * Set the championship type parameter.
     *
     * @param type
     *            the type to set
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * Return the championship mode parameter.
     *
     * @return the championship mode parameter
     */
    protected int getMode() {
        return mode;
    }

    /**
     * Set the championship mode parameter.
     *
     * @param mode
     *            the mode to set
     */
    protected void setMode(final Integer mode) {
        this.mode = mode;
    }
}
