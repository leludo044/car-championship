package net.leludo.gtrchamp.ws;

public class PiloteParams {
	private Integer id;
	private String nom;
	private String dateNaissance;

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
	public void setId(Integer id) {
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
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the dateNaissance
	 */
	public String getDateNaissance() {
		return dateNaissance;
	}

	/**
	 * @param dateNaissance
	 *            the dateNaissance to set
	 */
	public void setDateNaissance(String dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
}
