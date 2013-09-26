package net.leludo.gtrchamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("cf39c65f-57d8-11e1-8a02-0024210b642e")
public class GrandPrix {
    @objid ("f19e8444-57d8-11e1-8a02-0024210b642e")
    private Date date;

    @objid ("cb273a76-1ede-11e3-83b1-e83935331d2a")
    public boolean mode2Courses;

    @objid ("5a0cb588-5c65-11e1-b2cd-0024210b642e")
    public List<Concurrent> concurrents = new ArrayList<Concurrent> ();

    @objid ("827ca521-5c65-11e1-b2cd-0024210b642e")
    public Circuit circuit;

    @objid ("1eda6ed5-57d9-11e1-8a02-0024210b642e")
    public void inscrire(final Pilote pilote) {
    	Concurrent concurrent = new Concurrent(pilote);
    	this.concurrents.add(concurrent);
    }

    @objid ("bad22308-57d9-11e1-8a02-0024210b642e")
    public void classer(final Pilote pilote, final int points) {
    }

    @objid ("6b75ea0e-5ca3-11e1-8c1d-0024210b642e")
    public List<Concurrent> rendreClassement() {
        // TODO Auto-generated return
        return null;
    }

    @objid ("467fc76c-2442-11e3-842a-e83935331d2a")
    public void positionnerSurGrille(final net.leludo.gtrchamp.Pilote pilote, final int position) {
    }

    public GrandPrix(final Circuit circuit, final Date date) {
    	this.circuit = circuit;
    	this.date = date ;
    }
}
