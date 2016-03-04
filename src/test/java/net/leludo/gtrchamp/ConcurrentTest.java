package net.leludo.gtrchamp;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConcurrentTest {

    private Concurrent acteur;

    @Before
    public void setUp() throws Exception {
        acteur = new Concurrent();
    }

    @Test
    public void test() {
        assertNotNull(acteur);
        assertEquals(0, acteur.getPositionDepart());
        assertEquals(0, acteur.getPositionArrivee());
    }

    @Test
    public void testHasPolePosition() throws ChampionshipException {
        acteur.setPositionDepart(1);
        assertTrue(acteur.hasPolePosition());
    }

    @Test
    public void testNotHasPolePosition() throws ChampionshipException {
        acteur.setPositionDepart(2);
        Assert.assertFalse(acteur.hasPolePosition());
    }

    @Test
    public void testIsVainqueur() throws ChampionshipException {
        acteur.setPositionArrivee(1);
        Assert.assertTrue(acteur.isVainqueur());
    }

    @Test
    public void testNotIsVainqueur() throws ChampionshipException {
        acteur.setPositionArrivee(2);
        Assert.assertFalse(acteur.isVainqueur());
    }

    @Test(expected = ChampionshipException.class)
    public void testPositionDepartZero() throws ChampionshipException {
        acteur.setPositionDepart(0);
    }

    @Test(expected = ChampionshipException.class)
    public void testPositionDepartNegative() throws ChampionshipException {
        acteur.setPositionDepart(-1);
    }

    @Test(expected = ChampionshipException.class)
    public void testPositionArriveeZero() throws ChampionshipException {
        acteur.setPositionArrivee(0);
    }

    @Test(expected = ChampionshipException.class)
    public void testPositionArriveeNegative() throws ChampionshipException {
        acteur.setPositionArrivee(-1);
    }

    @Test
    public void testAbandon() {
        acteur.abandonner();
        Assert.assertEquals(-1, acteur.getPositionArrivee());
    }

    @Test
    public void testHasNotTermine() {
        Assert.assertFalse(acteur.hasTermine());
    }

    @Test
    public void testHasTermine() throws ChampionshipException {
        acteur.setPositionArrivee(1);
        Assert.assertTrue(acteur.hasTermine());
    }

}
