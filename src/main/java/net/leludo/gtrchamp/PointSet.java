package net.leludo.gtrchamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Scoring system.
 */
@Entity
@Table(name = "score")
public class PointSet {

    // FIXME JPA ID is not correct. Should be type AND rank and not only rank
    /** Position. */
    @Id
    @Column(name = "position")
    private int rank;

    /** Points received. */
    private int points;

    /** Type of the scoring system. */
    private String type;

    /**
     * Return the number of points received .
     *
     * @return the number of points received
     */
    public int getPoints() {
        return points;
    }

    /**
     * Return the type of the score (gtr or wtcc).
     *
     * @return the type of the score
     */
    public String getType() {
        return type;
    }

    /**
     * Set the type of the scoring system.
     *
     * @param type
     *            The type of the scoring system to set
     */
    public void setType(final String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PointSet [rank=");
        builder.append(rank);
        builder.append(", points=");
        builder.append(points);
        builder.append("]");
        return builder.toString();
    }
}
