package modele.dao.Commande;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entities.commande.Commande;
import modele.ModeleSQL;
import modele.dao.DAO;
import modele.dao.Categorie.MySQLCategorieDAO;

public class MySQLCommandeDAO implements DAO<Commande>{
	
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
			PreparedStatement query = ModeleSQL.getConnexion().prepareStatement("INSERT INTO Commande (id_commande, date_commande, id_client) VALUES (?, ?, ?))", Statement.RETURN_GENERATED_KEYS);
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
	public boolean update(Commande objetModife, Commande objetRemplacant) {
		try {
			PreparedStatement query = ModeleSQL.getConnexion().prepareStatement("UPDATE Commande SET date_commande = ?, id_client = ?  WHERE id_commande = ?");
			query.setDate(1, Date.valueOf(objetRemplacant.getDateCommande()));
			query.setInt(2, objetRemplacant.getIdClient());
			query.setInt(3, objetModife.getIdCommande());
			
			query.setInt(10, objetModife.getIdClient());
		
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
			PreparedStatement query = ModeleSQL.getConnexion().prepareStatement("DELETE FROM Commande WHERE id_commande = ?");
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

}