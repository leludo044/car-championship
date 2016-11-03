package net.leludo.gtrchamp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.leludo.gtrchamp.dao.converter.LocalDateConverter;

@Entity
@Table(name = "grandsprix")
public class Race {

    @Id
    @GeneratedValue
    private int id;

	@Convert(converter = LocalDateConverter.class)
    private LocalDate date;

    @Transient
    private List<Competitor> competitors = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idCircuit", nullable = false)
    private Track track;

    @OneToOne
    @JoinColumn(name = "idChampionnat", nullable = false)
    private Championship championship;

    public Competitor signUp(final Driver driver) throws ChampionshipException {
        if (driver == null) {
            throw new ChampionshipException();
        }
        Competitor competitor = new Competitor(driver);
        this.competitors.add(competitor);
        return competitor;
    }

    public List<Competitor> results() throws ChampionshipException {
        if (this.competitors.size() == 0) {
            throw new ChampionshipException();
        } else if (!this.isFinished()) {
            throw new ChampionshipException();
        }

        Collections.sort(competitors, new Comparator<Competitor>() {

            @Override
            public int compare(final Competitor o1, final Competitor o2) {
                if (o1.getArrivalPosition() < o2.getArrivalPosition()) {
                    return -1;
                } else {
                    return 0;
                }
            }

        });

        return competitors;
    }

    public Race(final Championship championship, final Track track, final LocalDate date) {
        this.championship = championship;
        this.track = track;
        this.date = date;
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
        for (Competitor competitor : this.competitors) {
            isTermine &= competitor.hasFinished();
        }
        return isTermine;
    }

    @Override
    public String toString() {
        return "Race [id=" + id + ", date=" + date + ", competitors=" + competitors + ", track="
                + track + "]";
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDateFr() {
        String formatedDate = "";
        if (this.date != null) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            formatedDate = this.date.format(dtf);
        }
        return formatedDate;
    }

    public void cancel() {
        this.championship = null;
    }
}
