package dao.entities.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dao.entities.ProduitDAO;
import entities.Produit;

public class MySQLProduitDAO implements ProduitDAO{
	
	private static MySQLProduitDAO instance;
	
	private MySQLProduitDAO() { }
	
	public static MySQLProduitDAO getInstance() {
		if(instance==null) instance = new MySQLProduitDAO();
		return instance;
	}

	@Override
	public boolean create(Produit objet) throws IllegalArgumentException {
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("INSERT INTO Produit (nom, description, tarif, visuel, id_categorie) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			query.setString(1, objet.getNom());
			query.setString(2, objet.getDescription());
			query.setDouble(3, objet.getTarif());
			query.setString(4, objet.getVisuel());
			query.setInt(5, objet.getIdCategorie());
			
			int nbLigne = query.executeUpdate();
			System.out.println(nbLigne + " ligne(s) ajoutee(s)");
			
			ResultSet res = query.getGeneratedKeys();
			while(res.next()) {
				System.out.println("Cle generee(s) : " + res.getInt(1));
			}
			
			if(res != null) res.close();
			if(query != null) query.close();
			
			return nbLigne==1;
			
			
		} catch (SQLException sqle) {
			if(sqle.getSQLState().equals("45000")) throw new IllegalArgumentException(sqle.getMessage());
			System.out.println("Erreur lors de la requete \"ajouterProduit\".");
			System.out.println("logs : " + sqle.getMessage());
		}		
		return false;
	}

	@Override
	public boolean update(Produit objet) throws IllegalArgumentException {
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("UPDATE Produit SET nom = ?, description = ?, tarif = ?, visuel = ?, id_categorie = ? WHERE id_produit = ?");
			query.setString(1, objet.getNom());
			query.setString(2, objet.getDescription());
			query.setDouble(3, objet.getTarif());
			query.setString(4, objet.getVisuel());
			query.setInt(5, objet.getIdCategorie());
			
			query.setInt(6, objet.getId());
			
			int nbLigne = query.executeUpdate();
			System.out.println(nbLigne + " ligne(s) modifiee(s)");

			if(query != null) query.close();
			
			if(nbLigne == 1) return true;
			else throw new IllegalArgumentException("Le produit que vous souhaitez modifier est introuvable.");
			
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requete \"modifierProduit\".");
			System.out.println("logs : " + sqle.getMessage());
		}		
		return false;
	}

	@Override
	public boolean delete(Produit objet) throws IllegalArgumentException {
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("DELETE FROM Produit WHERE id_produit = ?");
			query.setInt(1, objet.getId());
			
			int nbLigne = query.executeUpdate();
			System.out.println(nbLigne + " ligne(s) supprimee(s)");
			
			if(query != null) query.close();
			
			if(nbLigne == 1) return true;
			else throw new IllegalArgumentException("Le produit que vous souhaitez supprimer est introuvable.");
			
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requete \"supprimerProduit\".");
			System.out.println("logs : " + sqle.getMessage());		
		}		
		return false;
	}
	
	@Override
	public Produit getById(int id) throws IllegalArgumentException {
		Produit produit = null;
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("SELECT * FROM Produit WHERE id_produit = ?");
			query.setInt(1, id);
			
			ResultSet res = query.executeQuery();
			
			if (res.next()) produit = new Produit(res.getInt(1), res.getString(2), res.getString(3), res.getDouble(4), res.getString(5), res.getInt(1));
			else throw new IllegalArgumentException("Le produit recherche est introuvable.");
			
			return produit;
			
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requete \"MYSQLDAOFactory_produit.getById\".");
		}
		return produit;
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
			System.out.println("Erreur lors de la requete \"MySQLDAOFactory_Produit.getAll\".");
			System.out.println("Logs : " + sqle.getMessage());
		}
		return listeProduit;
	}

	@Override
	public ArrayList<Produit> research(Produit produitRecherche) {
		ArrayList<Produit> listeProduit = null;
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("SELECT * FROM Produit WHERE nom LIKE ? AND tarif <= ? AND id_categorie LIKE ?");
			String idCategorie = (produitRecherche.getIdCategorie() == -1) ? "" : String.valueOf(produitRecherche.getIdCategorie());
			
			query.setString(1, "%" + produitRecherche.getNom() + "%");
			query.setDouble(2, produitRecherche.getTarif());
			query.setString(3, "%" + idCategorie + "%");
			
			ResultSet res = query.executeQuery();
			
			listeProduit = new ArrayList<Produit>();	
			while(res.next()) {
				listeProduit.add(new Produit(res.getInt(1), res.getString(2), res.getString(3), res.getDouble(4), res.getString(5), res.getInt(6)));
			}
			return listeProduit;
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requete \"MySQLDAOFactory_Produit.research\".");
			System.out.println("Logs : " + sqle.getMessage());
		}
		return listeProduit;
	}

}
