package dao.entities.listeMemoire;

import dao.DAOFactory;
import dao.entities.CategorieDAO;
import dao.entities.ClientDAO;
import dao.entities.CommandeDAO;
import dao.entities.LigneCommandeDAO;
import dao.entities.ProduitDAO;
import dao.entities.listeMemoire.categorie.ListeMemoireCategorieDAO;
import dao.entities.listeMemoire.client.ListeMemoireClientDAO;
import dao.entities.listeMemoire.commande.ListeMemoireCommandeDAO;
import dao.entities.listeMemoire.ligneCommande.ListeMemoireLigneCommandeDAO;
import dao.entities.listeMemoire.produit.ListeMemoireProduitDAO;

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
	
	//TODO : Ajout de la possibilite de sauvegarder des configurations
	//--> Sauvegarde des donnees serialisees dans des fichiers
	//--> Chargement de donnees a partir d'un fichier .txt ou .ser
	@Override
	public boolean closeDAO() {
		if(instance == null) return true;
		else return true;
	}

}
