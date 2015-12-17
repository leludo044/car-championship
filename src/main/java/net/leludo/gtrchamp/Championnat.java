package net.leludo.gtrchamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "championnat")
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

    public Championnat(final String pLibelle) {
        this.libelle = pLibelle;
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

}
