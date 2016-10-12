package net.leludo.gtrchamp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
        assertEquals(0, acteur.getStartingPosition());
        assertEquals(0, acteur.getArrivalPosition());
        assertNull(acteur.getDriver());
        assertNull(acteur.getRace());
    }

    @Test
    public void testHasPolePosition() throws ChampionshipException {
        acteur.setStartingPosition(1);
        assertTrue(acteur.hasPolePosition());
    }

    @Test
    public void testNotHasPolePosition() throws ChampionshipException {
        acteur.setStartingPosition(2);
        assertFalse(acteur.hasPolePosition());
    }

    @Test
    public void testIsVainqueur() throws ChampionshipException {
        acteur.setArrivalPosition(1);
        assertTrue(acteur.hasWon());
    }

    @Test
    public void testNotIsVainqueur() throws ChampionshipException {
        acteur.setArrivalPosition(2);
        assertFalse(acteur.hasWon());
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
        assertEquals(-1, acteur.getArrivalPosition());
    }

    @Test
    public void testHasNotTermine() {
        assertFalse(acteur.hasFinished());
    }

    @Test
    public void testHasTermine() throws ChampionshipException {
        acteur.setArrivalPosition(1);
        assertTrue(acteur.hasFinished());
    }

    @Test
    public void settingDriverUpdateId() throws ChampionshipException {
        acteur.setDriver(new Driver());
        assertNotNull(acteur.getDriver());
        assertNotNull(acteur.getId());
        assertEquals(0, acteur.getId().getDriverId());
    }

    @Test
    public void settingRaceMustUpdateId() throws ChampionshipException {
        acteur.setRace(new Race());
        assertNotNull(acteur.getRace());
        assertNotNull(acteur.getId());
        assertEquals(0, acteur.getId().getRaceId());
    }

    @Test
    public void settingRaceNumberMustUpdateId() throws ChampionshipException {
        acteur.setRaceNumber(1);
        assertNotNull(acteur.getId());
        assertEquals(1, acteur.getRaceNumber());
        assertEquals(1, acteur.getId().getRaceNumber());
    }

    @Test
    public void settingDriverAndRaceMustUpdateId() throws ChampionshipException {
        acteur.setDriver(new Driver());
        acteur.setRace(new Race());
        assertNotNull(acteur.getDriver());
        assertNotNull(acteur.getRace());
        assertNotNull(acteur.getId());
        assertEquals(0, acteur.getId().getDriverId());
        assertEquals(0, acteur.getId().getRaceId());
    }

    @Test(expected = ChampionshipException.class)
    public void cannotSetNullDriver() throws ChampionshipException {
        acteur.setDriver(null);
    }

    @Test(expected = ChampionshipException.class)
    public void cannotSetNullRace() throws ChampionshipException {
        acteur.setRace(null);
    }
}
