package net.leludo.gtrchamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "resultats")
public class Competitor {
    @EmbeddedId
    private CompetitorId id;

    @MapsId("driverId")
    @OneToOne
    @JoinColumn(name = "idPilote")
    private Driver driver;

    @MapsId("raceId")
    @OneToOne
    @JoinColumn(name = "idGrandPrix")
    private Race race;

    @Column(name = "grille")
    private int startingPosition;

    @Column(name = "place")
    private int arrivalPosition;

    // TODO Supprimer ou configurer avec une clé primaire composée
    @ManyToOne()
    @JoinColumn(name = "place", insertable = false, updatable = false)
    private PointSet points;

    public boolean hasPolePosition() {
        return this.startingPosition == 1;
    }

    public boolean hasWon() {
        return this.arrivalPosition == 1;
    }

    public Competitor() {
        super();
        this.startingPosition = 0;
        this.arrivalPosition = 0;
    }

    public Competitor(final Driver driver) {
        this();
        this.setDriver(driver);
    }

    public void setDriver(final Driver driver) {
        this.driver = driver;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setStartingPosition(final int startingPosition) throws ChampionshipException {
        if (startingPosition > 0) {
            this.startingPosition = startingPosition;
        } else {
            throw new ChampionshipException();
        }
    }

    public void setArrivalPosition(final int arrivalPosition) throws ChampionshipException {
        if (arrivalPosition > 0) {
            this.arrivalPosition = arrivalPosition;
        } else {
            throw new ChampionshipException();
        }
    }

    public int getSartingPosition() {
        return startingPosition;
    }

    public int getArrivalPosition() {
        return arrivalPosition;
    }

    public int getRaceNumber() {
        return this.id.getRaceNumber();
    }

    public PointSet getPoints() {
        return points;
    }

    public void abandon() {
        this.arrivalPosition = -1;
    }

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
