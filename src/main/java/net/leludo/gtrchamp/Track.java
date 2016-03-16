package net.leludo.gtrchamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "circuits")
public class Track {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "nom")
    private String name;

    @Column(name = "longueur")
    private Float length;

    @OneToOne
    @JoinColumn(name = "idPays", nullable = false)
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
     *            The country where the track take place
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
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the name of the track
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
     * @return the length of the track in kilometer unit
     */
    public Float getLength() {
        return length;
    }

    /**
     * @param length
     *            the longueur to set
     */
    public void setLength(final Float length) {
        this.length = length;
    }

    /**
     * @return the country where the track take place
     */
    public Country getCountry() {
        return country;
    }

    /**
     * @param country
     *            the pays to set
     */
    public void setCountry(final Country country) {
        this.country = country;
    }

}
