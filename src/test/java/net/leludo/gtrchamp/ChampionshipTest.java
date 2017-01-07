package net.leludo.gtrchamp;
/**
 *
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Championship test class.
 */
public class ChampionshipTest {
    /** Actor of the test. */
    private Championship chp;

    /**
     * Launched method before each test.
     * 
     * @throws Exception
     *             Raised exception on setup error
     */
    @Before
    public void setUp() throws Exception {
        chp = new Championship("JUnit", "gt", 1);
        assertNotNull(chp);
    }

    /**
     * Test method for
     * {@link net.leludo.gtrchamp.Championship#Championship(java.lang.String)}.
     */
    @Test
    public final void testChampionnat() {
        Championship jUnitChp = new Championship("JUnit", "gt", 1);
        assertEquals("JUnit", jUnitChp.getName());
    }

    /**
     * Test method for
     * {@link net.leludo.gtrchamp.Championship#planRace(net.leludo.gtrchamp.Track, java.util.Date)}
     * .
     */
    @Test
    public final void testPlanRace() {
        chp.planRace(new Track(), LocalDate.now());
        assertEquals(1, chp.getPlannedRaces().size());
    }

    /**
     * Test method for
     * {@link net.leludo.gtrchamp.Championship#results()}.
     */
    @Test
    @Ignore
    public final void testRendreClassement() {
        fail("Not yet implemented"); // TODO
    }

}
