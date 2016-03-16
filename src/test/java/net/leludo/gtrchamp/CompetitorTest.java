package net.leludo.gtrchamp;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CompetitorTest {

    private Competitor acteur;

    @Before
    public void setUp() throws Exception {
        acteur = new Competitor();
    }

    @Test
    public void test() {
        assertNotNull(acteur);
        assertEquals(0, acteur.getSartingPosition());
        assertEquals(0, acteur.getArrivalPosition());
    }

    @Test
    public void testHasPolePosition() throws ChampionshipException {
        acteur.setStartingPosition(1);
        assertTrue(acteur.hasPolePosition());
    }

    @Test
    public void testNotHasPolePosition() throws ChampionshipException {
        acteur.setStartingPosition(2);
        Assert.assertFalse(acteur.hasPolePosition());
    }

    @Test
    public void testIsVainqueur() throws ChampionshipException {
        acteur.setArrivalPosition(1);
        Assert.assertTrue(acteur.hasWon());
    }

    @Test
    public void testNotIsVainqueur() throws ChampionshipException {
        acteur.setArrivalPosition(2);
        Assert.assertFalse(acteur.hasWon());
    }

    @Test(expected = ChampionshipException.class)
    public void testPositionDepartZero() throws ChampionshipException {
        acteur.setStartingPosition(0);
    }

    @Test(expected = ChampionshipException.class)
    public void testPositionDepartNegative() throws ChampionshipException {
        acteur.setStartingPosition(-1);
    }

    @Test(expected = ChampionshipException.class)
    public void testPositionArriveeZero() throws ChampionshipException {
        acteur.setArrivalPosition(0);
    }

    @Test(expected = ChampionshipException.class)
    public void testPositionArriveeNegative() throws ChampionshipException {
        acteur.setArrivalPosition(-1);
    }

    @Test
    public void testAbandon() {
        acteur.abandon();
        Assert.assertEquals(-1, acteur.getArrivalPosition());
    }

    @Test
    public void testHasNotTermine() {
        Assert.assertFalse(acteur.hasFinished());
    }

    @Test
    public void testHasTermine() throws ChampionshipException {
        acteur.setArrivalPosition(1);
        Assert.assertTrue(acteur.hasFinished());
    }

}
