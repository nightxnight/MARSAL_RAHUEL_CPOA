package test.dao.mysql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import dao.DAOFactory;
import dao.Persistance;
import entities.produit.Produit;

public class TestMySQLProduitDAO {

	private static Persistance PERSISTANCE = Persistance.MYSQL;
	
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
		Produit Produit = new Produit("Mario brosse", "Le meilleur pulls pour faire le ménage!", 19.99, "mariobrosse.png", 1);
		assertTrue(DAOFactory.getDAOFactory(PERSISTANCE).getProduitDAO().create(Produit));
	}
	
	@Test
	public void testUpdate() {
		Produit Produit = new Produit(2, "Eggman t'aimes pas", "Inspiré par la saga séga (il est plus fort que toi)", 141.5, "eggman.png", 1);
		try {
			assertTrue(DAOFactory.getDAOFactory(PERSISTANCE).getProduitDAO().update(Produit));
		} catch(IllegalArgumentException iae) {
			fail("Exception levee par erreur!");
		}
	}
	
	@Test
	public void testUpdateProduitIntrouvable() {
		Produit Produit = new Produit(-1, "", "", 0, "", 0);
		try {
			DAOFactory.getDAOFactory(PERSISTANCE).getProduitDAO().update(Produit);
			fail("On ne peut pas modifier un produit inexistant.");
		} catch(IllegalArgumentException iae) {
			//Normal
		}
	}
	
	@Test
	public void testDelete() {
		Produit Produit = new Produit(12, "", "", 0, "", 0);
		try {
			assertTrue(DAOFactory.getDAOFactory(PERSISTANCE).getProduitDAO().delete(Produit));
		} catch(IllegalArgumentException iae) {
			fail("Exception levee par erreur!");
		}
	}
	
	@Test
	public void testDeleteProduitIntrouvable() {
		Produit Produit = new Produit(-1, "", "", 0, "", 0);
		try {
			DAOFactory.getDAOFactory(PERSISTANCE).getProduitDAO().delete(Produit);
			fail("On ne peut pas supprimer un produit inexistant");
		} catch(IllegalArgumentException iae) {
			//Normal
		}
	}
	
	@Test
	public void testGetAll() {
		assertNotNull(DAOFactory.getDAOFactory(PERSISTANCE).getProduitDAO().getAll());
	}
	
	@Test
	public void testGetById() {
		try {
			assertEquals(DAOFactory.getDAOFactory(PERSISTANCE).getProduitDAO().getById(6), new Produit(6, "", "", 0, "", 0));
		} catch(IllegalArgumentException iae) {
			fail("Exception levee par erreur!");
		}
	}
	
	@Test
	public void testGetByIdClientIntrouvable() {
		try {
			DAOFactory.getDAOFactory(PERSISTANCE).getProduitDAO().getById(-1);
			fail("On ne peut pas obtenir un client dont l'id n'est pas enregistre");
		} catch(IllegalArgumentException iae) {
			//Normal
		}
	}
}
