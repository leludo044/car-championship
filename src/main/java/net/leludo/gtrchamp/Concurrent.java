package net.leludo.gtrchamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Représente la participation d'un pilote à un grand prix
 */
@Entity
@Table(name = "resultats")
public class Concurrent {
	@EmbeddedId
	ConcurrentId id;

	@MapsId("idPilote")
	@OneToOne
	@JoinColumn(name = "idPilote")
	private Pilote pilote;

	@MapsId("idGrandPrix")
	@OneToOne
	@JoinColumn(name = "idGrandPrix")
	private GrandPrix grandPrix;

	@Column(name = "grille")
	private int positionDepart;

	@Column(name = "place")
	private int positionArrivee;

	@ManyToOne()
	@JoinColumn(name = "place", insertable = false, updatable = false)
	private Point points;

	/**
	 * Constructeur par défaut
	 */
	public Concurrent() {
		super();
		this.positionDepart = 0;
		this.positionArrivee = 0;
	}

	/**
	 * Constructeur
	 * 
	 * @param pilote
	 *            Le pilote participant au grand prix
	 */
	public Concurrent(Pilote pilote) {
		this();
		this.setPilote(pilote);
	}

	/**
	 * Indique si le pilote s'est positionné en pole position
	 * 
	 * @return true ou false
	 */
	public boolean hasPolePosition() {
		return this.positionDepart == 1;
	}

	/**
	 * Indique si un pilote à terminé premier
	 * 
	 * @return true ou false
	 */
	public boolean isVainqueur() {
		return this.positionArrivee == 1;
	}

	/**
	 * Fixe le pilote concerné
	 * 
	 * @param pilote
	 *            Le nouveau pilote concerné
	 */
	public void setPilote(Pilote pilote) {
		this.pilote = pilote;
	}

	/**
	 * Retourne le pilote concerné
	 * 
	 * @return Le pilote concerné
	 */
	public Pilote getPilote() {
		return pilote;
	}

	/**
	 * Fixe la position de départ du pilote
	 * 
	 * @param positionDepart
	 *            La position de départ
	 * @throws ChampionnatException
	 *             Exception levée si la position de départ n'est pas > 0
	 */
	public void setPositionDepart(int positionDepart) throws ChampionnatException {
		if (positionDepart > 0) {
			this.positionDepart = positionDepart;
		} else {
			throw new ChampionnatException();
		}
	}

	/**
	 * Fixe la position d'arrivée du pilote
	 * 
	 * @param positionArrivee
	 *            La position d'arrivée
	 * @throws ChampionnatException
	 *             Exception levée si la position d'arrivée n'est pas > 0
	 */
	public void setPositionArrivee(int positionArrivee) throws ChampionnatException {
		if (positionArrivee > 0) {
			this.positionArrivee = positionArrivee;
		} else {
			throw new ChampionnatException();
		}
	}

	/**
	 * Retourne la position de départ du pilote
	 * 
	 * @return La position de départ du pilote
	 */
	public int getPositionDepart() {
		return positionDepart;
	}

	/**
	 * Retourne la position d'arrivée du pilote
	 * 
	 * @return La position d'arrivée du pilote
	 */
	public int getPositionArrivee() {
		return positionArrivee;
	}

	/**
	 * Retourne le n° de course. Cas ou un grand prix est couru sur plusieurs
	 * courses
	 * 
	 * @return Le n° de course
	 */
	public int getNumeroCourse() {
		return this.id.getNumCourse();
	}

	/**
	 * Retourne le nombre de points marqués par le pilote
	 * 
	 * @return Le nombre de points marqués par le pilote
	 */
	public Point getPoints() {
		return points;
	}

	/**
	 * Indique si un pilote à terminé le grand prix
	 * 
	 * @return true ou false
	 */
	public boolean hasTermine() {
		return this.positionArrivee != 0;
	}

	/**
	 * Signifie l'abandon d'un pilote
	 */
	public void abandonner() {
		this.positionArrivee = -1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.getNumeroCourse();
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
		if (this.getNumeroCourse() != other.getNumeroCourse())
			return false;
		if (pilote == null) {
			if (other.pilote != null)
				return false;
		} else if (!pilote.equals(other.pilote))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Concurrent [id=");
		builder.append(id);
		builder.append(", pilote=");
		builder.append(pilote);
		builder.append(", grandPrix=");
		builder.append(grandPrix);
		builder.append(", positionDepart=");
		builder.append(positionDepart);
		builder.append(", positionArrivee=");
		builder.append(positionArrivee);
		builder.append(", numeroCourse=");
		builder.append(this.getNumeroCourse());
		builder.append("]");
		return builder.toString();
	}

}
