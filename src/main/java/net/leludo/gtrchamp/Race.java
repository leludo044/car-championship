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

/**
 * Race.
 */
@Entity
@Table(name = "race")
public class Race {

    /** Unique id of the race. */
    @Id
    @GeneratedValue
    private int id;

    /** Date of the race. */
    @Convert(converter = LocalDateConverter.class)
    private LocalDate date;

    /** List of drivers participating in the race. */
    @Transient
    private List<Competitor> competitors = new ArrayList<>();

    /** Track where the race take place. */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trackId", nullable = false)
    private Track track;

    /** Championship owning this race. */
    @OneToOne
    @JoinColumn(name = "championshipId", nullable = false)
    private Championship championship;

    /**
     * Constructor.
     *
     * @param championship
     *            The championship owning this race
     * @param track
     *            The track where teh race take place
     * @param date
     *            The date of the race
     */
    public Race(final Championship championship, final Track track, final LocalDate date) {
        this.championship = championship;
        this.track = track;
        this.date = date;
    }

    /**
     * Constructor.
     */
    public Race() {

    }

    /**
     * Sign up a driver for this race.
     *
     * @param driver
     *            The driver to signup
     * @return The registered driver
     * @throws ChampionshipException
     *             Exception raised if driver is null
     */
    public Competitor signUp(final Driver driver) throws ChampionshipException {
        if (driver == null) {
            throw new ChampionshipException();
        }
        Competitor competitor = new Competitor(driver);
        this.competitors.add(competitor);
        return competitor;
    }

    /**
     * Return the list of the registered driver with their results.
     *
     * @return the list of the registered driver with their results
     * @throws ChampionshipException
     *             Exception raised if there is no registered drivers or if the
     *             race is finished
     */
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

    /**
     * Return the unique id of the race.
     *
     * @return the unique id of the race
     */
    public int getId() {
        return id;
    }

    /**
     * Return the track where the race take place.
     *
     * @return the track where the race take place
     */
    public Track getTrack() {
        return this.track;
    }

    /**
     * Return the number of registered drivers.
     *
     * @return the number of registered drivers
     */
    public Object competitorsCount() {
        return this.competitors.size();
    }

    /**
     * Say if the race id finished.
     *
     * @return true ou false
     */
    public boolean isFinished() {
        boolean isTermine = true;
        for (Competitor competitor : this.competitors) {
            isTermine &= competitor.hasFinished();
        }
        return isTermine;
    }

    /**
     * Return the date of the race.
     *
     * @return the date of the race
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Return the date in string format and in french.
     *
     * @return the date in string format and in french
     */
    public String getDateFr() {
        String formatedDate = "";
        if (this.date != null) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            formatedDate = this.date.format(dtf);
        }
        return formatedDate;
    }

    @Override
    public String toString() {
        return "Race [id=" + id + ", date=" + date + ", competitors=" + competitors + ", track="
                + track + "]";
    }
}
