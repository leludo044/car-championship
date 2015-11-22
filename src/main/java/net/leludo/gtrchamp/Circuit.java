package net.leludo.gtrchamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "circuits")
public class Circuit {
	@Id
	@GeneratedValue
	private int id;

	private String nom;

	// FIXME Positionner la longueur sur un float
	private String longueur;

	@OneToOne
	@JoinColumn(name = "idPays", nullable = false)
	private Pays pays;

	public Circuit() {
		this.nom = "";
		this.longueur = "0";
		this.pays = null;
	}

	public Circuit(String nom, String longueur, Pays pays) {
		this.nom = nom;
		this.longueur = longueur;
		this.pays = pays;
	}

	@Override
	public String toString() {
		return "Circuit [id=" + id + ", nom=" + nom + ", longueur=" + longueur + ", pays=" + pays + "]";
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getLongueur() {
		return longueur;
	}

	/**
	 * @param longueur the longueur to set
	 */
	public void setLongueur(String longueur) {
		this.longueur = longueur;
	}

	public Pays getPays() {
		return pays;
	}

	/**
	 * @param pays the pays to set
	 */
	public void setPays(Pays pays) {
		this.pays = pays;
	}

}
