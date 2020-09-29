package dao.entities.mysql.categorie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entities.categorie.Categorie;
import entities.produit.Produit;
import dao.entities.CategorieDAO;
import dao.entities.mysql.MySQLDAOFactory;

public class MySQLCategorieDAO implements CategorieDAO {
	
	private static MySQLCategorieDAO instance;
	
	private MySQLCategorieDAO() {}
	
	public static MySQLCategorieDAO getInstance() {
		if(instance==null) instance = new MySQLCategorieDAO();
		return instance;
	}
	
	@Override
	public boolean create(Categorie objet) {
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("INSERT INTO Categorie (titre, visuel) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
			query.setString(1, objet.getTitre());
			query.setString(2, objet.getVisuel());
			
			int nbLigne = query.executeUpdate();
			System.out.println(nbLigne + " ligne(s) ajout�e(s)");
			
			ResultSet res = query.getGeneratedKeys();
			while(res.next()) {
				System.out.println("Cl� g�n�r�e : " + res.getInt(1));
			}
			
			if(res != null) res.close();
			if(query != null) query.close();
			
			return nbLigne == 1;
			
			} catch (SQLException sqle) {
				System.out.println("Erreur lors de la requ�te \"ajouterCategorie\".");
				System.out.println("logs : " + sqle.getMessage());
			}		
		return false;
	}

	@Override
	public boolean update(Categorie objet) {
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("UPDATE Categorie SET titre = ?, visuel = ? WHERE id_categorie = ?");
			query.setString(1, objet.getTitre());
			query.setString(2, objet.getVisuel());
			
			query.setInt(3, objet.getIdCategorie());
		
			int nbLigne = query.executeUpdate();
			System.out.println(nbLigne + " ligne(s) modifi�e(s)");
			
			if(query != null) query.close();
			
			return nbLigne == 1;
		
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requ�te \"modifierCategorie\".");
			System.out.println("logs : " + sqle.getMessage());
		}	
		return false;
	}

	@Override
	public boolean delete(Categorie objet) {
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("DELETE FROM Categorie WHERE id_categorie = ?");
			query.setInt(1, objet.getIdCategorie());
			
			int nbLigne = query.executeUpdate();
			System.out.println(nbLigne + " ligne(s) supprim�e(s)");
			
			if(query != null) query.close();
			
			return nbLigne ==1;
			
			} catch (SQLException sqle) {
				System.out.println("Erreur lors de la requ�te \"supprimerCategorie\".");
				System.out.println("logs : " + sqle.getMessage());
			}	
		return false;
	}

	@Override
	public Categorie getById(int id) {
		Categorie categorie = null;
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("SELECT * FROM Categorie WHERE id_categorie = ?");
			query.setInt(1, id);
			
			ResultSet res = query.executeQuery();
			
			if(res.next()) {
				categorie = new Categorie(res.getInt(1), res.getString(2), res.getString(3));
			}
			
			return categorie;
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requ�te \"MYSQLDAOFactory_categorie.getById\".");
		}
		return categorie;
	}
	
	@Override
	public ArrayList<Categorie> getAll() {
		ArrayList<Categorie> listeCategorie = null;
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("SELECT * FROM Categorie");
			ResultSet res = query.executeQuery();
			
			listeCategorie = new ArrayList<Categorie>();	
			while(res.next()) {
				listeCategorie.add(new Categorie(res.getInt(1), res.getString(2), res.getString(3)));
			}
			return listeCategorie;
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requ�te \"MySQLDAOFactory_Categorie.getAll");
			System.out.println("Logs : " + sqle.getMessage());
		}
		return listeCategorie;
	}

}
