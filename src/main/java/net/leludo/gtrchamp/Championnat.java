package net.leludo.gtrchamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "championnats")
public class Championnat {
    @Id
    @GeneratedValue
    private int id;

    private String libelle;
    
    private String type ;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "championnat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GrandPrix> grandsPrix = new ArrayList<GrandPrix>();

    public GrandPrix organiserGrandPrix(final net.leludo.gtrchamp.Circuit circuit,
            final Date date) {
        GrandPrix gp = new GrandPrix(this, circuit, date);
        this.grandsPrix.add(gp);
        return gp;
    }

    public List<net.leludo.gtrchamp.Pilote> rendreClassement() {
        // TODO Auto-generated return
        return null;
    }

    public Championnat(final String pLibelle, final String pType) {
        this.libelle = pLibelle;
        this.type = pType;
        this.grandsPrix = new ArrayList<GrandPrix>();
    }

    public List<net.leludo.gtrchamp.GrandPrix> getGrandsPrix() {
        return this.grandsPrix;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public void setLibelle(final String libelle) {
        this.libelle = libelle;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    public Championnat() {
        this.libelle = "Choisir...";
        this.grandsPrix = new ArrayList<GrandPrix>();
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Championnat [id=" + id + ", libelle=" + libelle + ", grandsPrix=" + grandsPrix
                + "]";
    }

    public GrandPrix supprimerGrandPrix(final Integer idGrandPrix) {
        GrandPrix toDelete = null;
        for (GrandPrix race : this.grandsPrix) {
            if (race.getCircuit().getId() == idGrandPrix) {
                toDelete = race;
                this.grandsPrix.remove(toDelete);
                //toDelete.annuler();
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

}
