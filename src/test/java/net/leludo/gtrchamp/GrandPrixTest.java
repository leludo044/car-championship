package net.leludo.gtrchamp;

import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class GrandPrixTest {

	GrandPrix acteur ;
	
	@Before
	public void setUp() throws Exception {
		acteur = new GrandPrix(new Circuit(), Calendar.getInstance().getTime()) ;
	}

	@Test
	public void test() {
		Assert.assertNotNull(acteur.getCircuit());
	}
	
	@Test
	public void testInscrirePilote() {
		Pilote pilote = new Pilote() ;
		Concurrent c = acteur.inscrire(pilote);
		Assert.assertEquals(1, acteur.getNbInscrits());
		Assert.assertEquals(pilote.nom, c.nom);
	}
	
	@Test
	public void testPositionnerSurGrille() {
		Pilote pilote = new Pilote();
		Concurrent concurrent = acteur.inscrire(pilote);
		acteur.positionnerSurGrille(concurrent, 1);
		Assert.assertEquals(1, acteur.getPotitionDepart(concurrent));
	}

}
