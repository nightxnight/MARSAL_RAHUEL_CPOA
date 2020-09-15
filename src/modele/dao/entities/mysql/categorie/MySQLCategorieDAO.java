package modele.dao.entities.mysql.categorie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entities.categorie.Categorie;
import modele.ModeleSQL;
import modele.dao.DAO;

public class MySQLCategorieDAO implements DAO<Categorie> {
	
	private static MySQLCategorieDAO instance;
	
	private MySQLCategorieDAO() { }
	
	public static MySQLCategorieDAO getInstance() {
		if(instance==null) instance = new MySQLCategorieDAO();
		return instance;
	}
	
	@Override
	public boolean create(Categorie objet) {
		try {
			PreparedStatement query = ModeleSQL.getConnexion().prepareStatement("INSERT INTO Categorie (titre, visuel) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
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
	public boolean update(Categorie objetModife, Categorie objetRemplacant) {
		try {
			PreparedStatement query = ModeleSQL.getConnexion().prepareStatement("UPDATE Categorie SET titre = ?, visuel = ? WHERE id_categorie = ?");
			query.setString(1, objetRemplacant.getTitre());
			query.setString(2, objetRemplacant.getVisuel());
			query.setInt(3, objetModife.getIdCategorie());
		
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
			PreparedStatement query = ModeleSQL.getConnexion().prepareStatement("DELETE FROM Categorie WHERE id_categorie = ?");
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

}
