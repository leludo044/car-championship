package net.leludo.gtrchamp;

public class Concurrent extends Pilote {
	public int positionDepart;

	public int positionArrivee;

	public int numeroCourse;

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
		this.id = pilote.id;
		this.nom = pilote.nom;
		this.dateNaissance = pilote.dateNaissance;
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
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals((Pilote)obj) ;
	}

}
