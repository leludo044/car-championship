package net.leludo.gtrchamp.ws;

/**
 * Wrapper for driver request parameters.
 */
public class DriverParams {
    private Integer id;
    private String name;
    private String birthdate;

    /**
     * Return the driver id parameter.
     *
     * @return The Driver id parameter
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set the driver id parameter.
     *
     * @param id
     *            the id to set
     */
    public void setId(final Integer id) {
        this.id = id;
    }

    /**
     * Return the driver name parameter.
     *
     * @return The driver name parameter
     */
    public String getName() {
        return name;
    }

    /**
     * Set the driver name parameter.
     *
     * @param name
     *            the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Return the birth date of the driver.
     *
     * @return The birth date of the driver
     */
    public String getBirthdate() {
        return birthdate;
    }

    /**
     * Set the birth date of the driver.
     *
     * @param birthdate
     *            the birth date to set
     */
    public void setBirthdate(final String birthdate) {
        this.birthdate = birthdate;
    }
}
