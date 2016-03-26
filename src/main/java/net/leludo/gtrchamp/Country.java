package net.leludo.gtrchamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pays")
public class Country {
	@Id
	private int id;

	@Column(name = "nom")
	private String name;

	@Override
	public String toString() {
		return "Country [id=" + id + ", name=" + name + "]";
	}

	/**
	 * @return the id of the country
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name of the country
	 */
	public String getName() {
		return name;
	}

}
