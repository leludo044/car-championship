package net.leludo.gtrchamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GrandPrix {
    private Date date;

    public boolean mode2Courses;

    public List<Concurrent> concurrents = new ArrayList<Concurrent> ();

    public Circuit circuit;

    public Concurrent inscrire(final Pilote pilote) throws ChampionnatException {
    	if (pilote == null) {
    		throw new ChampionnatException() ;
    	}
    	Concurrent concurrent = new Concurrent(pilote);
    	this.concurrents.add(concurrent);
    	return concurrent;
    }

    public void classer(final Pilote pilote, final int points) {
    }

    public List<Concurrent> rendreClassement() {
        // TODO Auto-generated return
        return null;
    }

	public void positionnerSurGrille(final Concurrent concurrent,
			final int position) throws ChampionnatException {
    	if (concurrent == null) {
    		throw new ChampionnatException();
    	}
    	concurrent.setPositionDepart(position);
    }

    public GrandPrix(final Circuit circuit, final Date date) {
    	this.circuit = circuit;
    	this.date = date ;
    }

	public Object getCircuit() {
		return this.circuit;
	}

	public Object getNbInscrits() {
		return this.concurrents.size();
	}

}
