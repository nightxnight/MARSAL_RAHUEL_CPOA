package modele.dao.entities.mysql.produit;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entities.categorie.Categorie;
import entities.produit.Produit;
import modele.dao.entities.ProduitDAO;
import modele.dao.entities.mysql.MySQLDAOFactory;

public class MySQLProduitDAO implements ProduitDAO{
	
	private static MySQLProduitDAO instance;
	
	private MySQLProduitDAO() { }
	
	public static MySQLProduitDAO getInstance() {
		if(instance==null) instance = new MySQLProduitDAO();
		return instance;
	}

	@Override
	public boolean create(Produit objet) {
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("INSERT INTO Produit (nom, description, tarif, visuel, id_categorie) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			query.setString(1, objet.getNom());
			query.setString(2, objet.getDescription());
			query.setDouble(3, objet.getTarif());
			query.setString(4, objet.getVisuel());
			query.setInt(5, objet.getIdCategorie());
			
			int nbLigne = query.executeUpdate();
			System.out.println(nbLigne + " ligne(s) ajout�e(s)");
			
			ResultSet res = query.getGeneratedKeys();
			while(res.next()) {
				System.out.println("Cl� g�n�r�e : " + res.getInt(1));
			}
			
			if(res != null) res.close();
			if(query != null) query.close();
			
			return nbLigne==1;
			
			
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requ�te \"ajouterProduit\".");
			System.out.println("logs : " + sqle.getMessage());
		}		
		return false;
	}

	@Override
	public boolean update(int idObjetModifie, Produit objetRemplacant) {
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("UPDATE Produit SET nom = ?, description = ?, tarif = ?, visuel = ?, id_categorie = ? WHERE id_produit = ?");
			query.setString(1, objetRemplacant.getNom());
			query.setString(2, objetRemplacant.getDescription());
			query.setDouble(3, objetRemplacant.getTarif());
			query.setString(4, objetRemplacant.getVisuel());
			query.setInt(5, objetRemplacant.getIdCategorie());
			query.setInt(6, idObjetModifie);
			
			int nbLigne = query.executeUpdate();
			System.out.println(nbLigne + " ligne(s) modifi�e(s)");

			if(query != null) query.close();
			
			return nbLigne == 1;
			
		
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requ�te \"modifierProduit\".");
			System.out.println("logs : " + sqle.getMessage());
		}		return false;
	}

	@Override
	public boolean delete(Produit objet) {
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("DELETE FROM Produit WHERE id_produit = ?");
			query.setInt(1, objet.getId());
			
			int nbLigne = query.executeUpdate();
			System.out.println(nbLigne + " ligne(s) supprim�e(s)");
			
			if(query != null) query.close();
			
			return nbLigne ==1;
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requ�te \"supprimerProduit\".");
			System.out.println("logs : " + sqle.getMessage());		
		}		return false;
	}
	
	@Override
	public ArrayList<Produit> getAll() {
		ArrayList<Produit> listeProduit = null;
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("SELECT * FROM Produit");
			ResultSet res = query.executeQuery();
			
			listeProduit = new ArrayList<Produit>();	
			while(res.next()) {
				listeProduit.add(new Produit(res.getInt(1), res.getString(2), res.getString(3), res.getDouble(4), res.getString(5), res.getInt(6)));
			}
			return listeProduit;
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requ�te \"MySQLDAOFactory_Produit.getAll");
			System.out.println("Logs : " + sqle.getMessage());
		}
		return listeProduit;
	}

}
