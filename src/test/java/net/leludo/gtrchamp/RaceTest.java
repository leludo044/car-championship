package net.leludo.gtrchamp;

import java.time.LocalDate;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Race test class.
 */
public class RaceTest {

    /** Actor of the test. */
    private Race acteur;

    /**
     * Launched method before each test.
     *
     * @throws Exception
     *             Raised exception on setup error
     */
    @Before
    public void setUp() throws Exception {
        acteur = new Race(null, new Track(), LocalDate.now());
    }

    /**
     * Default test.
     */
    @Test
    public void test() {
        Assert.assertNotNull(acteur.getTrack());
        Assert.assertEquals(0, acteur.competitorsCount());
    }

    /**
     * Assert that when a driver is registered then competitors count equals 1.
     *
     * @throws ChampionshipException
     *             Raised exception when there is an error during the
     *             registration of the driver
     */
    @Test
    public void testDriverRegistering() throws ChampionshipException {
        Competitor concurrent = registerDriver(1);
        Assert.assertEquals(1, acteur.competitorsCount());
        Assert.assertEquals("", concurrent.getDriver().getName());
    }

    /**
     * Assert that when a null driver is registered then an exception is raised.
     *
     * @throws ChampionshipException
     *             Raised exception due to null driver registration registration
     *             of the driver
     */
    @Test(expected = ChampionshipException.class)
    public void testNullDriverRegistering() throws ChampionshipException {
        acteur.signUp(null);
    }

    /**
     * Assert that when a driver is simply registered then the race is not
     * finished.
     *
     * @throws ChampionshipException
     *             Raised exception when there is an error during the
     *             registration of the driver
     */
    @Test
    public void testIsNotFinished() throws ChampionshipException {
        this.registerDriver(1);
        Assert.assertFalse(acteur.isFinished());
    }

    /**
     * Assert that when only one driver is registered and his arrival position
     * is set then the race is finished.
     *
     * @throws ChampionshipException
     *             Raised exception when there is an error during the
     *             registration of the driver
     */
    @Test
    public void testOneDriverRaceFinished() throws ChampionshipException {
        Competitor concurrent = this.registerDriver(1);
        concurrent.setArrivalPosition(1);
        Assert.assertTrue(acteur.isFinished());
    }

    /**
     * Assert that when only two drivers are registered and one has his arrival
     * position set then the race is not finished.
     *
     * @throws ChampionshipException
     *             Raised exception when there is an error during the
     *             registration of the driver
     */
    @Test
    public void testTwoDriversOneArrivalRaceNotFinished() throws ChampionshipException {
        Competitor concurrent1 = this.registerDriver(1);
        concurrent1.setArrivalPosition(1);
        this.registerDriver(2);
        Assert.assertFalse(acteur.isFinished());
    }

    /**
     * Assert that when two drivers are registered and their arrival positions
     * are set then the race is finished.
     *
     * @throws ChampionshipException
     *             Raised exception when there is an error during the
     *             registration of the driver
     */
    @Test
    public void testTwoDriversTwoArrivalsRaceNotFinished() throws ChampionshipException {
        Competitor concurrent1 = this.registerDriver(1);
        concurrent1.setArrivalPosition(1);
        Competitor concurrent2 = this.registerDriver(2);
        concurrent2.setArrivalPosition(2);
        Assert.assertTrue(acteur.isFinished());
    }

    /**
     * Assert that by default getting the results raises exception.
     *
     * @throws ChampionshipException
     *             Expected raised exception
     */
    @Test(expected = ChampionshipException.class)
    public void testNoDriverResultsException() throws ChampionshipException {
        // TODO Review this strange test
        acteur.results();
    }

    /**
     * Assert that when a driver is registered then getting the results raises
     * exception.
     *
     * @throws ChampionshipException
     *             Expected raised exception
     */
    @Test(expected = ChampionshipException.class)
    public void testOneRegisteredDriverResultsException() throws ChampionshipException {
        // TODO Review this strange test
        this.registerDriver(1);
        acteur.results();
    }

    /**
     * Assert that when a driver is registered and his arrival position is set
     * then the results count equals 1.
     *
     * @throws ChampionshipException
     *             Raised exception when there is an error during the
     *             registration of the driver
     */
    @Test
    public void testOneRegisteredDriverResults() throws ChampionshipException {
        Competitor concurrent = this.registerDriver(1);
        concurrent.setArrivalPosition(1);
        List<Competitor> classement = acteur.results();
        Assert.assertEquals(1, classement.size());
    }

    /**
     * Assert that when two drivers are registered and only one has his arrival
     * position set then an exception is raised.
     *
     * @throws ChampionshipException
     *             Expected raised exception
     */
    @Test(expected = ChampionshipException.class)
    public void testTwoRegisteredDriversResultsException()
            throws ChampionshipException {
        Competitor concurrent1 = this.registerDriver(1);
        concurrent1.setArrivalPosition(1);
        this.registerDriver(2);
        acteur.results();
    }

    /**
     * Assert that when two drivers are registered and their arrival positions
     * are set then the results count equals 2.
     *
     * @throws ChampionshipException
     *             Raised exception when there is an error during the
     *             registration of the driver
     */
    @Test
    public void testTwoRegisteredDriversResults() throws ChampionshipException {
        Competitor concurrent1 = this.registerDriver(1);
        concurrent1.setArrivalPosition(1);
        Competitor concurrent2 = this.registerDriver(2);
        concurrent2.setArrivalPosition(2);
        List<Competitor> classement = acteur.results();
        Assert.assertEquals(2, classement.size());
    }

    /**
     * Assert that when two drivers are registered and their arrival positions
     * are set and driver #1 is after driver #2 then the results count equals 2
     * and the first is driver #2 and the second is driver #1.
     *
     * @throws ChampionshipException
     *             Raised exception when there is an error during the
     *             registration of the driver
     */
    @Test
    public void testRegisteredDriversOrder() throws ChampionshipException {
        Competitor concurrent1 = this.registerDriver(1);
        concurrent1.setArrivalPosition(2);
        Competitor concurrent2 = this.registerDriver(2);
        concurrent2.setArrivalPosition(1);
        List<Competitor> classement = acteur.results();
        Assert.assertEquals(2, classement.size());
        Assert.assertEquals(concurrent2, classement.get(0));
        Assert.assertEquals(concurrent1, classement.get(1));
    }

    /**
     * Helper test class to register à driver with a given id for the race
     * corresponding to the actor of the test.
     *
     * @param id
     *            Id of the new registered driver
     * @return The competitor
     * @throws ChampionshipException
     *             Raised exception if there is an error during the registration
     *             of the driver
     */
    private Competitor registerDriver(final int id) throws ChampionshipException {
        Driver pilote = new Driver();
        pilote.setId(id);
        Competitor concurrent = acteur.signUp(pilote);
        return concurrent;
    }

}
