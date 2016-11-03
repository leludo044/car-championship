package net.leludo.gtrchamp.ws;

/**
 * Wrapper for championship request parameters.
 *
 * @author Ludovic THOMAS
 *
 */
public class ChampionshipParams {
    private Integer id;
    private String name;
    private String type;
    private int mode ;

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

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * @return the mode
     */
    protected int getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    protected void setMode(Integer mode) {
        this.mode = mode;
    }
}
