package net.leludo.gtrchamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Représente un pays
 */
@Entity
@Table(name = "pays")
public class Pays {
	@Id
	private int id;
	private String nom;

	@Override
	public String toString() {
		return "Pays [id=" + id + ", nom=" + nom + "]";
	}

	/**
	 * Retourne le nom du pays
	 * 
	 * @return Le nom du pays
	 */
	public String getNom() {
		return nom;
	}
}
