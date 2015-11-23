package net.leludo.gtrchamp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import net.leludo.gtrchamp.dao.converter.LocalDateConverter;

@Entity
@Table(name = "pilotes")
public class Pilote {
	@Id
	@GeneratedValue
	private int id;

	private String nom;

	@Convert(converter = LocalDateConverter.class)
	private LocalDate dateNaissance;

	public Pilote() {
		this.nom = "";
		this.dateNaissance = null;
	}

	public Pilote(final String name, final LocalDate date) {
		this.nom = name;
		this.dateNaissance = date;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public void setPrenom(final String prenom) {
		this.nom = prenom;
	}

	public String getDateNaissance() {
		return this.dateNaissance==null?"":dateNaissance.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
	
	public void setDateNaissance(LocalDate date) {
		this.dateNaissance = date ;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pilote other = (Pilote) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Pilote [id=");
		builder.append(id);
		builder.append(", nom=");
		builder.append(nom);
		builder.append(", dateNaissance=");
		builder.append(dateNaissance);
		builder.append("]");
		return builder.toString();
	}

}
