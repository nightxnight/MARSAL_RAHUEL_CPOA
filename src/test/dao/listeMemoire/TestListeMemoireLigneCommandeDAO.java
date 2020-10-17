package test.dao.listeMemoire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import dao.DAOFactory;
import dao.Persistance;
import entities.LigneCommande;

public class TestListeMemoireLigneCommandeDAO {

	private static Persistance PERSISTANCE = Persistance.LISTEMEMOIRE;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		DAOFactory.getDAOFactory(PERSISTANCE);
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		DAOFactory.getDAOFactory(PERSISTANCE).closeDAO();
	}
	
	@Test
	public void testCreate() {
		LigneCommande LigneCommande = new LigneCommande(1, 12, 1, 35);
		assertTrue(DAOFactory.getDAOFactory(PERSISTANCE).getLigneCommandeDAO().create(LigneCommande));
	}
	
	@Test
	public void testUpdate() {
		LigneCommande LigneCommande = new LigneCommande(1, 6, 2, 15);
		try {
			assertTrue(DAOFactory.getDAOFactory(PERSISTANCE).getLigneCommandeDAO().update(LigneCommande));
		} catch(IllegalArgumentException iae) {
			fail("Exception levee par erreur!");
		}
	}
	
	@Test
	public void testUpdateLigneCommandeInexistante() {
		LigneCommande LigneCommande = new LigneCommande(-1, 6, 1, 15);
		try {
			DAOFactory.getDAOFactory(PERSISTANCE).getLigneCommandeDAO().update(LigneCommande);
			fail("On ne peut pas modifier une ligne de commande innexistante");
		} catch(IllegalArgumentException iae) {
			//normal
		}
	}
	
	@Test
	public void testDelete() {
		LigneCommande LigneCommande = new LigneCommande(2, 12, 4, 35);
		try {
			assertTrue(DAOFactory.getDAOFactory(PERSISTANCE).getLigneCommandeDAO().delete(LigneCommande));
		} catch(IllegalArgumentException iae) {
			fail("Exception lancee par erreur!");
		}
	}
	
	@Test
	public void testDeleteLigneCommandeIntrouvable() {
		LigneCommande LigneCommande = new LigneCommande(-1, 12, 4, 35);
		try {
			DAOFactory.getDAOFactory(PERSISTANCE).getLigneCommandeDAO().delete(LigneCommande);
			fail("Impossible de modifier une ligne de commande inexistante");
		} catch(IllegalArgumentException iae) {
			//Normal
		}
	}
	
	@Test
	public void testGetAll() {
		assertNotNull(DAOFactory.getDAOFactory(PERSISTANCE).getLigneCommandeDAO().getAll());
	}
	
	@Test
	public void testGetById() {
		ArrayList<LigneCommande> listeAttendu = new ArrayList<LigneCommande>();
		listeAttendu.add(new LigneCommande(3, 2, 1, 41.5));
		listeAttendu.add(new LigneCommande(3, 12, 8, 35));
		try {
			assertEquals(DAOFactory.getDAOFactory(PERSISTANCE).getLigneCommandeDAO().getById(3), listeAttendu);
		} catch(IllegalArgumentException iae) {
			fail("Exception levee par erreur!");
		}
	}
	
	@Test
	public void testGetByIdLigneCommandeIntrouvable() {
		try {
			DAOFactory.getDAOFactory(PERSISTANCE).getLigneCommandeDAO().getById(-1);
			fail("On ne peut pas obtenir la listes des ligne de commande concernant une commande dont l'id n'est pas enregistre");
		} catch(IllegalArgumentException iae) {
			//Normal
		}
	}
}
