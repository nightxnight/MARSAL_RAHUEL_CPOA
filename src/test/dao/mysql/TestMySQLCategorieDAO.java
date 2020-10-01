package test.dao.mysql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.jupiter.api.Test;

import dao.DAOFactory;
import dao.Persistance;
import entities.categorie.Categorie;

public class TestMySQLCategorieDAO {
	
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
		Categorie categorie = new Categorie("Polaires", "lespolaires.png");
		assertTrue(DAOFactory.getDAOFactory(PERSISTANCE).getCategorieDAO().create(categorie));
	}
	
	@Test
	public void testUpdate() {
		Categorie categorie = new Categorie(2, "Echarpes", "lesecharpes.png");
		assertTrue(DAOFactory.getDAOFactory(PERSISTANCE).getCategorieDAO().update(categorie));
	}
	
	@Test
	public void testDelete() {
		Categorie categorie = new Categorie(1, "", "");
		assertTrue(DAOFactory.getDAOFactory(PERSISTANCE).getCategorieDAO().delete(categorie));
	}
	
	@Test
	public void testGetAll() {
		assertNotNull(DAOFactory.getDAOFactory(PERSISTANCE).getCategorieDAO().getAll());
	}
	
	@Test
	public void testGetById() {
		assertEquals(DAOFactory.getDAOFactory(PERSISTANCE).getCategorieDAO().getById(4), new Categorie(4, "", ""));
	}
}
