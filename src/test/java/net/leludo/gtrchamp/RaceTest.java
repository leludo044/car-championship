package net.leludo.gtrchamp;

import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RaceTest {

    private Race acteur;

    @Before
    public void setUp() throws Exception {
        acteur = new Race(null, new Track(), Calendar.getInstance().getTime());
    }

    @Test
    public void test() {
        Assert.assertNotNull(acteur.getTrack());
        Assert.assertEquals(0, acteur.competitorsCount());
    }

    @Test
    public void testInscrirePilote() throws ChampionshipException {
        Concurrent concurrent = inscrirePilote(1);
        Assert.assertEquals(1, acteur.competitorsCount());
        Assert.assertEquals("", concurrent.getPilote().getName());
    }

    @Test(expected = ChampionshipException.class)
    public void testInscrirePiloteNull() throws ChampionshipException {
        Concurrent concurrent = acteur.signUp(null);
    }

    @Test
    public void testIsNotTermine() throws ChampionshipException {
        this.inscrirePilote(1);
        Assert.assertFalse(acteur.isFinished());
    }

    @Test
    public void testIsTerminePour1Concurrent() throws ChampionshipException {
        Concurrent concurrent = this.inscrirePilote(1);
        concurrent.setPositionArrivee(1);
        Assert.assertTrue(acteur.isFinished());
    }

    @Test
    public void testIsNotTerminePour1Concurrent() throws ChampionshipException {
        Concurrent concurrent1 = this.inscrirePilote(1);
        concurrent1.setPositionArrivee(1);
        Concurrent concurrent2 = this.inscrirePilote(2);
        Assert.assertFalse(acteur.isFinished());
    }

    @Test
    public void testIsTerminePour2Concurrents() throws ChampionshipException {
        Concurrent concurrent1 = this.inscrirePilote(1);
        concurrent1.setPositionArrivee(1);
        Concurrent concurrent2 = this.inscrirePilote(2);
        concurrent2.setPositionArrivee(2);
        Assert.assertTrue(acteur.isFinished());
    }

    @Test(expected = ChampionshipException.class)
    public void testRendreClassementACreation() throws ChampionshipException {
        acteur.results();
    }

    @Test(expected = ChampionshipException.class)
    public void testRendreClassementPourGrandsPrixNonTermine() throws ChampionshipException {
        this.inscrirePilote(1);
        acteur.results();
    }

    @Test
    public void testRendreClassementPour1Concurrent() throws ChampionshipException {
        Concurrent concurrent = this.inscrirePilote(1);
        concurrent.setPositionArrivee(1);
        List<Concurrent> classement = acteur.results();
        Assert.assertEquals(1, classement.size());
    }

    @Test(expected = ChampionshipException.class)
    public void testRendreClassementPourPlusieursConcurrentsNonTermine()
            throws ChampionshipException {
        Concurrent concurrent1 = this.inscrirePilote(1);
        concurrent1.setPositionArrivee(1);
        Concurrent concurrent2 = this.inscrirePilote(2);
        List<Concurrent> classement = acteur.results();
    }

    @Test
    public void testRendreClassementPourPlusieursConcurrentsTermine() throws ChampionshipException {
        Concurrent concurrent1 = this.inscrirePilote(1);
        concurrent1.setPositionArrivee(1);
        Concurrent concurrent2 = this.inscrirePilote(2);
        concurrent2.setPositionArrivee(2);
        List<Concurrent> classement = acteur.results();
        Assert.assertEquals(2, classement.size());
    }

    @Test
    public void testRendreClassementPourPlusieursConcurrentsTrie() throws ChampionshipException {
        Concurrent concurrent1 = this.inscrirePilote(1);
        concurrent1.setPositionArrivee(2);
        Concurrent concurrent2 = this.inscrirePilote(2);
        concurrent2.setPositionArrivee(1);
        List<Concurrent> classement = acteur.results();
        Assert.assertEquals(2, classement.size());
        Assert.assertEquals(concurrent2, classement.get(0));
        Assert.assertEquals(concurrent1, classement.get(1));
    }

    private Concurrent inscrirePilote(final int id) throws ChampionshipException {
        Driver pilote = new Driver();
        pilote.setId(id);
        Concurrent concurrent = acteur.signUp(pilote);
        return concurrent;
    }

}
