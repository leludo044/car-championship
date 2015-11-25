package net.leludo.gtrchamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Représente un circuit
 *
 */
@Entity
@Table(name = "circuits")
public class Circuit {
	@Id
	private int id;
	private String nom;
	private String longueur;

	@OneToOne
	@JoinColumn(name = "idPays", nullable = false)
	private Pays pays;

	@Override
	public String toString() {
		return "Circuit [id=" + id + ", nom=" + nom + ", longueur=" + longueur + ", pays=" + pays + "]";
	}

	/**
	 * Retourne le nom du circuit
	 * 
	 * @return Le nom du circuit
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Retourne la longueur en Km du circuit
	 * 
	 * @return La longueur en Km du circuit
	 */
	public String getLongueur() {
		return longueur;
	}

	/**
	 * Retourne le pays d'appartenance du circuit
	 * 
	 * @return Le pays d'appartenance du circuit
	 */
	public Pays getPays() {
		return pays;
	}
}
