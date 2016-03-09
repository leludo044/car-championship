package net.leludo.gtrchamp.ws;

/**
 * Wrapper for driver request parameters.
 *
 * @author Ludovic THOMAS
 *
 */
public class DriverParams {
    private Integer id;
    private String name;
    private String birthdate;

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
     * @return the birthdate
     */
    public String getBirthdate() {
        return birthdate;
    }

    /**
     * @param birthdate
     *            the birthdate to set
     */
    public void setBirthdate(final String birthdate) {
        this.birthdate = birthdate;
    }
}
