package net.leludo.gtrchamp.stat;

/**
 * Représente une données statistiques pour un pilote
 */
public class Stat {

	public String name;
	public Integer count;

	/**
	 * Retourne le nom du pilote
	 * 
	 * @return Le nom du pilote
	 */
	public String getName() {
		return name;
	}

	/**
	 * Fixe le nom du pilote
	 * 
	 * @param name
	 *            Le nouveau nom du pilote
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Retourne la valeur statistique
	 * 
	 * @return La valeur statistique
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * Fixe la valeur statistique
	 * 
	 * @param count
	 *            La nouvelle valeur statistique
	 */
	public void setCount(Integer count) {
		this.count = count;
	}
}
