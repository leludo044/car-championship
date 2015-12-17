package net.leludo.gtrchamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "grandsprix")
public class GrandPrix {

    @Id
    private int id;

    private Date date;

    private boolean mode2Courses;

    @Transient
    private List<Concurrent> concurrents = new ArrayList<Concurrent>();

    @OneToOne
    @JoinColumn(name = "idCircuit", nullable = false)
    private Circuit circuit;

    @ManyToOne
    @JoinColumn(name = "idChampionnat", nullable = false)
    private Championnat championnat;

    public Concurrent inscrire(final Pilote pilote) throws ChampionnatException {
        if (pilote == null) {
            throw new ChampionnatException();
        }
        Concurrent concurrent = new Concurrent(pilote);
        this.concurrents.add(concurrent);
        return concurrent;
    }

    public List<Concurrent> rendreClassement() throws ChampionnatException {
        if (this.concurrents.size() == 0) {
            throw new ChampionnatException();
        } else if (!this.isTermine()) {
            throw new ChampionnatException();
        }

        Collections.sort(concurrents, new Comparator<Concurrent>() {

            @Override
            public int compare(final Concurrent o1, final Concurrent o2) {
                if (o1.getPositionArrivee() < o2.getPositionArrivee()) {
                    return -1;
                } else {
                    return 0;
                }
            }

        });

        return concurrents;
    }

    public GrandPrix(final Championnat championnat, final Circuit circuit, final Date date) {
        this.championnat = championnat;
        this.circuit = circuit;
        this.date = date;
        this.mode2Courses = true ;
    }

    public GrandPrix() {

    }

    public int getId() {
        return id;
    }

    public Circuit getCircuit() {
        return this.circuit;
    }

    public Object getNbInscrits() {
        return this.concurrents.size();
    }

    public boolean isTermine() {
        boolean isTermine = true;
        for (Concurrent concurrent : this.concurrents) {
            isTermine &= concurrent.hasTermine();
        }
        return isTermine;
    }

    @Override
    public String toString() {
        return "GrandPrix [id=" + id + ", date=" + date + ", mode2Courses=" + mode2Courses
                + ", concurrents=" + concurrents + ", circuit=" + circuit + "]";
    }

    public Date getDate() {
        return date;
    }

    public String getDateFr() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(this.date);
    }

}
