package net.leludo.gtrchamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "points")
public class PointSet {

    @Id
    @Column(name = "place")
    private int rank;

    private int points;

    private String type;

    /**
     * @return the number of points for this rank
     */
    public int getPoints() {
        return points;
    }

    /**
     * @return the type of the set of points
     */
    public String getType() {
        return type;
    }

    /**
     * Set the type of the set of points
     *
     * @param type
     *            the type to set
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
