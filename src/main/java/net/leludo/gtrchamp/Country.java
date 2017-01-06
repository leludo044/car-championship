package net.leludo.gtrchamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Country. A country is where a race take place.
 */
@Entity
@Table(name = "country")
public class Country {
    /** Unique id of the country. */
    @Id
    @GeneratedValue
    private int id;

    /** Country name. */
    private String name;

    /**
     * Constructor.
     */
    public Country() {
        this.name = "";
    }

    /**
     * Constructor.
     *
     * @param name
     *            The name of the country
     */
    public Country(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Country [id=" + id + ", name=" + name + "]";
    }

    /**
     * Return the id of the country.
     *
     * @return the id of the country
     */
    public int getId() {
        return id;
    }

    /**
     * Return the name of the country.
     *
     * @return the name of the country
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the country.
     *
     * @param name
     *            The name of the country to set
     */
    public void setName(final String name) {
        this.name = name;
    }
}
