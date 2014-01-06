package net.leludo.gtrchamp;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ConcurrentId implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4792327019267435193L;

	@Column(name="idPilote")
	int idPilote ;
	@Column(name="idGrandPrix")
	int idGrandPrix ;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idPilote;
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
		if (idPilote != other.idPilote)
			return false;
		return true;
	}
}
