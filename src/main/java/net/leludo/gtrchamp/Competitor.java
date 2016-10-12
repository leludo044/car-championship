package net.leludo.gtrchamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Represents à driver ans his results about a race
 */
@Entity
@Table(name = "resultats")
public class Competitor {

    /**
     * Default constructor.
     */
    public Competitor() {
        super();
        this.id = new CompetitorId();
        this.startingPosition = 0;
        this.arrivalPosition = 0;
    }

    /**
     * Constructor from a known driver.
     *
     * @param driver
     *            The driver
     * @throws ChampionshipException
     *             Raised exception if the driver is null
     */
    public Competitor(final Driver driver) throws ChampionshipException {
        this();
        this.setDriver(driver);
    }

    /** Id of the competitor. */
    @EmbeddedId
    private CompetitorId id;

    /** The driver. */
    @MapsId("driverId")
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "idPilote", nullable = false)
    private Driver driver;

    /** The race the driver ran. */
    @MapsId("raceId")
    @OneToOne
    @JoinColumn(name = "idGrandPrix", nullable = false)
    private Race race;

    /** The starting position of the driver and the race. */
    @Column(name = "grille", nullable = false)
    private int startingPosition;

    /** The arrival position of the driver and the race. */
    @Column(name = "place", nullable = false)
    private int arrivalPosition;

    /** The earned points. */
    // TODO Supprimer ou configurer avec une clé primaire composée
    @ManyToOne()
    @JoinColumn(name = "place", insertable = false, updatable = false)
    private PointSet points;

    /**
     * Say if the drive won the pole position.
     *
     * @return true or false
     */
    public boolean hasPolePosition() {
        return this.startingPosition == 1;
    }

    /**
     * Say if the drive won the race.
     *
     * @return true or false
     */
    public boolean hasWon() {
        return this.arrivalPosition == 1;
    }

    /**
     * @return the id
     */
    protected CompetitorId getId() {
        return id;
    }

    /**
     * Set the driver.
     *
     * @param driver
     *            The driver to set. Cannot be null
     * @throws ChampionshipException
     *             Raised exception if the driver is null
     */
    public void setDriver(final Driver driver) throws ChampionshipException {
        if (driver != null) {
            this.driver = driver;
            this.id.setDriverId(this.driver.getId());
        } else {
            throw new ChampionshipException();
        }
    }

    /**
     * Return the driver.
     *
     * @return The driver
     */
    public Driver getDriver() {
        return driver;
    }

    /**
     * Return the race.
     *
     * @return the race
     */
    public Race getRace() {
        return race;
    }

    /**
     * Set the race.
     *
     * @param race
     *            The race to set. Cannot be null
     * @throws ChampionshipException
     *             Raised exception if the race is null
     */
    public void setRace(final Race race) throws ChampionshipException {
        if (race != null) {
            this.race = race;
            this.id.setRaceId(this.race.getId());
        } else {
            throw new ChampionshipException();
        }
    }

    /**
     * Set the starting position.
     *
     * @param startingPosition
     *            The starting position to set. Must be above 0
     * @throws ChampionshipException
     *             Raised exception if the starting position is equal or under 0
     */
    public void setStartingPosition(final int startingPosition) throws ChampionshipException {
        if (startingPosition > 0) {
            this.startingPosition = startingPosition;
        } else {
            throw new ChampionshipException();
        }
    }

    /**
     * Set the arrival position.
     *
     * @param arrivalPosition
     *            The arrival position to set. Must be above 0
     * @throws ChampionshipException
     *             Raised exception if the arrival position is equal or under 0
     */
    public void setArrivalPosition(final int arrivalPosition) throws ChampionshipException {
        if (arrivalPosition > 0) {
            this.arrivalPosition = arrivalPosition;
        } else {
            throw new ChampionshipException();
        }
    }

    /**
     * Return the starting position.
     *
     * @return The starting position
     */
    public int getStartingPosition() {
        return startingPosition;
    }

    /**
     * Return the arrival position.
     *
     * @return The arrival position
     */
    public int getArrivalPosition() {
        return arrivalPosition;
    }

    /**
     * Get the race number.
     *
     * @return The race number
     */
    public int getRaceNumber() {
        return this.id.getRaceNumber();
    }

    /**
     * Set the race number.
     *
     * @param raceNumber
     *            The race number to set. Must be 1 or 2
     * @throws ChampionshipException
     *             Raised exception if the race number is not 1 and not 2
     */
    public void setRaceNumber(final int raceNumber) throws ChampionshipException {
        if (raceNumber == 1 || raceNumber == 2) {
            this.id.setRaceNumber(raceNumber);
        } else {
            throw new ChampionshipException();
        }
    }

    /**
     * Return the earned points.
     *
     * @return The earned points
     */
    public PointSet getPoints() {
        return points;
    }

    /**
     * Say that the driver has aborted the race.
     */
    public void canceled() {
        this.arrivalPosition = -1;
    }

    /**
     * Say if the driver has finished the race.
     *
     * @return true or false
     */
    public boolean hasFinished() {
        return this.arrivalPosition != 0;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.getRaceNumber();
        result = prime * result + ((driver == null) ? 0 : driver.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Competitor other = (Competitor) obj;
        if (this.getRaceNumber() != other.getRaceNumber()) {
            return false;
        }
        if (driver == null) {
            if (other.driver != null) {
                return false;
            }
        } else if (!driver.equals(other.driver)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Competitor [id=");
        builder.append(id);
        builder.append(", driver=");
        builder.append(driver);
        builder.append(", race=");
        builder.append(race);
        builder.append(", startingPosition=");
        builder.append(startingPosition);
        builder.append(", arrivalPosition=");
        builder.append(arrivalPosition);
        builder.append(", raceNumber=");
        builder.append(this.getRaceNumber());
        builder.append("]");
        return builder.toString();
    }

}
