package net.leludo.gtrchamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "championnats")
public class Championship {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "libelle")
    private String name;

    private String type;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "championship", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Race> plannedRaces = new ArrayList<Race>();

    /**
     * Constructor. By default the name is "Choose..." and the planned races
     * list is empty
     *
     */
    public Championship() {
        this.name = "Choose...";
        this.plannedRaces = new ArrayList<Race>();
    }

    /**
     * Constructor.
     *
     * @param name
     *            The name of the championship
     * @param type
     *            The type of the championship (gtr, wtcc, etc)
     */
    public Championship(final String name, final String type) {
        this.name = name;
        this.type = type;
        this.plannedRaces = new ArrayList<Race>();
    }

    /**
     * Plan a race.
     *
     * @param circuit
     *            The track to plan
     * @param date
     *            The date the track will be run
     * @return The planned race
     */
    public Race planRace(final Track circuit, final Date date) {
        Race gp = new Race(this, circuit, date);
        this.plannedRaces.add(gp);
        return gp;
    }

    /**
     * @return the already planned races
     */
    public List<Race> getPlannedRaces() {
        return this.plannedRaces;
    }

    /**
     * @return the name of the championship
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name of the championship.
     *
     * @param name
     *            The name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the type of the championship
     */
    public String getType() {
        return type;
    }

    /**
     * Set the type of the championship.
     *
     * @param type
     *            the type to set
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * @return the id of the championship
     */
    public int getId() {
        return id;
    }

    /**
     * Cancel a planned race.
     *
     * @param raceId
     *            The id of the race to cancel
     * @return the cancelled race
     */
    public Race cancelRace(final Integer raceId) {
        Race toDelete = null;
        for (Race race : this.plannedRaces) {
            if (race.getTrack().getId() == raceId) {
                toDelete = race;
                this.plannedRaces.remove(toDelete);
                break;
            }
        }

        return toDelete;
    }

    @Override
    public String toString() {
        return "Cahmpionship [id=" + id + ", name=" + name + ", plannedRaces=" + plannedRaces + "]";
    }

}
