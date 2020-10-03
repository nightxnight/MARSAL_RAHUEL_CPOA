package test.dao.listeMemoire;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import dao.DAOFactory;
import dao.Persistance;
import entities.commande.Commande;

public class TestListeMemoireCommandeDAO {

	private static Persistance PERSISTANCE = Persistance.LISTEMEMOIRE;
	private static DateTimeFormatter formatage = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
		Commande commande = new Commande(LocalDate.parse("26/09/2020", formatage), 2);
		assertTrue(DAOFactory.getDAOFactory(PERSISTANCE).getCommandeDAO().create(commande));
	}
	
	@Test
	public void testUpdate() {
		Commande commande = new Commande(2, LocalDate.parse("18/09/2020", formatage), 1);
		try {
			assertTrue(DAOFactory.getDAOFactory(PERSISTANCE).getCommandeDAO().update(commande));
		} catch(IllegalArgumentException iae) {
			fail("Exception lancee par erreur!");
		}
	}
	
	@Test
	public void testUpdateCommandeInexistante() {
		Commande commande = new Commande(-1, LocalDate.parse("01/01/1970", formatage), 1);
		try {
			DAOFactory.getDAOFactory(PERSISTANCE).getCommandeDAO().update(commande);
			fail("On ne peut pas modifier une commande inexistante");
		} catch(IllegalArgumentException iae) {
			//Normal
		}
	}
	
	@Test
	public void testDelete() {
		Commande commande = new Commande(1, LocalDate.parse("01/01/1970", formatage), 0);
		try {
			assertTrue(DAOFactory.getDAOFactory(PERSISTANCE).getCommandeDAO().delete(commande));
		} catch (IllegalArgumentException iae) {
			fail("Exception lancee par erreur!");
		}
	}
	
	@Test
	public void testDeleteCommandeIntrouvable() {
		Commande commande = new Commande(-1, LocalDate.parse("01/01/1970", formatage), 0);
		try {
			DAOFactory.getDAOFactory(PERSISTANCE).getCommandeDAO().delete(commande);
			fail("On ne peut pas supprimer une commande inexistante.");
		} catch (IllegalArgumentException iae) {
			//Normal
		}
	}
	
	@Test
	public void testGetAll() {
		assertNotNull(DAOFactory.getDAOFactory(PERSISTANCE).getCommandeDAO().getAll());
	}
	
	@Test
	public void testGetById() {
		assertEquals(DAOFactory.getDAOFactory(PERSISTANCE).getCommandeDAO().getById(3), new Commande(3, LocalDate.parse("01/01/1970", formatage), 0));
	}
}
