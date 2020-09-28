package modele.dao.entities.mysql;

import modele.dao.entities.ClientDAO;
import modele.dao.entities.CommandeDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
	
	private static MySQLDAOFactory instance;
	private final static String dbURL = "jdbc:mysql://devbdd.iutmetz.univ-lorraine.fr:3306/";
	private final static String dbName = "marsal15u_cpoa";
	private final static String login = "marsal15u_appli";
	private final static String password = "vincentseum01";
	
	private static Connection connexion;
	
	
	private MySQLDAOFactory() { 
		creerConnexion();
	}
	
	public static MySQLDAOFactory getInstance() {
		if(instance==null) instance = new MySQLDAOFactory();
		return instance;
	}
	
	public static boolean creerConnexion() {
		boolean connexionCree = true;
		connexion = null;
		try {
			String url = dbURL + dbName + "?serverTimezone=Europe/Paris";
			connexion = DriverManager.getConnection(url, login, password);
			System.out.println("Connexion ï¿½ " + dbName + " ï¿½tablie.");
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la connexion ï¿½ " + dbName);
			System.out.println(sqle.getMessage());
			connexionCree = false;
		}
		return connexionCree;
	}
	
	public static boolean fermerConnexion() {
		boolean connexionFerme = false;
		try {
			if(!connexion.isClosed())
				connexion.close();
			connexionFerme = true;
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la fermeture de la connexion ï¿½ " + dbName + ".");
		}
		return connexionFerme;
	}

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
	
	public static Connection getConnexion() {
		return connexion;
	}

	//Détruire l'instance pour rappeler le constructeur
	//Et ainsi rouvrir une connexion lorsqu'on reselectionne cette persistance
	@Override
	public boolean closeDAO() {
		if(instance==null) return true;
		else {
			instance = null;
			return fermerConnexion();
		}
	}
	
}
