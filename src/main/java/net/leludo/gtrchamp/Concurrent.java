package net.leludo.gtrchamp;


public class Concurrent extends Pilote {
	public int positionDepart;

	public int positionArrivee;

	public int numeroCourse;

	public boolean hasPoolPosition() {
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
		this.nom = pilote.nom;
		this.dateNaissance = pilote.dateNaissance;
	}

}
