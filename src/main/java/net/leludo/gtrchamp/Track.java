package net.leludo.gtrchamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * TRack.
 */
@Entity
@Table(name = "track")
public class Track {
    /** Unique id of the track. */
    @Id
    @GeneratedValue
    private int id;

    /** Name of the track. */
    private String name;

    /** Length of the track. */
    private Float length;

    /** Country where the track is located. */
    @OneToOne
    @JoinColumn(name = "countryId", nullable = false)
    private Country country;

    /**
     * Constructor.
     */
    public Track() {
        this.name = "";
        this.length = 0f;
        this.country = null;
    }

    /**
     * Constructor.
     *
     * @param name
     *            The name of the track
     * @param length
     *            The length (in kilometer unit) of the track
     * @param country
     *            The country where the track is located
     */
    public Track(final String name, final Float length, final Country country) {
        this.name = name;
        this.length = length;
        this.country = country;
    }

    @Override
    public String toString() {
        return "Track [id=" + id + ", name=" + name + ", length=" + length + ", country=" + country
                + "]";
    }

    /**
     * Return the unique id of the track.
     *
     * @return the unique id of the track
     */
    public int getId() {
        return id;
    }

    /**
     * Return the name of the track.
     *
     * @return the name of the track
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the track.
     *
     * @param name
     *            The name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Return the length of the track.
     *
     * @return the length of the track in kilometer unit
     */
    public Float getLength() {
        return length;
    }

    /**
     * Set the length of the track.
     *
     * @param length
     *            The length to set in kilometer unit
     */
    public void setLength(final Float length) {
        this.length = length;
    }

    /**
     * Return the country where the track is located.
     *
     * @return the country where the track is located
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Set the country where the track is located.
     *
     * @param country
     *            the country where the track is located
     */
    public void setCountry(final Country country) {
        this.country = country;
    }

}
