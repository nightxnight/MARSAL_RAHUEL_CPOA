package test.dao.mysql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
		assertTrue(DAOFactory.getDAOFactory(PERSISTANCE).getProduitDAO().update(Produit));
	}
	
	@Test
	public void testDelete() {
		Produit Produit = new Produit(12, "", "", 0, "", 0);
		assertTrue(DAOFactory.getDAOFactory(PERSISTANCE).getProduitDAO().delete(Produit));
	}
	
	@Test
	public void testGetAll() {
		assertNotNull(DAOFactory.getDAOFactory(PERSISTANCE).getProduitDAO().getAll());
	}
	
	@Test
	public void testGetById() {
		assertEquals(DAOFactory.getDAOFactory(PERSISTANCE).getProduitDAO().getById(6), new Produit(6, "", "", 0, "", 0));
	}
}
