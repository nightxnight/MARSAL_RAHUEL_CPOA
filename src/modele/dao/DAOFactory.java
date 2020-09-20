package modele.dao;

import modele.dao.entities.CategorieDAO;
import modele.dao.entities.ClientDAO;
import modele.dao.entities.CommandeDAO;
import modele.dao.entities.LigneCommandeDAO;
import modele.dao.entities.ProduitDAO;
import modele.dao.entities.listeMemoire.ListeMemoireDAOFactory;
import modele.dao.entities.mysql.MySQLDAOFactory;

public abstract class DAOFactory {
	
	public static DAOFactory getDAOFactory (Persistance cible) {
		DAOFactory daoF = null;
		
		switch(cible) {
		case MYSQL:
			daoF = new MySQLDAOFactory(); break;
		
		case LISTEMEMOIRE:
			daoF = new ListeMemoireDAOFactory(); break;
		}
		
		return daoF;
	}
	
	public abstract CategorieDAO getCategorieDAO();
	public abstract ClientDAO getClientDAO();
	public abstract ProduitDAO getProduitDAO();
	public abstract CommandeDAO getCommandeDAO();
	public abstract LigneCommandeDAO getLigneCommandeDAO();
}
