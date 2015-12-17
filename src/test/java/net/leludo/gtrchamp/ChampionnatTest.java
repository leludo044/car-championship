package net.leludo.gtrchamp;
/**
 * 
 */

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author pmzn560
 */

public class ChampionnatTest {
    private Championnat chp;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        chp = new Championnat("JUnit");
        assertNotNull(chp);
    }

    /**
     * Test method for
     * {@link net.leludo.gtrchamp.Championnat#Championnat(java.lang.String)}.
     */
    @Test
    public final void testChampionnat() {
        Championnat jUnitChp = new Championnat("JUnit");
        assertEquals("JUnit", jUnitChp.getLibelle());
    }

    /**
     * Test method for
     * {@link net.leludo.gtrchamp.Championnat#organiserGrandPrix(net.leludo.gtrchamp.Circuit, java.util.Date)}
     * .
     */
    @Test
    public final void testOrganiserGrandPrix() {
        chp.organiserGrandPrix(new Circuit(), Calendar.getInstance().getTime());
        assertEquals(1, chp.getGrandsPrix().size());
    }

    /**
     * Test method for
     * {@link net.leludo.gtrchamp.Championnat#rendreClassement()}.
     */
    @Test
    @Ignore
    public final void testRendreClassement() {
        fail("Not yet implemented"); // TODO
    }

}
