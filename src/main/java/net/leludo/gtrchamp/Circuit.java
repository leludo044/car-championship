package net.leludo.gtrchamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="circuits")
public class Circuit {
	@Id
    private int id;

    private String nom;

    // FIXME Positionner la longueur sur un float
    private String longueur;

    @OneToOne
    @JoinColumn(name="idPays", nullable=false)
    private Pays pays;

	@Override
	public String toString() {
		return "Circuit [id=" + id + ", nom=" + nom + ", longueur=" + longueur
				+ ", pays=" + pays + "]";
	}

	public String getNom() {
		return nom;
	}

	public String getLongueur() {
		return longueur;
	}

	public Pays getPays() {
		return pays;
	}

}
