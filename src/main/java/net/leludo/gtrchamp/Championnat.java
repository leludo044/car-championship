package net.leludo.gtrchamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Représente un championnat
 */
@Entity
@Table(name = "championnats")
public class Championnat {
	@Id
	private int id;
	private String libelle;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "championnat")
	private List<GrandPrix> grandsPrix = new ArrayList<GrandPrix>();

	/**
	 * Constructeur. La liste des grands prix est vide par défaut.
	 * 
	 * @param pLibelle
	 *            Le libellé du championnat créer
	 */
	public Championnat(final String pLibelle) {
		this.libelle = pLibelle;
		this.grandsPrix = new ArrayList<GrandPrix>();
	}

	public Championnat() {
		this.libelle = "Choisir...";
		this.grandsPrix = new ArrayList<GrandPrix>();
	}

	/**
	 * Retourne le libellé du championnat
	 * 
	 * @return Le libellé du championnat
	 */
	public String getLibelle() {
		return this.libelle;
	}

	/**
	 * Retourne l'ID du championnat
	 * 
	 * @return L'ID du championnat
	 */
	public int getId() {
		return id;
	}

	/**
	 * Retourne la liste des grands prix
	 * 
	 * @return La liste des grands prix
	 */
	public List<net.leludo.gtrchamp.GrandPrix> getGrandsPrix() {
		return this.grandsPrix;
	}

	@Override
	public String toString() {
		return "Championnat [id=" + id + ", libelle=" + libelle + ", grandsPrix=" + grandsPrix + "]";
	}

	/*
	 * Fonctionnalité DDD
	 */

	public GrandPrix organiserGrandPrix(final net.leludo.gtrchamp.Circuit circuit, final Date date) {
		GrandPrix gp = new GrandPrix(circuit, date);
		this.grandsPrix.add(gp);
		return gp;
	}

	public List<net.leludo.gtrchamp.Pilote> rendreClassement() {
		// TODO Auto-generated return
		return null;
	}

}
