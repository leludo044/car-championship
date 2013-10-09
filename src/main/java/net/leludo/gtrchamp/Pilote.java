package net.leludo.gtrchamp;

import java.util.Date;

public class Pilote {
    public int id;

    public String nom;

    public Date dateNaissance;

    public Pilote() {
    	this.nom ="" ;
    	this.dateNaissance = null;
    }
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Pilote other = (Pilote) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
