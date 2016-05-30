package net.leludo.gtrchamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pays")
public class Country {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "nom")
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
     * @return the id of the country
     */
    public int getId() {
        return id;
    }

    /**
     * @return the name of the country
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the country.
     *
     * @param name
     *            The name of the country
     */
    public void setName(final String name) {
        this.name = name;
    }
}
