package dao;

import dao.entities.CategorieDAO;
import dao.entities.ClientDAO;
import dao.entities.CommandeDAO;
import dao.entities.LigneCommandeDAO;
import dao.entities.ProduitDAO;
import dao.entities.listeMemoire.ListeMemoireDAOFactory;
import dao.entities.mysql.MySQLDAOFactory;

public abstract class DAOFactory {
	
	public static DAOFactory getDAOFactory (Persistance cible) {
		DAOFactory daoF = null;
		
		switch(cible) {
		case MYSQL:
			daoF = MySQLDAOFactory.getInstance(); break;
		
		case LISTEMEMOIRE:
			daoF = ListeMemoireDAOFactory.getInstance(); break;
		}
		
		return daoF;
	}
	
	public abstract boolean closeDAO();
	public abstract CategorieDAO getCategorieDAO();
	public abstract ClientDAO getClientDAO();
	public abstract ProduitDAO getProduitDAO();
	public abstract CommandeDAO getCommandeDAO();
	public abstract LigneCommandeDAO getLigneCommandeDAO();
}
