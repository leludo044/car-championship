package net.leludo.gtrchamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "grandsprix")
public class Race {

    @Id
    @GeneratedValue
    private int id;

    private Date date;

    @Column(name = "mode2Courses")
    private boolean twoRacesMode;

    @Transient
    private List<Concurrent> competitors = new ArrayList<Concurrent>();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idCircuit", nullable = false)
    private Track track;

    @OneToOne
    @JoinColumn(name = "idChampionnat", nullable = false)
    private Championship championship;

    public Concurrent signUp(final Driver driver) throws ChampionshipException {
        if (driver == null) {
            throw new ChampionshipException();
        }
        Concurrent concurrent = new Concurrent(driver);
        this.competitors.add(concurrent);
        return concurrent;
    }

    public List<Concurrent> results() throws ChampionshipException {
        if (this.competitors.size() == 0) {
            throw new ChampionshipException();
        } else if (!this.isFinished()) {
            throw new ChampionshipException();
        }

        Collections.sort(competitors, new Comparator<Concurrent>() {

            @Override
            public int compare(final Concurrent o1, final Concurrent o2) {
                if (o1.getPositionArrivee() < o2.getPositionArrivee()) {
                    return -1;
                } else {
                    return 0;
                }
            }

        });

        return competitors;
    }

    public Race(final Championship championship, final Track track, final Date date) {
        this.championship = championship;
        this.track = track;
        this.date = date;
        this.twoRacesMode = true;
    }

    public Race() {

    }

    public int getId() {
        return id;
    }

    public Track getTrack() {
        return this.track;
    }

    public Object competitorsCount() {
        return this.competitors.size();
    }

    public boolean isFinished() {
        boolean isTermine = true;
        for (Concurrent concurrent : this.competitors) {
            isTermine &= concurrent.hasTermine();
        }
        return isTermine;
    }

    @Override
    public String toString() {
        return "Race [id=" + id + ", date=" + date + ", mode2Courses=" + twoRacesMode
                + ", concurrents=" + competitors + ", track=" + track + "]";
    }

    public Date getDate() {
        return date;
    }

    public String getDateFr() {
        String formatedDate = "";
        if (this.date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            formatedDate = sdf.format(this.date);
        }
        return formatedDate;
    }

    public void cancel() {
        this.championship = null;
    }
}
