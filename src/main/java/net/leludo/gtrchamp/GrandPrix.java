package net.leludo.gtrchamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GrandPrix {
    private Date date;

    public boolean mode2Courses;

    public List<Concurrent> concurrents = new ArrayList<Concurrent> ();

    public Circuit circuit;

    public void inscrire(final Pilote pilote) {
    	Concurrent concurrent = new Concurrent(pilote);
    	this.concurrents.add(concurrent);
    }

    public void classer(final Pilote pilote, final int points) {
    }

    public List<Concurrent> rendreClassement() {
        // TODO Auto-generated return
        return null;
    }

    public void positionnerSurGrille(final net.leludo.gtrchamp.Pilote pilote, final int position) {
    }

    void GrandPrix(final Circuit circuit, final Date date) {
    	this.circuit = circuit;
    	this.date = date ;
    }
}
