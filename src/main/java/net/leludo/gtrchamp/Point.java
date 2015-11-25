package net.leludo.gtrchamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Représente une valeur de point ne fonction d'une position finale sur un grand
 * prix
 */
@Entity
@Table(name = "points")
public class Point {

	@Id
	private int place;
	private int points;

	/**
	 * Retourne le nombre de points
	 * 
	 * @return Le nombre de points
	 */
	public int getPoints() {
		return points;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Point [place=");
		builder.append(place);
		builder.append(", points=");
		builder.append(points);
		builder.append("]");
		return builder.toString();
	}
}
