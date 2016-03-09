package net.leludo.gtrchamp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import net.leludo.gtrchamp.dao.converter.LocalDateConverter;

@Entity
@Table(name = "pilotes")
public class Driver {
	@Id
	@GeneratedValue
	private int id;

	@Column(name = "nom")
	private String name;

	@Column(name = "dateNaissance")
	@Convert(converter = LocalDateConverter.class)
	private LocalDate birthdate;

	/**
	 * Constructor.
	 */
	public Driver() {
		this.name = "";
		this.birthdate = null;
	}

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            The name of the driver
	 * @param birthdate
	 *            The birthdate of the driver or null if the birthdate is
	 *            unknown
	 */
	public Driver(final String name, final LocalDate birthdate) {
		this.name = name;
		this.birthdate = birthdate;
	}

	/**
	 * Set the id of the driver.
	 * 
	 * @param id
	 *            The id of the driver
	 */
	public void setId(final int id) {
		this.id = id;
	}

	/**
	 * @return the id of the driver
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name of the driver
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the driver.
	 * 
	 * @param name
	 *            The name of the driver
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the birthdate of the driver to "dd/MM/yyyy" format or blank
	 *         string if birthdate is null
	 */
	public String getBirthdate() {
		return this.birthdate == null ? "" : birthdate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

	/**
	 * Set the birthdate of the driver.
	 * @param date The birthdate of the driver
	 */
	public void setBirthdate(final LocalDate date) {
		this.birthdate = date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Driver other = (Driver) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Pilote [id=");
		builder.append(id);
		builder.append(", nom=");
		builder.append(name);
		builder.append(", dateNaissance=");
		builder.append(birthdate);
		builder.append("]");
		return builder.toString();
	}

}
