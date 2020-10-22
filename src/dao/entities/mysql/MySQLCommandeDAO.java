package dao.entities.mysql;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dao.entities.CommandeDAO;
import entities.Commande;

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
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("INSERT INTO Commande (id_commande, date_commande, id_client) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			query.setInt(1, objet.getIdCommande());
			query.setDate(2, Date.valueOf(objet.getDateCommande()));
			query.setInt(3, objet.getIdClient());
			
			int nbLigne = query.executeUpdate();
			System.out.println(nbLigne + " ligne(s) ajout�e(s)");
			
			ResultSet res = query.getGeneratedKeys();
			
			if(res.next()) {
				System.out.println("Cle generee : " + res.getInt(1));
				objet.setIdCommande(res.getInt(1));
			}
			
			if(res != null) res.close();
			if(query != null) query.close();
			
			return nbLigne ==1;
			
			} catch (SQLException sqle) {
				System.out.println("Erreur lors de la requete \"ajouterCommande\".");
				System.out.println("logs : " + sqle.getMessage());
			}		
		return false;
	}

	@Override
	public boolean update(Commande objet) throws IllegalArgumentException {
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("UPDATE Commande SET date_commande = ?, id_client = ?  WHERE id_commande = ?");
			query.setDate(1, Date.valueOf(objet.getDateCommande()));
			query.setInt(2, objet.getIdClient());
			
			query.setInt(3, objet.getIdCommande());
		
			int nbLigne = query.executeUpdate();
			System.out.println(nbLigne + " ligne(s) modifiee(s)");
			
			if(query != null) query.close();
			
			if(nbLigne == 1) return true;
			else throw new IllegalArgumentException("La commande que vous essayez de modifier est introuvable.");
		
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requete \"modifierCommande\".");
			System.out.println("logs : " + sqle.getMessage());
		}				
		return false;
	}

	@Override
	public boolean delete(Commande objet) throws IllegalArgumentException {
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("DELETE FROM Commande WHERE id_commande = ?");
			query.setInt(1, objet.getIdCommande());
			
			int nbLigne = query.executeUpdate();
			System.out.println(nbLigne + " ligne(s) supprimee(s)");
			
			if(query != null) query.close();
			
			if(nbLigne == 1) return true;
			else throw new IllegalArgumentException("La commande que vous essayez de supprimer est introuvable");
			
			} catch (SQLException sqle) {
				System.out.println("Erreur lors de la requete \"supprimerCommande\".");
				System.out.println("logs : " + sqle.getMessage());
			}
		return false;
	}
	
	@Override
	public Commande getById(int id) throws IllegalArgumentException {
		Commande commande = null;
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("SELECT * FROM Commande WHERE id_commande = ?");
			query.setInt(1, id);
			
			ResultSet res = query.executeQuery();
			
			if(res.next()) commande = new Commande(res.getInt(1), res.getDate(2), res.getInt(3));
			else throw new IllegalArgumentException("La commande recherchee est introuvable.");
			return commande;
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requete \"MYSQLDAOFactory_commande.getById\".");
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

	@Override
	public ArrayList<Commande> research(Commande commandeRecherchee) {
		ArrayList<Commande> listeCommande = null;
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("SELECT * FROM Commande WHERE id_commande LIKE '%?%' AND id_client LIKE '%?%'");
			String idCommandeStr = (commandeRecherchee.getIdCommande() != -1) ? String.valueOf(commandeRecherchee.getIdCommande()) : "";
			String idClientStr = (commandeRecherchee.getIdClient() != -1) ? String.valueOf(commandeRecherchee.getDateCommande()) : "";

			query.setString(1, idCommandeStr);
			query.setString(2, idClientStr);
			
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
