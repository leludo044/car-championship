package net.leludo.gtrchamp;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CompetitorId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4792327019267435193L;

    @Column(name = "idPilote")
    private int driverId;
    @Column(name = "idGrandPrix")
    private int raceId;
    @Column(name = "numCourse")
    private int raceNumber;

    public int getRaceNumber() {
        return raceNumber;
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
