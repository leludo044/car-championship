package net.leludo.gtrchamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="points")
public class Point {

	@Id
	private int place ;
	private int points ;
	
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
