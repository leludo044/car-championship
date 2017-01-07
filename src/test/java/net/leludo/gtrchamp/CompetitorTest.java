package net.leludo.gtrchamp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Competitor test class.
 */
public class CompetitorTest {

    /** Actor of the test. */
    private Competitor actor;

    /**
     * Launched method before each test.
     *
     * @throws Exception
     *             Raised exception on setup error
     */
    @Before
    public void setUp() throws Exception {
        actor = new Competitor();
    }

    /**
     * Default test.
     */
    @Test
    public void test() {
        assertNotNull(actor);
        assertEquals(0, actor.getStartingPosition());
        assertEquals(0, actor.getArrivalPosition());
        assertNull(actor.getDriver());
        assertNull(actor.getRace());
    }

    /**
     * Assert that starting position 1 is a pole position.
     *
     * @throws ChampionshipException
     *             Raised exception if the starting position is incorrect
     */
    @Test
    public void testHasPolePosition() throws ChampionshipException {
        actor.setStartingPosition(1);
        assertTrue(actor.hasPolePosition());
    }

    /**
     * Assert that starting position 2 is not a pole position.
     *
     * @throws ChampionshipException
     *             Raised exception if the starting position is incorrect
     */
    @Test
    public void testNotHasPolePosition() throws ChampionshipException {
        actor.setStartingPosition(2);
        assertFalse(actor.hasPolePosition());
    }

    /**
     * Assert that arrival position 1 gives victory.
     *
     * @throws ChampionshipException
     *             Raised exception if the arrival is incorrect
     */
    @Test
    public void testIsWinner() throws ChampionshipException {
        actor.setArrivalPosition(1);
        assertTrue(actor.hasWon());
    }

    /**
     * Assert that arrival position 2 doesn't give victory.
     *
     * @throws ChampionshipException
     *             Raised exception if the arrival is incorrect
     */
    @Test
    public void testIsNotWinner() throws ChampionshipException {
        actor.setArrivalPosition(2);
        assertFalse(actor.hasWon());
    }

    /**
     * Assert that starting position 0 is incorrect. Championship exception must
     * be raised.
     *
     * @throws ChampionshipException
     *             Raised exception if the starting position is incorrect
     */
    @Test(expected = ChampionshipException.class)
    public void testStartingPositionToZero() throws ChampionshipException {
        actor.setStartingPosition(0);
    }

    /**
     * Assert that negative starting position is incorrect. Championship
     * exception must be raised.
     *
     * @throws ChampionshipException
     *             Raised exception if the starting position is incorrect
     */
    @Test(expected = ChampionshipException.class)
    public void testNegativeStartingPosition() throws ChampionshipException {
        actor.setStartingPosition(-1);
    }

    /**
     * Assert that arrival position 0 is incorrect. Championship exception must
     * be raised.
     *
     * @throws ChampionshipException
     *             Raised exception if the starting position is incorrect
     */
    @Test(expected = ChampionshipException.class)
    public void testArrivalPositionToZero() throws ChampionshipException {
        actor.setArrivalPosition(0);
    }

    /**
     * Assert that negative arrival position is incorrect. Championship
     * exception must be raised.
     *
     * @throws ChampionshipException
     *             Raised exception if the starting position is incorrect
     */
    @Test(expected = ChampionshipException.class)
    public void testNegativeArrivalPosition() throws ChampionshipException {
        actor.setArrivalPosition(-1);
    }

    /**
     * Assert that race number lesser than 1 is incorrect. Championship
     * exception must be raised.
     *
     * @throws ChampionshipException
     *             Raised exception if the starting position is incorrect
     */
    @Test(expected = ChampionshipException.class)
    public void testRaceNumberUnder1() throws ChampionshipException {
        actor.setRaceNumber(0);
    }

    /**
     * Assert that race number greater than 2 is incorrect. Championship
     * exception must be raised.
     *
     * @throws ChampionshipException
     *             Raised exception if the starting position is incorrect
     */
    @Test(expected = ChampionshipException.class)
    public void testRaceNumberAbove2() throws ChampionshipException {
        actor.setRaceNumber(0);
    }

    /**
     * Assert that a canceled race gives arrival position to -1.
     */
    @Test
    public void testCanceledRace() {
        actor.canceled();
        assertEquals(-1, actor.getArrivalPosition());
    }

    /**
     * Assert that if arrival position is unknown then the competitor hasn't
     * finished the race.
     */
    @Test
    public void testHasNotFinished() {
        assertFalse(actor.hasFinished());
    }

    /**
     * Assert that if arrival position is set then the competitor has finished
     * the race.
     *
     * @throws ChampionshipException
     *             Raised exception if the arrival position is incorrect
     */
    @Test
    public void testHasFinished() throws ChampionshipException {
        actor.setArrivalPosition(1);
        assertTrue(actor.hasFinished());
    }

    /**
     * Assert that if set a new driver the driver part of the competitor id
     * equals the given driver id.
     *
     * @throws ChampionshipException
     *             Raised exception if the given driver is null
     */
    @Test
    public void settingDriverMustUpdateId() throws ChampionshipException {
        actor.setDriver(new Driver());
        assertNotNull(actor.getDriver());
        assertNotNull(actor.getId());
        assertEquals(0, actor.getId().getDriverId());
    }

    /**
     * Assert that if set a new race the race part of the competitor id equals
     * the given race id.
     *
     * @throws ChampionshipException
     *             Raised exception if the given race is null
     */
    @Test
    public void settingRaceMustUpdateId() throws ChampionshipException {
        actor.setRace(new Race());
        assertNotNull(actor.getRace());
        assertNotNull(actor.getId());
        assertEquals(0, actor.getId().getRaceId());
    }

    /**
     * Assert that if set a new race number the race number part of the
     * competitor id equals the given race number.
     *
     * @throws ChampionshipException
     *             Raised exception if race number is incorrect
     */
    @Test
    public void settingRaceNumberMustUpdateId() throws ChampionshipException {
        actor.setRaceNumber(1);
        assertNotNull(actor.getId());
        assertEquals(1, actor.getRaceNumber());
        assertEquals(1, actor.getId().getRaceNumber());
    }

    /**
     * Assert that a driver cannot be null.
     *
     * @throws ChampionshipException
     *             Raised exception if the given driver is null
     */
    @Test(expected = ChampionshipException.class)
    public void cannotSetNullDriver() throws ChampionshipException {
        actor.setDriver(null);
    }

    /**
     * Assert that a race cannot be null.
     *
     * @throws ChampionshipException
     *             Raised exception if the given race is null
     */
    @Test(expected = ChampionshipException.class)
    public void cannotSetNullRace() throws ChampionshipException {
        actor.setRace(null);
    }
}
