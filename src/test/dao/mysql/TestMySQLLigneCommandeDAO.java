package test.dao.mysql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import dao.DAOFactory;
import dao.Persistance;
import entities.ligneCommande.LigneCommande;

public class TestMySQLLigneCommandeDAO {

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
		LigneCommande LigneCommande = new LigneCommande(1, 12, 1, 35);
		assertTrue(DAOFactory.getDAOFactory(PERSISTANCE).getLigneCommandeDAO().create(LigneCommande));
	}
	
	@Test
	public void testUpdate() {
		LigneCommande LigneCommande = new LigneCommande(1, 6, 2, 15);
		assertTrue(DAOFactory.getDAOFactory(PERSISTANCE).getLigneCommandeDAO().update(LigneCommande));
	}
	
	@Test
	public void testDelete() {
		LigneCommande LigneCommande = new LigneCommande(2, 12, 4, 35);
		assertTrue(DAOFactory.getDAOFactory(PERSISTANCE).getLigneCommandeDAO().delete(LigneCommande));
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
		assertEquals(DAOFactory.getDAOFactory(PERSISTANCE).getLigneCommandeDAO().getById(3), listeAttendu);
	}
}
