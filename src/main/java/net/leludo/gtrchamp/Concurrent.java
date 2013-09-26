package net.leludo.gtrchamp;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("2ec9ac8d-5ca4-11e1-8c1d-0024210b642e")
public class Concurrent extends net.leludo.gtrchamp.Pilote {
    @objid ("afc5bc14-1edf-11e3-83b1-e83935331d2a")
    public int positionDepart;

    @objid ("b9897a10-1edf-11e3-83b1-e83935331d2a")
    public int positionArrivee;

    @objid ("c8c51314-1edf-11e3-83b1-e83935331d2a")
    public int numeroCourse;

    @objid ("9502a2b6-2446-11e3-842a-e83935331d2a")
    public boolean hasPoolPosition() {
        return this.positionDepart==1;
    }

    @objid ("ba7d19ce-2446-11e3-842a-e83935331d2a")
    public boolean isVainqueur() {
        return this.positionArrivee == 1;
    }

}
