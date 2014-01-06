package net.leludo.gtrchamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

//@Entity
//@Table(name="resultats")
public class Concurrent {
	
//    @OneToOne
//    @JoinColumn(name="idPilote", nullable=false)
	private Pilote pilote ;
	
//    @Column(name="grille")
	private int positionDepart;

	private int positionArrivee;

	private int numeroCourse;

	public boolean hasPolePosition() {
		return this.positionDepart == 1;
	}

	public boolean isVainqueur() {
		return this.positionArrivee == 1;
	}

	public Concurrent() {
		super();
		this.positionDepart = 0;
		this.positionArrivee = 0;
	}

public Concurrent(Pilote pilote) {
		this();
		this.setPilote(pilote);
	}

	public void setPilote(Pilote pilote) {
	this.pilote = pilote;
}

	public Pilote getPilote() {
		return pilote;
	}

	public void setPositionDepart(int positionDepart)
			throws ChampionnatException {
		if (positionDepart > 0) {
			this.positionDepart = positionDepart;
		} else {
			throw new ChampionnatException();
		}
	}

	public void setPositionArrivee(int positionArrivee) throws ChampionnatException {
		if (positionArrivee > 0) {
			this.positionArrivee = positionArrivee;
		} else {
			throw new ChampionnatException() ;
		}
	}

	public int getPositionDepart() {
		return positionDepart;
	}

	public int getPositionArrivee() {
		return positionArrivee;
	}

	public void abandonner() {
		this.positionArrivee = -1 ;
	}

	public boolean hasTermine() {
		return this.positionArrivee!=0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numeroCourse;
		result = prime * result + ((pilote == null) ? 0 : pilote.hashCode());
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
		Concurrent other = (Concurrent) obj;
		if (numeroCourse != other.numeroCourse)
			return false;
		if (pilote == null) {
			if (other.pilote != null)
				return false;
		} else if (!pilote.equals(other.pilote))
			return false;
		return true;
	}



}
