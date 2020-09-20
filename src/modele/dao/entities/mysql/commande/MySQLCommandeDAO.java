package modele.dao.entities.mysql.commande;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entities.commande.Commande;
import modele.dao.entities.CommandeDAO;
import modele.dao.entities.mysql.MySQLDAOFactory;

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
			System.out.println(nbLigne + " ligne(s) ajoutï¿½e(s)");
			
			ResultSet res = query.getGeneratedKeys();
			while(res.next()) {
				System.out.println("Clï¿½ gï¿½nï¿½rï¿½e : " + res.getInt(1));
			}
			
			if(res != null) res.close();
			if(query != null) query.close();
			
			return nbLigne ==1;
			
			} catch (SQLException sqle) {
				System.out.println("Erreur lors de la requï¿½te \"ajouterCommande\".");
				System.out.println("logs : " + sqle.getMessage());
			}		
		return false;
	}

	@Override
	public boolean update(Commande objetModife, Commande objetRemplacant) {
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("UPDATE Commande SET date_commande = ?, id_client = ?  WHERE id_commande = ?");
			query.setDate(1, Date.valueOf(objetRemplacant.getDateCommande()));
			query.setInt(2, objetRemplacant.getIdClient());
			query.setInt(3, objetModife.getIdCommande());
			
			query.setInt(10, objetModife.getIdClient());
		
		int nbLigne = query.executeUpdate();
		System.out.println(nbLigne + " ligne(s) modifiï¿½e(s)");
		
		if(query != null) query.close();
		
		return nbLigne == 1;
		
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requï¿½te \"modifierCommande\".");
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
			System.out.println(nbLigne + " ligne(s) supprimï¿½e(s)");
			
			if(query != null) query.close();
			
			return nbLigne == 1;
			
			} catch (SQLException sqle) {
				System.out.println("Erreur lors de la requï¿½te \"supprimerCommande\".");
				System.out.println("logs : " + sqle.getMessage());
			}
		return false;
	}
	
	@Override
	public ArrayList<Commande> getAll() {
		ArrayList<Commande> listeCommande = null;
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("SELECT * FROM Categorie");
			ResultSet res = query.executeQuery();
			
			listeCommande = new ArrayList<Commande>();	
			while(res.next()) {
				listeCommande.add(new Commande(res.getInt(1), res.getDate(2), res.getInt(3)));
			}
			return listeCommande;
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requête \"MySQLDAOFactory_Commande.getAll");
		}
		return listeCommande;
	}

}
