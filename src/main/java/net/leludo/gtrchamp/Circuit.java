package net.leludo.gtrchamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name="circuits")
public class Circuit {
	@Id
    private int id;

    private String nom;

    private String longueur;

    @OneToOne
    @JoinColumn(name="id", nullable=false)
    private net.leludo.gtrchamp.Pays pays;

	@Override
	public String toString() {
		return "Circuit [id=" + id + ", nom=" + nom + ", longueur=" + longueur
				+ ", pays=" + pays + "]";
	}

	public String getNom() {
		return nom;
	}

}
