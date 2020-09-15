package modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entities.client.Client;
import entities.produit.Produit;

public abstract class ModeleSQL {
	
	private final static String dbURL = "jdbc:mysql://devbdd.iutmetz.univ-lorraine.fr:3306/";
	private final static String dbName = "marsal15u_cpoa";
	private final static String login = "marsal15u_appli";
	private final static String password = "vincentseum01";
	
	private static Connection connexion;
	
	public static boolean creerConnexion() {
		boolean connexionCree = true;
		connexion = null;
		try {
			String url = dbURL + dbName + "?serverTimezone=Europe/Paris";
			connexion = DriverManager.getConnection(url, login, password);
			System.out.println("Connexion � " + dbName + " �tablie.");
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la connexion � " + dbName);
			System.out.println(sqle.getMessage());
			connexionCree = false;
		}
		return connexionCree;
	}
	
	
	
	
	
	
	
	public static ArrayList<String> obtenirToutesCategories() {
		ArrayList<String> listeCategorie = null;
		
		try {
			PreparedStatement query = connexion.prepareStatement("SELECT titre FROM Categorie");
			ResultSet res = query.executeQuery();
			
			listeCategorie = new ArrayList<String>();
			while(res.next()) {
				listeCategorie.add(res.getString(1));
			}
			
			if(res != null ) res.close();
			if(query != null) query.close();
			
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requ�te \"obtenirToutesCategories\".");
			System.out.println("logs : " + sqle.getMessage());
		}
		
		return listeCategorie;
	}
	
	//Produit
	
	
	
	public static ArrayList<Produit> obtenirTousProduit() {
		ArrayList<Produit> listeProduit = null;
		try {
			PreparedStatement query = connexion.prepareStatement("SELECT * FROM Produit");
			
			ResultSet res = query.executeQuery();
			listeProduit = new ArrayList<Produit>();
			while(res.next()) {
				listeProduit.add(new Produit(res.getInt(1), res.getString(2), res.getString(3), res.getDouble(4), res.getString(5), res.getInt(6)));
			}
			
			if(res != null) res.close();
			if(query != null) query.close();
			
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requ�te \"obtenirTousProduit\".");
			System.out.println("logs : " + sqle.getMessage());	
		}
		return listeProduit;
	}


	
	public static void modifierClient(String nomClientModifie, String nouveauNomClient, String nouveauPrenomClient) {
		try {
			PreparedStatement query = connexion.prepareStatement("UPDATE Client SET nom = ?, prenom = ? WHERE nom = ?");
			query.setString(1, nouveauNomClient);
			query.setString(2, nouveauPrenomClient);
			query.setString(3, nomClientModifie);
		
		int nbLigne = query.executeUpdate();
		System.out.println(nbLigne + " ligne(s) modifi�e(s)");
		
		if(query != null) query.close();
		
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requ�te \"modifierCategorie\".");
			System.out.println("logs : " + sqle.getMessage());
		}		
	}

	
	public static ArrayList<Client> obtenirTousClients() {
		ArrayList<Client> listeClient = null;
		
		try {
			PreparedStatement query = connexion.prepareStatement("SELECT * FROM Client");
			ResultSet res = query.executeQuery();
			
			listeClient = new ArrayList<Client>();
			while(res.next()) {
				listeClient.add(new Client(res.getInt(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6)
						,res.getString(7), res.getString(8), res.getString(9), res.getString(10)));
			}
			
			if(res != null ) res.close();
			if(query != null) query.close();
			
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requ�te \"obtenirTousClients\".");
			System.out.println("logs : " + sqle.getMessage());
		}
		
		return listeClient;
	}
	
	public static Connection getConnexion() {
		return connexion;
	}
	
}
