package net.leludo.gtrchamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.taglibs.standard.tag.common.core.OtherwiseTag;
import org.hibernate.FetchMode;
import org.hibernate.annotations.Fetch;

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
//	@Transient
	private int positionArrivee;

//	@ManyToOne()
//	@Fetch(org.hibernate.annotations.FetchMode.JOIN)
//	@JoinColumn(name = "place")
//	private Point points;
	
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

	public void setPositionArrivee(int positionArrivee)
			throws ChampionnatException {
		if (positionArrivee > 0) {
			this.positionArrivee = positionArrivee;
		} else {
			throw new ChampionnatException();
		}
	}

	public int getPositionDepart() {
		return positionDepart;
	}

	public int getPositionArrivee() {
		return positionArrivee;
	}

	public int getNumeroCourse() {
		return this.id.getNumCourse();
	}
	
	public int getPoints() {
		int points = 0 ;
		
		switch (this.positionArrivee) {
		case 1 : points =10 ; break;
		case 2 : points = 8 ; break;
		}
		
		return points ;
	}

	public void abandonner() {
		this.positionArrivee = -1;
	}

	public boolean hasTermine() {
		return this.positionArrivee != 0;
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
