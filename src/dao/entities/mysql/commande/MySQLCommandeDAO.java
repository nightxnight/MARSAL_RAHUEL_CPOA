package dao.entities.mysql.commande;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entities.commande.Commande;
import dao.entities.CommandeDAO;
import dao.entities.mysql.MySQLDAOFactory;

public class MySQLCommandeDAO implements CommandeDAO{
	
	private static MySQLCommandeDAO instance;
	
	private MySQLCommandeDAO() {}
	
	public static MySQLCommandeDAO getInstance() {
		if (instance == null) {
			instance = new MySQLCommandeDAO();
		}
		return instance;
	}

	@Override
	public boolean create(Commande objet) {
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("INSERT INTO Commande (id_commande, date_commande, id_client) VALUES (?, ?, ?))", Statement.RETURN_GENERATED_KEYS);
			query.setInt(1, objet.getIdCommande());
			query.setDate(2, Date.valueOf(objet.getDateCommande()));
			query.setInt(3, objet.getIdClient());
			
			int nbLigne = query.executeUpdate();
			System.out.println(nbLigne + " ligne(s) ajout�e(s)");
			
			ResultSet res = query.getGeneratedKeys();
			while(res.next()) {
				System.out.println("Cl� g�n�r�e : " + res.getInt(1));
			}
			
			if(res != null) res.close();
			if(query != null) query.close();
			
			return nbLigne ==1;
			
			} catch (SQLException sqle) {
				System.out.println("Erreur lors de la requ�te \"ajouterCommande\".");
				System.out.println("logs : " + sqle.getMessage());
			}		
		return false;
	}

	@Override
	public boolean update(Commande objet) {
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("UPDATE Commande SET date_commande = ?, id_client = ?  WHERE id_commande = ?");
			query.setDate(1, Date.valueOf(objet.getDateCommande()));
			query.setInt(2, objet.getIdClient());
			
			query.setInt(3, objet.getIdCommande());
		
		int nbLigne = query.executeUpdate();
		System.out.println(nbLigne + " ligne(s) modifi�e(s)");
		
		if(query != null) query.close();
		
		return nbLigne == 1;
		
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requ�te \"modifierCommande\".");
			System.out.println("logs : " + sqle.getMessage());
		}				
		return false;
	}

	@Override
	public boolean delete(Commande objet) {
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("DELETE FROM Commande WHERE id_commande = ?");
			query.setInt(1, objet.getIdCommande());
			
			int nbLigne = query.executeUpdate();
			System.out.println(nbLigne + " ligne(s) supprim�e(s)");
			
			if(query != null) query.close();
			
			return nbLigne == 1;
			
			} catch (SQLException sqle) {
				System.out.println("Erreur lors de la requ�te \"supprimerCommande\".");
				System.out.println("logs : " + sqle.getMessage());
			}
		return false;
	}
	
	@Override
	public Commande getById(int id) {
		Commande commande = null;
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("SELECT * FROM Commande WHERE id_commande = ?");
			query.setInt(1, id);
			
			ResultSet res = query.executeQuery();
			
			while(res.next()) {
				commande = new Commande(res.getInt(1), res.getDate(2), res.getInt(3));
			}
			return commande;
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requ�te \"MYSQLDAOFactory_commande.getById\".");
		}
		return commande;
	}
	
	@Override
	public ArrayList<Commande> getAll() {
		ArrayList<Commande> listeCommande = null;
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("SELECT * FROM Commande");
			ResultSet res = query.executeQuery();
			
			listeCommande = new ArrayList<Commande>();	
			while(res.next()) {
				listeCommande.add(new Commande(res.getInt(1), res.getDate(2), res.getInt(3)));
			}
			return listeCommande;
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requete \"MySQLDAOFactory_Commande.getAll");
		}
		return listeCommande;
	}

}
