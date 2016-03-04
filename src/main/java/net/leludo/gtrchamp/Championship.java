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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "championnat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GrandPrix> plannedRaces = new ArrayList<GrandPrix>();

    /**
     * Constructor. By default the name is "Choose..." and the planned races
     * list is empty
     *
     */
    public Championship() {
        this.name = "Choose...";
        this.plannedRaces = new ArrayList<GrandPrix>();
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
        this.plannedRaces = new ArrayList<GrandPrix>();
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
    public GrandPrix planRace(final Circuit circuit, final Date date) {
        GrandPrix gp = new GrandPrix(this, circuit, date);
        this.plannedRaces.add(gp);
        return gp;
    }

    /**
     * @return the already planned races
     */
    public List<GrandPrix> getPlannedRaces() {
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
     * @param idGrandPrix
     *            The id of the race to cancel
     * @return the cancelled race
     */
    public GrandPrix cancelRace(final Integer idGrandPrix) {
        GrandPrix toDelete = null;
        for (GrandPrix race : this.plannedRaces) {
            if (race.getCircuit().getId() == idGrandPrix) {
                toDelete = race;
                this.plannedRaces.remove(toDelete);
                // toDelete.annuler();
                break;
            }
        }
        // Stream<GrandPrix> gp =
        // this.grandsPrix.stream().map(GrandPrix::getCircuit).filter(circuit ->
        // circuit.getId().equals(idGrandPrix));
        // if (gp.count() == 1) {
        // toDelete = gp.findFirst().get();
        // this.grandsPrix.remove(toDelete);
        // }

        return toDelete;
    }

    @Override
    public String toString() {
        return "Championnat [id=" + id + ", libelle=" + name + ", grandsPrix=" + plannedRaces + "]";
    }

}
