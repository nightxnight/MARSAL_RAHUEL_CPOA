package dao.entities.mysql.ligneCommande;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entities.ligneCommande.LigneCommande;
import dao.entities.LigneCommandeDAO;
import dao.entities.mysql.MySQLDAOFactory;

public class MySQLLigneCommandeDAO implements LigneCommandeDAO{
	
	private static MySQLLigneCommandeDAO instance;
	
	private MySQLLigneCommandeDAO() { }
	
	public static MySQLLigneCommandeDAO getInstance() {
		if(instance==null) instance = new MySQLLigneCommandeDAO();
		return instance;
	}
	
	@Override
	public boolean create(LigneCommande objet) {
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("INSERT INTO Ligne_commande (id_commande, id_produit, quantite, tarif_unitaire) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			query.setInt(1, objet.getIdCommande());
			query.setInt(2, objet.getIdProduit());
			query.setInt(3, objet.getQuantite());
			query.setDouble(4, objet.getTarifUnitaire());
			
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
				System.out.println("Erreur lors de la requ�te \"ajouterLigneCommande\".");
				System.out.println("logs : " + sqle.getMessage());
			}		
		return false;
	}

	@Override
	public boolean update(LigneCommande objet) {
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("UPDATE Ligne_commande SET quantite = ?, tarif_unitaire = ? WHERE id_commande = ? AND id_produit = ?");
			query.setInt(1, objet.getQuantite());
			query.setDouble(2, objet.getTarifUnitaire());
			
			query.setInt(3, objet.getIdCommande());
			query.setInt(4, objet.getIdProduit());
		
		int nbLigne = query.executeUpdate();
		System.out.println(nbLigne + " ligne(s) modifi�e(s)");
		
		if(query != null) query.close();
		
		return nbLigne == 1;
		
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requ�te \"modifierLigneCommande\".");
			System.out.println("logs : " + sqle.getMessage());
		}	
		return false;
	}

	@Override
	public boolean delete(LigneCommande objet) {
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("DELETE FROM Ligne_commande WHERE id_commande = ? AND id_produit = ?");
			query.setInt(1, objet.getIdCommande());
			query.setInt(2, objet.getIdProduit());
			
			int nbLigne = query.executeUpdate();
			System.out.println(nbLigne + " ligne(s) supprim�e(s)");
			
			if(query != null) query.close();
			
			return nbLigne ==1;
			
			} catch (SQLException sqle) {
				System.out.println("Erreur lors de la requ�te \"supprimerLigneCommande\".");
				System.out.println("logs : " + sqle.getMessage());
			}	
		return false;
	}
	
	@Override
	public ArrayList<LigneCommande> getById(int id) {
		ArrayList<LigneCommande> listeLigneCommande = null;
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("SELECT * FROM Ligne_commande WHERE id_commande = ?");
			query.setInt(1, id);
			
			ResultSet res = query.executeQuery();
			
			listeLigneCommande = new ArrayList<LigneCommande>();
			while(res.next()) {
				listeLigneCommande.add(new LigneCommande(res.getInt(1), res.getInt(2), res.getInt(3), res.getDouble(4)));
			}
			return listeLigneCommande;
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requ�te \"MYSQLDAOFactory_ligneCommande.getById\".");
		}
		return listeLigneCommande;
	}
	
	@Override
	public ArrayList<LigneCommande> getAll() {
		ArrayList<LigneCommande> listeLigneCommande = null;
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("SELECT * FROM Ligne_commande");
			ResultSet res = query.executeQuery();
			
			listeLigneCommande = new ArrayList<LigneCommande>();	
			while(res.next()) {
				listeLigneCommande.add(new LigneCommande(res.getInt(1), res.getInt(2), res.getInt(3), res.getDouble(4)));
			}
			return listeLigneCommande;
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requ�te \"MySQLDAOFactory_LigneCommande.getAll");
		}
		return listeLigneCommande;
	}

}
