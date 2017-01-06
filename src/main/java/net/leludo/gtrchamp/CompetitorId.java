package net.leludo.gtrchamp;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * JPA embedded id of a competitor. A competitor is a registered driver for a
 * race.
 */
@Embeddable
public class CompetitorId implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4792327019267435193L;

    /** Id of the driver. */
    private int driverId;

    /** Id of the race. */
    private int raceId;

    /** Race number. */
    private int raceNumber;

    /**
     * Constructor.
     */
    public CompetitorId() {
    }

    /**
     * Constructor.
     *
     * @param driverId
     *            Id of the driver
     * @param raceId
     *            Id of the race
     * @param raceNumber
     *            raceNumber
     */
    public CompetitorId(final int driverId, final int raceId, final int raceNumber) {
        this.driverId = driverId;
        this.raceId = raceId;
        this.raceNumber = raceNumber;
    }

    /**
     * Return the driver id as a part of the embedded JPA id.
     *
     * @return the driverId the driver id as a part of the embedded JPA id
     */
    protected int getDriverId() {
        return driverId;
    }

    /**
     * Set the driver id as a part of the embedded JPA id.
     *
     * @param driverId
     *            The driver id to set
     */
    protected void setDriverId(final int driverId) {
        this.driverId = driverId;
    }

    /**
     * Return the race id as a part of the embedded JPA id.
     *
     * @return the raceId the race id as a part of the embedded JPA id
     */
    protected int getRaceId() {
        return raceId;
    }

    /**
     * Set the race id as a part of the embedded JPA id.
     *
     * @param raceId
     *            The race id to set
     */
    protected void setRaceId(final int raceId) {
        this.raceId = raceId;
    }

    /**
     * Return the race number as a part of the embedded JPA id.
     *
     * @return the race number as a part of the embedded JPA id
     */
    public int getRaceNumber() {
        return raceNumber;
    }

    /**
     * Set the race number as a part of the embedded JPA id.
     *
     * @param raceNumber
     *            The race number to set
     */
    protected void setRaceNumber(final int raceNumber) {
        this.raceNumber = raceNumber;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + raceId;
        result = prime * result + driverId;
        result = prime * result + raceNumber;
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
        CompetitorId other = (CompetitorId) obj;
        if (raceId != other.raceId) {
            return false;
        }
        if (driverId != other.driverId) {
            return false;
        }
        if (raceNumber != other.raceNumber) {
            return false;
        }
        return true;
    }

}
