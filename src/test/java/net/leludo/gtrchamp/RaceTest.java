package net.leludo.gtrchamp;

import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RaceTest {

    private Race acteur;

    @Before
    public void setUp() throws Exception {
        acteur = new Race(null, new Track(), LocalDate.now());
    }

    @Test
    public void test() {
        Assert.assertNotNull(acteur.getTrack());
        Assert.assertEquals(0, acteur.competitorsCount());
    }

    @Test
    public void testInscrirePilote() throws ChampionshipException {
        Competitor concurrent = inscrirePilote(1);
        Assert.assertEquals(1, acteur.competitorsCount());
        Assert.assertEquals("", concurrent.getDriver().getName());
    }

    @Test(expected = ChampionshipException.class)
    public void testInscrirePiloteNull() throws ChampionshipException {
        Competitor concurrent = acteur.signUp(null);
    }

    @Test
    public void testIsNotTermine() throws ChampionshipException {
        this.inscrirePilote(1);
        Assert.assertFalse(acteur.isFinished());
    }

    @Test
    public void testIsTerminePour1Concurrent() throws ChampionshipException {
        Competitor concurrent = this.inscrirePilote(1);
        concurrent.setArrivalPosition(1);
        Assert.assertTrue(acteur.isFinished());
    }

    @Test
    public void testIsNotTerminePour1Concurrent() throws ChampionshipException {
        Competitor concurrent1 = this.inscrirePilote(1);
        concurrent1.setArrivalPosition(1);
        Competitor concurrent2 = this.inscrirePilote(2);
        Assert.assertFalse(acteur.isFinished());
    }

    @Test
    public void testIsTerminePour2Concurrents() throws ChampionshipException {
        Competitor concurrent1 = this.inscrirePilote(1);
        concurrent1.setArrivalPosition(1);
        Competitor concurrent2 = this.inscrirePilote(2);
        concurrent2.setArrivalPosition(2);
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
        Competitor concurrent = this.inscrirePilote(1);
        concurrent.setArrivalPosition(1);
        List<Competitor> classement = acteur.results();
        Assert.assertEquals(1, classement.size());
    }

    @Test(expected = ChampionshipException.class)
    public void testRendreClassementPourPlusieursConcurrentsNonTermine()
            throws ChampionshipException {
        Competitor concurrent1 = this.inscrirePilote(1);
        concurrent1.setArrivalPosition(1);
        Competitor concurrent2 = this.inscrirePilote(2);
        List<Competitor> classement = acteur.results();
    }

    @Test
    public void testRendreClassementPourPlusieursConcurrentsTermine() throws ChampionshipException {
        Competitor concurrent1 = this.inscrirePilote(1);
        concurrent1.setArrivalPosition(1);
        Competitor concurrent2 = this.inscrirePilote(2);
        concurrent2.setArrivalPosition(2);
        List<Competitor> classement = acteur.results();
        Assert.assertEquals(2, classement.size());
    }

    @Test
    public void testRendreClassementPourPlusieursConcurrentsTrie() throws ChampionshipException {
        Competitor concurrent1 = this.inscrirePilote(1);
        concurrent1.setArrivalPosition(2);
        Competitor concurrent2 = this.inscrirePilote(2);
        concurrent2.setArrivalPosition(1);
        List<Competitor> classement = acteur.results();
        Assert.assertEquals(2, classement.size());
        Assert.assertEquals(concurrent2, classement.get(0));
        Assert.assertEquals(concurrent1, classement.get(1));
    }

    private Competitor inscrirePilote(final int id) throws ChampionshipException {
        Driver pilote = new Driver();
        pilote.setId(id);
        Competitor concurrent = acteur.signUp(pilote);
        return concurrent;
    }

}
