package net.leludo.gtrchamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GrandPrix {
	private Date date;

	public boolean mode2Courses;

	public List<Concurrent> concurrents = new ArrayList<Concurrent>();

	public Circuit circuit;

	public Concurrent inscrire(final Pilote pilote) throws ChampionnatException {
		if (pilote == null) {
			throw new ChampionnatException();
		}
		Concurrent concurrent = new Concurrent(pilote);
		this.concurrents.add(concurrent);
		return concurrent;
	}

	public List<Concurrent> rendreClassement() throws ChampionnatException {
		if (this.concurrents.size() == 0) {
			throw new ChampionnatException();
		} else if (!this.isTermine()) {
			throw new ChampionnatException();
		}

		List<Concurrent> classement = new ArrayList<Concurrent>();
		for (Concurrent concurrent : this.concurrents) {
			classement.add(concurrent);
		}

		return classement;
	}

	public GrandPrix(final Circuit circuit, final Date date) {
		this.circuit = circuit;
		this.date = date;
	}

	public Object getCircuit() {
		return this.circuit;
	}

	public Object getNbInscrits() {
		return this.concurrents.size();
	}

	public boolean isTermine() {
		boolean isTermine = true;
		for (Concurrent concurrent : this.concurrents) {
			isTermine &= concurrent.hasTermine();
		}
		return isTermine;
	}

}
