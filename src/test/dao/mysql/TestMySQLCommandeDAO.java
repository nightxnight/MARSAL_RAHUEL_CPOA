package test.dao.mysql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import dao.DAOFactory;
import dao.Persistance;
import entities.commande.Commande;

public class TestMySQLCommandeDAO {

private static Persistance PERSISTANCE = Persistance.MYSQL;
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
		assertTrue(DAOFactory.getDAOFactory(PERSISTANCE).getCommandeDAO().update(commande));
	}
	
	@Test
	public void testDelete() {
		Commande commande = new Commande(1, new Date(0), 0);
		assertTrue(DAOFactory.getDAOFactory(PERSISTANCE).getCommandeDAO().delete(commande));
	}
	
	@Test
	public void testGetAll() {
		assertNotNull(DAOFactory.getDAOFactory(PERSISTANCE).getCommandeDAO().getAll());
	}
	
	@Test
	public void testGetById() {
		assertEquals(DAOFactory.getDAOFactory(PERSISTANCE).getCommandeDAO().getById(3), new Commande(3, LocalDate.parse("01/01/0001", formatage), 0));
	}
}
