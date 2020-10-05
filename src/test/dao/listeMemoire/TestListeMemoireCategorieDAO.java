package test.dao.listeMemoire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import dao.DAOFactory;
import dao.Persistance;
import entities.categorie.Categorie;

public class TestListeMemoireCategorieDAO {
	
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
		Categorie categorie = new Categorie("Polaires", "lespolaires.png");
		assertTrue(DAOFactory.getDAOFactory(PERSISTANCE).getCategorieDAO().create(categorie));
	}
	
	@Test
	public void testUpdate() {
		Categorie categorie = new Categorie(2, "Echarpes", "lesecharpes.png");
		try {
			assertTrue(DAOFactory.getDAOFactory(PERSISTANCE).getCategorieDAO().update(categorie));
		} catch(IllegalArgumentException iae) {
			fail("Exception lancee par erreur!");
		}
	}
	
	@Test 
	public void testUpdateCategorieIntrouvable() {
		Categorie categorie = new Categorie(-1, "test", "test.png");
		try {
			DAOFactory.getDAOFactory(PERSISTANCE).getCategorieDAO().update(categorie);
			fail("On ne peut pas modifier une categorie inexistante");
		} catch(IllegalArgumentException iae) {
			//Normal
		}
	}
	
	@Test
	public void testDelete() {
		Categorie categorie = new Categorie(1, "", "");
		try {
			assertTrue(DAOFactory.getDAOFactory(PERSISTANCE).getCategorieDAO().delete(categorie));
		} catch(IllegalArgumentException iae) {
			fail("Exception lancée par erreur!");
		}
	}
	
	@Test
	public void testDeleteCategorieIntrouvable() {
		Categorie categorie = new Categorie(-1, "", "");
		try {
			DAOFactory.getDAOFactory(PERSISTANCE).getCategorieDAO().delete(categorie);
			fail("On ne peut pas supprimer une categorie inexistante");
		} catch(IllegalArgumentException iae) {
			//Normal
		}
	}
	
	@Test
	public void testGetAll() {
		assertNotNull(DAOFactory.getDAOFactory(PERSISTANCE).getCategorieDAO().getAll());
	}
	
	@Test
	public void testGetById() {
		try {
			assertEquals(DAOFactory.getDAOFactory(PERSISTANCE).getCategorieDAO().getById(3), new Categorie(3, "", ""));
		} catch(IllegalArgumentException iae) {
			fail("Exception lancée par erreur!");
		}
	}
	
	@Test
	public void testGetByIdCategorieIntrouvable() {
		try {
			DAOFactory.getDAOFactory(PERSISTANCE).getCategorieDAO().getById(-1);
			fail("On ne peut pas obtenir une categorie dont l'id n'est pas enregistre.");
		} catch(IllegalArgumentException iae) {
			//Normal");
		}
	}
}
