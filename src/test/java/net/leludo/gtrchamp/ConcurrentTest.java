package net.leludo.gtrchamp;

import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class ConcurrentTest {

	Concurrent acteur;

	@Before
	public void setUp() throws Exception {
		acteur = new Concurrent();
	}

	@Test
	public void test() {
		Assert.assertNotNull(acteur);
		Assert.assertEquals(0, acteur.getPositionDepart());
		Assert.assertEquals(0, acteur.getPositionArrivee());
	}

	@Test
	public void testHasPolePosition() throws ChampionnatException {
		acteur.setPositionDepart(1);
		Assert.assertTrue(acteur.hasPolePosition());
	}

	@Test
	public void testNotHasPolePosition() throws ChampionnatException {
		acteur.setPositionDepart(2);
		Assert.assertFalse(acteur.hasPolePosition());
	}

	@Test
	public void testIsVainqueur() throws ChampionnatException {
		acteur.setPositionArrivee(1);
		Assert.assertTrue(acteur.isVainqueur());
	}

	@Test
	public void testNotIsVainqueur() throws ChampionnatException {
		acteur.setPositionArrivee(2);
		Assert.assertFalse(acteur.isVainqueur());
	}

	@Test(expected = ChampionnatException.class)
	public void testPositionDepartZero() throws ChampionnatException {
		acteur.setPositionDepart(0);
	}

	@Test(expected = ChampionnatException.class)
	public void testPositionDepartNegative() throws ChampionnatException {
		acteur.setPositionDepart(-1);
	}

	@Test(expected = ChampionnatException.class)
	public void testPositionArriveeZero() throws ChampionnatException {
		acteur.setPositionArrivee(0);
	}

	@Test(expected = ChampionnatException.class)
	public void testPositionArriveeNegative() throws ChampionnatException {
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
	public void testHasTermine() throws ChampionnatException {
		acteur.setPositionArrivee(1);
		Assert.assertTrue(acteur.hasTermine());
	}

}
