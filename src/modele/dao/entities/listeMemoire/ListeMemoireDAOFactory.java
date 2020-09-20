package modele.dao.entities.listeMemoire;

import modele.dao.DAOFactory;
import modele.dao.entities.CategorieDAO;
import modele.dao.entities.ClientDAO;
import modele.dao.entities.CommandeDAO;
import modele.dao.entities.LigneCommandeDAO;
import modele.dao.entities.ProduitDAO;
import modele.dao.entities.listeMemoire.categorie.ListeMemoireCategorieDAO;
import modele.dao.entities.listeMemoire.client.ListeMemoireClientDAO;
import modele.dao.entities.listeMemoire.commande.ListeMemoireCommandeDAO;
import modele.dao.entities.listeMemoire.ligneCommande.ListeMemoireLigneCommandeDAO;
import modele.dao.entities.listeMemoire.produit.ListeMemoireProduitDAO;

public class ListeMemoireDAOFactory extends DAOFactory {
	
	private static ListeMemoireDAOFactory instance;
	
	private ListeMemoireDAOFactory() { }
	
	public static ListeMemoireDAOFactory getInstance() {
		if(instance==null) instance = new ListeMemoireDAOFactory();
		return instance;
	}
	
	@Override
	public CategorieDAO getCategorieDAO() {
		return ListeMemoireCategorieDAO.getInstance();
	}

	@Override
	public ClientDAO getClientDAO() {
		return ListeMemoireClientDAO.getInstance();
	}

	@Override
	public ProduitDAO getProduitDAO() {
		return ListeMemoireProduitDAO.getInstance();
	}

	@Override
	public CommandeDAO getCommandeDAO() {
		return ListeMemoireCommandeDAO.getInstance();
	}

	@Override
	public LigneCommandeDAO getLigneCommandeDAO() {
		return ListeMemoireLigneCommandeDAO.getInstance();
	}

}
