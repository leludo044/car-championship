package net.leludo.gtrchamp;

import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class GrandPrixTest {

	GrandPrix acteur;

	@Before
	public void setUp() throws Exception {
		acteur = new GrandPrix(new Circuit(), Calendar.getInstance().getTime());
	}

	@Test
	public void test() {
		Assert.assertNotNull(acteur.getCircuit());
	}

	@Test
	public void testInscrirePilote() throws ChampionnatException {
		Concurrent concurrent = inscrirePilote();
		Assert.assertEquals(1, acteur.getNbInscrits());
		Assert.assertEquals("", concurrent.nom);
	}

	@Test(expected = ChampionnatException.class)
	public void testInscrirePiloteNull() throws ChampionnatException {
		Concurrent concurrent = acteur.inscrire(null);
	}

	@Test
	public void testPositionnerPiloteSurGrille() throws ChampionnatException {
		Concurrent concurrent = inscrirePilote();
		acteur.positionnerSurGrille(concurrent, 1);
		Assert.assertTrue(concurrent.hasPolePosition());
	}

	@Test(expected = ChampionnatException.class)
	public void testPositionnerPiloteNullSurGrille()
			throws ChampionnatException {
		acteur.positionnerSurGrille(null, 1);
	}

	private Concurrent inscrirePilote() throws ChampionnatException {
		Pilote pilote = new Pilote();
		Concurrent concurrent = acteur.inscrire(pilote);
		return concurrent;
	}

}
