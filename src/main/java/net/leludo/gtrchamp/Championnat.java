package net.leludo.gtrchamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid("08400c4e-5c66-11e1-b2cd-0024210b642e")
@Entity
@Table(name = "championnat")
public class Championnat {
	@Id
	private int id;

	private String libelle;

	@Transient
	private List<GrandPrix> grandsPrix = new ArrayList<GrandPrix>();

	public GrandPrix organiserGrandPrix(
			final net.leludo.gtrchamp.Circuit circuit, final Date date) {
		GrandPrix gp = new GrandPrix();
		this.grandsPrix.add(gp);
		return gp;
	}

	public List<net.leludo.gtrchamp.Pilote> rendreClassement() {
		// TODO Auto-generated return
		return null;
	}

	public Championnat(final String pLibelle) {
		this.libelle = pLibelle;
		this.grandsPrix = new ArrayList<GrandPrix>();
	}

	public List<net.leludo.gtrchamp.GrandPrix> getGrandsPrix() {
		return this.grandsPrix;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public Championnat() {
		this.libelle = "Choisir...";
		this.grandsPrix = new ArrayList<GrandPrix>();
	}

	public int getId() {
		return id;
	}

}
