package net.leludo.gtrchamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="pays")
public class Pays {
	@Id
    private int id;

	private String nom;

	@Override
	public String toString() {
		return "Pays [id=" + id + ", nom=" + nom + "]";
	}

}
