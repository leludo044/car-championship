package net.leludo.gtrchamp;

import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
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
		Assert.assertEquals(0, acteur.getNbInscrits());
	}

	@Test
	public void testInscrirePilote() throws ChampionnatException {
		Concurrent concurrent = inscrirePilote(1);
		Assert.assertEquals(1, acteur.getNbInscrits());
		Assert.assertEquals("", concurrent.getPilote().getNom());
	}

	@Test(expected = ChampionnatException.class)
	public void testInscrirePiloteNull() throws ChampionnatException {
		Concurrent concurrent = acteur.inscrire(null);
	}

	@Test
	public void testIsNotTermine() throws ChampionnatException {
		this.inscrirePilote(1) ;
		Assert.assertFalse(acteur.isTermine());
	}
	
	@Test
	public void testIsTerminePour1Concurrent() throws ChampionnatException {
		Concurrent concurrent = this.inscrirePilote(1) ;
		concurrent.setPositionArrivee(1);
		Assert.assertTrue(acteur.isTermine());
	}
	@Test
	public void testIsNotTerminePour1Concurrent() throws ChampionnatException {
		Concurrent concurrent1 = this.inscrirePilote(1) ;
		concurrent1.setPositionArrivee(1);
		Concurrent concurrent2 = this.inscrirePilote(2) ;
		Assert.assertFalse(acteur.isTermine());
	}
	@Test
	public void testIsTerminePour2Concurrents() throws ChampionnatException {
		Concurrent concurrent1 = this.inscrirePilote(1) ;
		concurrent1.setPositionArrivee(1);
		Concurrent concurrent2 = this.inscrirePilote(2) ;
		concurrent2.setPositionArrivee(2);
		Assert.assertTrue(acteur.isTermine());
	}
	
	@Test(expected=ChampionnatException.class)
	public void testRendreClassementACreation() throws ChampionnatException {
		acteur.rendreClassement();		
	}
	@Test(expected=ChampionnatException.class)
	public void testRendreClassementPourGrandsPrixNonTermine() throws ChampionnatException {
		this.inscrirePilote(1) ;
		acteur.rendreClassement();		
	}
	@Test
	public void testRendreClassementPour1Concurrent() throws ChampionnatException {
		Concurrent concurrent = this.inscrirePilote(1) ;
		concurrent.setPositionArrivee(1);
		List<Concurrent> classement = acteur.rendreClassement();
		Assert.assertEquals(1, classement.size());
	}
	@Test(expected=ChampionnatException.class)
	public void testRendreClassementPourPlusieursConcurrentsNonTermine() throws ChampionnatException {
		Concurrent concurrent1 = this.inscrirePilote(1) ;
		concurrent1.setPositionArrivee(1);
		Concurrent concurrent2 = this.inscrirePilote(2) ;
		List<Concurrent> classement = acteur.rendreClassement();
	}
	@Test
	public void testRendreClassementPourPlusieursConcurrentsTermine() throws ChampionnatException {
		Concurrent concurrent1 = this.inscrirePilote(1) ;
		concurrent1.setPositionArrivee(1);
		Concurrent concurrent2 = this.inscrirePilote(2) ;
		concurrent2.setPositionArrivee(2);
		List<Concurrent> classement = acteur.rendreClassement();
		Assert.assertEquals(2, classement.size());
	}
	@Test
	public void testRendreClassementPourPlusieursConcurrentsTrie() throws ChampionnatException {
		Concurrent concurrent1 = this.inscrirePilote(1) ;
		concurrent1.setPositionArrivee(2);
		Concurrent concurrent2 = this.inscrirePilote(2) ;
		concurrent2.setPositionArrivee(1);
		List<Concurrent> classement = acteur.rendreClassement();
		Assert.assertEquals(2, classement.size());
		Assert.assertEquals(concurrent2, classement.get(0));
		Assert.assertEquals(concurrent1, classement.get(1));
	}
	
	private Concurrent inscrirePilote(int id) throws ChampionnatException {
		Pilote pilote = new Pilote();
		pilote.setId(id);
		Concurrent concurrent = acteur.inscrire(pilote);
		return concurrent;
	}

}
