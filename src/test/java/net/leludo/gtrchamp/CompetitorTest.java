package net.leludo.gtrchamp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class CompetitorTest {

    private Competitor actor;

    @Before
    public void setUp() throws Exception {
        actor = new Competitor();
    }

    @Test
    public void test() {
        assertNotNull(actor);
        assertEquals(0, actor.getStartingPosition());
        assertEquals(0, actor.getArrivalPosition());
        assertNull(actor.getDriver());
        assertNull(actor.getRace());
    }

    @Test
    public void testHasPolePosition() throws ChampionshipException {
        actor.setStartingPosition(1);
        assertTrue(actor.hasPolePosition());
    }

    @Test
    public void testNotHasPolePosition() throws ChampionshipException {
        actor.setStartingPosition(2);
        assertFalse(actor.hasPolePosition());
    }

    @Test
    public void testIsWinner() throws ChampionshipException {
        actor.setArrivalPosition(1);
        assertTrue(actor.hasWon());
    }

    @Test
    public void testIsNotWinner() throws ChampionshipException {
        actor.setArrivalPosition(2);
        assertFalse(actor.hasWon());
    }

    @Test(expected = ChampionshipException.class)
    public void testStartingPositionToZero() throws ChampionshipException {
        actor.setStartingPosition(0);
    }

    @Test(expected = ChampionshipException.class)
    public void testNegativeStartingPosition() throws ChampionshipException {
        actor.setStartingPosition(-1);
    }

    @Test(expected = ChampionshipException.class)
    public void testArrivalPositionToZero() throws ChampionshipException {
        actor.setArrivalPosition(0);
    }

    @Test(expected = ChampionshipException.class)
    public void testNegativeArrivalPosition() throws ChampionshipException {
        actor.setArrivalPosition(-1);
    }

    @Test(expected = ChampionshipException.class)
    public void testRaceNumberUnder1() throws ChampionshipException {
        actor.setRaceNumber(0);
    }

    @Test(expected = ChampionshipException.class)
    public void testRaceNumberAbove2() throws ChampionshipException {
        actor.setRaceNumber(0);
    }

    @Test
    public void testCanceledRace() {
        actor.canceled();
        assertEquals(-1, actor.getArrivalPosition());
    }

    @Test
    public void testHasNotFinished() {
        assertFalse(actor.hasFinished());
    }

    @Test
    public void testHasFinished() throws ChampionshipException {
        actor.setArrivalPosition(1);
        assertTrue(actor.hasFinished());
    }

    @Test
    public void settingDriverUpdateId() throws ChampionshipException {
        actor.setDriver(new Driver());
        assertNotNull(actor.getDriver());
        assertNotNull(actor.getId());
        assertEquals(0, actor.getId().getDriverId());
    }

    @Test
    public void settingRaceMustUpdateId() throws ChampionshipException {
        actor.setRace(new Race());
        assertNotNull(actor.getRace());
        assertNotNull(actor.getId());
        assertEquals(0, actor.getId().getRaceId());
    }

    @Test
    public void settingRaceNumberMustUpdateId() throws ChampionshipException {
        actor.setRaceNumber(1);
        assertNotNull(actor.getId());
        assertEquals(1, actor.getRaceNumber());
        assertEquals(1, actor.getId().getRaceNumber());
    }

    @Test
    public void settingDriverAndRaceMustUpdateId() throws ChampionshipException {
        actor.setDriver(new Driver());
        actor.setRace(new Race());
        assertNotNull(actor.getDriver());
        assertNotNull(actor.getRace());
        assertNotNull(actor.getId());
        assertEquals(0, actor.getId().getDriverId());
        assertEquals(0, actor.getId().getRaceId());
    }

    @Test(expected = ChampionshipException.class)
    public void cannotSetNullDriver() throws ChampionshipException {
        actor.setDriver(null);
    }

    @Test(expected = ChampionshipException.class)
    public void cannotSetNullRace() throws ChampionshipException {
        actor.setRace(null);
    }
}
