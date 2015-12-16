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
	private Float longueur;

	@OneToOne
	@JoinColumn(name = "idPays", nullable = false)
	private Pays pays;

	public Circuit() {
		this.nom = "";
		this.longueur = 0f;
		this.pays = null;
	}

	public Circuit(final String nom, final Float longueur, final Pays pays) {
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
	 * @param nom
	 *            the nom to set
	 */
	public void setNom(final String nom) {
		this.nom = nom;
	}

	public Float getLongueur() {
		return longueur;
	}

	/**
	 * @param longueur
	 *            the longueur to set
	 */
	public void setLongueur(final Float longueur) {
		this.longueur = longueur;
	}

	public Pays getPays() {
		return pays;
	}

	/**
	 * @param pays
	 *            the pays to set
	 */
	public void setPays(final Pays pays) {
		this.pays = pays;
	}

}
