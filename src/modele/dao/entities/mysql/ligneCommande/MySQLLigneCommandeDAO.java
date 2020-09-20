package modele.dao.entities.mysql.ligneCommande;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entities.ligneCommande.LigneCommande;
import modele.ModeleSQL;
import modele.dao.entities.LigneCommandeDAO;

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
			PreparedStatement query = ModeleSQL.getConnexion().prepareStatement("INSERT INTO Ligne_commande (id_commande, id_produit, quantite, tarif_unitaire) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
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
	public boolean update(LigneCommande objetModife, LigneCommande objetRemplacant) {
		try {
			PreparedStatement query = ModeleSQL.getConnexion().prepareStatement("UPDATE Ligne_commande SET id_produit = ?, quantite = ?, tarif_unitaire = ? WHERE id_commande = ? AND id_produit = ?");
			query.setInt(1, objetRemplacant.getIdProduit());
			query.setInt(2, objetRemplacant.getQuantite());
			query.setDouble(3, objetRemplacant.getTarifUnitaire());
			query.setInt(5, objetModife.getIdCommande());
			query.setInt(5, objetModife.getIdProduit());
		
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
			PreparedStatement query = ModeleSQL.getConnexion().prepareStatement("DELETE FROM Ligne_commande WHERE id_commande = ? AND id_produit = ?");
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

}
