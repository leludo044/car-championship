package net.leludo.gtrchamp;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Représente un pilote
 */
@Entity
@Table(name = "pilotes")
public class Pilote {
	@Id
	private int id;
	private String nom;

	@Transient
	private Date dateNaissance;

	/**
	 * Constructeur. Le nom est vide et la date de naissance est nulle.
	 */
	public Pilote() {
		this.nom = "";
		this.dateNaissance = null;
	}

	/**
	 * Retourne l'ID du pilote
	 * 
	 * @return L'ID du pilote
	 */
	public int getId() {
		return id;
	}

	/**
	 * Fixe l'ID du pilote
	 * 
	 * @param id
	 *            Le nouvel ID du pilote
	 */
	public void setId(int id) {
	}

	/**
	 * Retourne le nom du pilote
	 * 
	 * @return Le nom du pilote
	 */
	public String getNom() {
		return nom;
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
