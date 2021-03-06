package net.leludo.gtrchamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Championship business object.
 */
@Entity
@Table(name = "championship")
public class Championship {
    /** Unique id of the championship. */
    @Id
    @GeneratedValue
    private int id;

    /** Name/label. */
    private String name;

    /** Type (gtr or wtcc). */
    private String type;

    /** Race mode. */
    private int mode;

    /** List of scheduled races. */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "championship", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Race> plannedRaces = new ArrayList<>();

    /**
     * Constructor. By default the name is "Choose..." and the scheduled races
     * list is empty
     */
    public Championship() {
        this.name = "Choose...";
        this.plannedRaces = new ArrayList<>();
    }

    /**
     * Constructor.
     *
     * @param name
     *            The name of the championship
     * @param type
     *            The type of the championship (gtr, wtcc, etc)
     * @param mode
     *            The mode race (1 race or 2 races)
     */
    public Championship(final String name, final String type, final int mode) {
        this.name = name;
        this.type = type;
        this.mode = mode;
        this.plannedRaces = new ArrayList<>();
    }

    /**
     * Scedule a race.
     *
     * @param circuit
     *            The track to schedule
     * @param date
     *            The date the track will be run
     * @return The planned race
     */
    public Race planRace(final Track circuit, final LocalDate date) {
        Race gp = new Race(this, circuit, date);
        this.plannedRaces.add(gp);
        return gp;
    }

    /**
     * Return the list of the scheduled races.
     *
     * @return the scheduled races
     */
    public List<Race> getPlannedRaces() {
        return this.plannedRaces;
    }

    /**
     * Return the name of the championship.
     *
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
     * Return the type of the championship. Two types are available : "gtr" or
     * "wtcc"
     *
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
     * Return the id of the championship.
     *
     * @return the id of the championship
     */
    public int getId() {
        return id;
    }

    /**
     * Return the race mode : 1 race or 2 races.
     *
     * @return The race mode
     */
    public int getMode() {
        return mode;
    }

    /**
     * Set the race mode.
     *
     * @param mode
     *            The new mode to set
     */
    public void setMode(final int mode) {
        this.mode = mode;
    }

    /**
     * Cancel a scheduled race.
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

    /**
     * Return the last scheduled race.
     *
     * @return the last scheduled race
     */
    public Race lastRace() {
        return this.plannedRaces.get(this.plannedRaces.size() - 1);
    }

    @Override
    public String toString() {
        return "Cahmpionship [id=" + id + ", name=" + name + ", type=" + type + ", mode=" + mode
                + ", plannedRaces=" + plannedRaces + "]";
    }

}
