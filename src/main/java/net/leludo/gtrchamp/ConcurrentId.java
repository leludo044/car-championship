package net.leludo.gtrchamp;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Clé primaire identifiant un concurrent
 * 
 * @see Concurrent
 */
@Embeddable
public class ConcurrentId implements Serializable {

	private static final long serialVersionUID = -4792327019267435193L;

	@Column(name = "idPilote")
	int idPilote;
	@Column(name = "idGrandPrix")
	int idGrandPrix;
	@Column(name = "numCourse")
	int numCourse;

	/**
	 * Retourne le n° de course de la clé
	 * 
	 * @return le n° de cours de la clé
	 */
	public int getNumCourse() {
		return numCourse;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idGrandPrix;
		result = prime * result + idPilote;
		result = prime * result + numCourse;
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
		ConcurrentId other = (ConcurrentId) obj;
		if (idGrandPrix != other.idGrandPrix)
			return false;
		if (idPilote != other.idPilote)
			return false;
		if (numCourse != other.numCourse)
			return false;
		return true;
	}
}
