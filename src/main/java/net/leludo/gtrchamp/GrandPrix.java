package net.leludo.gtrchamp;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class GrandPrix {
    private Date date;

    public boolean mode2Courses;

    public Map<Concurrent, Integer> concurrents = new Hashtable<Concurrent, Integer> ();

    public Circuit circuit;

    public Concurrent inscrire(final Pilote pilote) {
    	Concurrent concurrent = new Concurrent(pilote);
    	this.concurrents.put(concurrent, 0);
    	return concurrent;
    }

    public void classer(final Pilote pilote, final int points) {
    }

    public List<Concurrent> rendreClassement() {
        // TODO Auto-generated return
        return null;
    }

    public void positionnerSurGrille(final Concurrent concurrent, final int position) {
    	this.concurrents.put(concurrent, position);
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

	public Object getPotitionDepart(Concurrent concurrent) {
		return this.concurrents.get(concurrent);
	}
}
