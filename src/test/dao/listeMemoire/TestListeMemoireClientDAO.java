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
import entities.Client;

public class TestListeMemoireClientDAO {
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
		Client client = new Client("MORIZE", "Vincent", "vctmor@gmail.com", "seum79", "13", "rue de la HashMap", "90000", "Creuzot", "France");
		assertTrue(DAOFactory.getDAOFactory(PERSISTANCE).getClientDAO().create(client));
	}
	
	@Test
	public void testUpdate() {
		Client client = new Client(4, "LEMALADE", "Victor", "victorleM@gmail.com", "carole", "51", "rue des antibios", "41256", "Dunkerque", "France");
		try {
			assertTrue(DAOFactory.getDAOFactory(PERSISTANCE).getClientDAO().update(client));
		} catch(IllegalArgumentException iae) {
			fail("Exception lancee par erreur!");
		}
	}
	
	@Test
	public void testUpdateClientIntrouvable() {
		Client client = new Client(-1, "", "", "", "", "", "", "", "", "");
		try {
			DAOFactory.getDAOFactory(PERSISTANCE).getClientDAO().update(client);
			fail("On ne peut pas modifier un client inexistant.");
		} catch(IllegalArgumentException iae) {
			//Normal
		}
	}
	
	@Test
	public void testDelete() {
		Client client = new Client(1, "", "", "", "", "", "", "", "", "");
		try {
			assertTrue(DAOFactory.getDAOFactory(PERSISTANCE).getClientDAO().delete(client));
		} catch(IllegalArgumentException iae) {
			fail("Exception lancée par erreur!");
		}
	}
	
	@Test
	public void testDeleteClientIntrouvable() {
		Client client = new Client(-1, "", "", "", "", "", "", "", "", "");
		try {
			DAOFactory.getDAOFactory(PERSISTANCE).getClientDAO().delete(client);
			fail("On ne peut pas supprimer un client inexistant");
		} catch(IllegalArgumentException iae) {
			//Normal
		}
	}
	
	@Test
	public void testGetAll() {
		assertNotNull(DAOFactory.getDAOFactory(PERSISTANCE).getClientDAO().getAll());
	}
	
	@Test
	public void testGetById() {
		try {
			assertEquals(DAOFactory.getDAOFactory(PERSISTANCE).getClientDAO().getById(2), new Client(2, "", "", "", "", "", "", "", "", ""));
		} catch(IllegalArgumentException iae) {
			fail("Exception levee par erreur!");
		}
	}
	
	@Test
	public void testGetByIdClientIntrouvable() {
		try {
			DAOFactory.getDAOFactory(PERSISTANCE).getClientDAO().getById(-1);
			fail("On ne peut pas obtenir un client dont l'id n'est pas enregistre");
		} catch(IllegalArgumentException iae) {
			//Normal
		}
	}
}
