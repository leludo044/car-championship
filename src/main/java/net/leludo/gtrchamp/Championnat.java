package net.leludo.gtrchamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("08400c4e-5c66-11e1-b2cd-0024210b642e")
public class Championnat {
    @objid ("0f39c4d9-5c66-11e1-b2cd-0024210b642e")
    private int id;

    @objid ("1ed0d1bf-5c66-11e1-b2cd-0024210b642e")
    private String libelle;

    @objid ("2dc35d32-5c66-11e1-b2cd-0024210b642e")
    public List<net.leludo.gtrchamp.GrandPrix> grandsPrix = new ArrayList<net.leludo.gtrchamp.GrandPrix>();

    @objid ("dad02fe6-5ca2-11e1-8c1d-0024210b642e")
    public GrandPrix organiserGrandPrix(final Circuit circuit, final Date date) {
        GrandPrix gp = new GrandPrix(circuit, date);
        this.grandsPrix.add(gp);
        return gp;
    }

    @objid ("3d089d73-5ca3-11e1-8c1d-0024210b642e")
    public List<net.leludo.gtrchamp.Pilote> rendreClassement() {
        // TODO Auto-generated return
        return null;
    }

    @objid ("ea51aa07-244c-11e3-842a-e83935331d2a")
    public Championnat(final String pLibelle) {
        this.libelle = pLibelle;
        this.grandsPrix = new ArrayList<GrandPrix>();
    }

    @objid ("ca295267-244e-11e3-842a-e83935331d2a")
    public List<net.leludo.gtrchamp.GrandPrix> getGrandsPrix() {
        return this.grandsPrix;
    }

    @objid ("e8f0a7c7-244e-11e3-842a-e83935331d2a")
    public String getLibelle() {
        return this.libelle;
    }

    @objid ("24390706-b596-4840-b4a6-ad4692681b05")
    public Championnat() {
        this.libelle = "Choisir...";
        this.grandsPrix = new ArrayList<GrandPrix>();
    }

}
