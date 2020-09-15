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

public abstract class Modele {
	
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
	
	//Requ�te cat�gorie
	public static void ajouterCategorie(String nomCategorie, String fichierVisuel) {
		try {
		PreparedStatement query = connexion.prepareStatement("INSERT INTO Categorie (titre, visuel) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
		query.setString(1, nomCategorie);
		query.setString(2, fichierVisuel);
		
		int nbLigne = query.executeUpdate();
		System.out.println(nbLigne + " ligne(s) ajout�e(s)");
		
		ResultSet res = query.getGeneratedKeys();
		while(res.next()) {
			System.out.println("Cl� g�n�r�e : " + res.getInt(1));
		}
		
		if(res != null) res.close();
		if(query != null) query.close();
		
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requ�te \"ajouterCategorie\".");
			System.out.println("logs : " + sqle.getMessage());
		}		
	}
	
	
	public static void modifierCategorie(String nomCategorieModifiee, String nouveauNomCateg, String nouveauFichierVisuel) {
		try {
			PreparedStatement query = connexion.prepareStatement("UPDATE Categorie SET titre = ?, visuel = ? WHERE titre = ?");
			query.setString(1, nouveauNomCateg);
			query.setString(2, nouveauFichierVisuel);
			query.setString(3, nomCategorieModifiee);
		
		int nbLigne = query.executeUpdate();
		System.out.println(nbLigne + " ligne(s) modifi�e(s)");
		
		if(query != null) query.close();
		
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requ�te \"modifierCategorie\".");
			System.out.println("logs : " + sqle.getMessage());
		}		
	}
	
	
	public static void supprimerCategorie(String nomCategorieSupprimee) {
		try {
		PreparedStatement query = connexion.prepareStatement("DELETE FROM Categorie WHERE titre = ?");
		query.setString(1, nomCategorieSupprimee);
		
		int nbLigne = query.executeUpdate();
		System.out.println(nbLigne + " ligne(s) supprim�e(s)");
		
		if(query != null) query.close();
		
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requ�te \"supprimerCategorie\".");
			System.out.println("logs : " + sqle.getMessage());
		}	
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
	public static void ajouterProduit(Produit produitAjoute) {
		try {
			PreparedStatement query = connexion.prepareStatement("INSERT INTO Produit (nom, description, tarif, visuel, id_categorie) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			query.setString(1, produitAjoute.getNom());
			query.setString(2, produitAjoute.getDescription());
			query.setDouble(3, produitAjoute.getTarif());
			query.setString(4, produitAjoute.getVisuel());
			query.setInt(5, produitAjoute.getIdCategorie());
			
			int nbLigne = query.executeUpdate();
			System.out.println(nbLigne + " ligne(s) ajout�e(s)");
			
			ResultSet res = query.getGeneratedKeys();
			while(res.next()) {
				System.out.println("Cl� g�n�r�e : " + res.getInt(1));
			}
			
			if(res != null) res.close();
			if(query != null) query.close();
			
			
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requ�te \"ajouterProduit\".");
			System.out.println("logs : " + sqle.getMessage());
		}
	}
	
	public static void modifierProduit(Produit produitModifie, Produit produitRemplacant) {
		try {
			PreparedStatement query = connexion.prepareStatement("UPDATE Produit SET nom = ?, description = ?, tarif = ?, visuel = ?, id_categorie = ? WHERE id_produit = ?");
			query.setString(1, produitRemplacant.getNom());
			query.setString(2, produitRemplacant.getDescription());
			query.setDouble(3, produitRemplacant.getTarif());
			query.setString(4, produitRemplacant.getVisuel());
			query.setInt(5, produitRemplacant.getIdCategorie());
			query.setInt(6, produitModifie.getId());
			
			int nbLigne = query.executeUpdate();
			System.out.println(nbLigne + " ligne(s) modifi�e(s)");

			if(query != null) query.close();
			
		
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requ�te \"modifierProduit\".");
			System.out.println("logs : " + sqle.getMessage());
		}
	}
	
	public static void supprimerProduit(Produit produitSupprime) {
		try {
			PreparedStatement query = connexion.prepareStatement("DELETE FROM Produit WHERE id_produit = ?");
			query.setInt(1, produitSupprime.getId());
			
			int nbLigne = query.executeUpdate();
			System.out.println(nbLigne + " ligne(s) supprim�e(s)");
			
			if(query != null) query.close();
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requ�te \"supprimerProduit\".");
			System.out.println("logs : " + sqle.getMessage());		
		}
	}
	
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

	public static void ajouterClient(String nomClient, String prenomClient) {
		try {
		PreparedStatement query = connexion.prepareStatement("INSERT INTO CLient (nom, prenom) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
		query.setString(1, nomClient);
		query.setString(2, prenomClient);
		
		int nbLigne = query.executeUpdate();
		System.out.println(nbLigne + " ligne(s) ajout�e(s)");
		
		ResultSet res = query.getGeneratedKeys();
		while(res.next()) {
			System.out.println("Cl� g�n�r�e : " + res.getInt(1));
		}
		
		if(res != null) res.close();
		if(query != null) query.close();
		
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requ�te \"ajouterClient\".");
			System.out.println("logs : " + sqle.getMessage());
		}		
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

	public static void modifierClient(Client client, String nouveauNomClient, String nouveauPrenomClient) {
		try {
			PreparedStatement query = connexion.prepareStatement("UPDATE Client SET nom = ?, prenom = ? WHERE nom = ? AND prenom = ?");
			query.setString(1, nouveauNomClient);
			query.setString(2, nouveauPrenomClient);
			query.setString(3, client.getNom());
			query.setString(4, client.getPrenom());
		
		int nbLigne = query.executeUpdate();
		System.out.println(nbLigne + " ligne(s) modifi�e(s)");
		
		if(query != null) query.close();
		
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requ�te \"modifierClient\".");
			System.out.println("logs : " + sqle.getMessage());
		}		
	}
	
	public static void supprimerClient(String nomClientSupprime) {
		try {
		PreparedStatement query = connexion.prepareStatement("DELETE FROM Client WHERE nom = ?");
		query.setString(1, nomClientSupprime);
		
		int nbLigne = query.executeUpdate();
		System.out.println(nbLigne + " ligne(s) supprim�e(s)");
		
		if(query != null) query.close();
		
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requ�te \"supprimerCategorie\".");
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
