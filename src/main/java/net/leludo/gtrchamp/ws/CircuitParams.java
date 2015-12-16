package net.leludo.gtrchamp.ws;

public class CircuitParams {
	private Integer id;
	private String nom;
	private Float longueur;
	private String idPays;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(final Integer id) {
		this.id = id;
	}

	/**
	 * @return the nom
	 */
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

	/**
	 * @return the longueur
	 */
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

	/**
	 * @return the idPays
	 */
	public String getIdPays() {
		return idPays;
	}

	/**
	 * @param idPays
	 *            the idPays to set
	 */
	public void setIdPays(final String idPays) {
		this.idPays = idPays;
	}

}
