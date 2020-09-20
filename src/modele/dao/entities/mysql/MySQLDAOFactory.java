package modele.dao.entities.mysql;

import modele.dao.entities.ClientDAO;
import modele.dao.entities.CommandeDAO;
import modele.dao.DAOFactory;
import modele.dao.entities.LigneCommandeDAO;
import modele.dao.entities.ProduitDAO;
import modele.dao.entities.CategorieDAO;
import modele.dao.entities.mysql.categorie.MySQLCategorieDAO;
import modele.dao.entities.mysql.client.MySQLClientDAO;
import modele.dao.entities.mysql.commande.MySQLCommandeDAO;
import modele.dao.entities.mysql.ligneCommande.MySQLLigneCommandeDAO;
import modele.dao.entities.mysql.produit.MySQLProduitDAO;

public class MySQLDAOFactory extends DAOFactory{

	@Override
	public CategorieDAO getCategorieDAO() {
		return MySQLCategorieDAO.getInstance();
	}

	@Override
	public ClientDAO getClientDAO() {
		return MySQLClientDAO.getInstance();
	}

	@Override
	public ProduitDAO getProduitDAO() {
		return MySQLProduitDAO.getInstance();
	}

	@Override
	public CommandeDAO getCommandeDAO() {
		return MySQLCommandeDAO.getInstance();
	}

	@Override
	public LigneCommandeDAO getLigneCommandeDAO() {
		return MySQLLigneCommandeDAO.getInstance();
	}
	
}
