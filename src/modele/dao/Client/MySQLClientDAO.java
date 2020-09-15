package modele.dao.Client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entities.client.Client;
import modele.ModeleSQL;
import modele.dao.DAO; 

public class MySQLClientDAO implements DAO<Client>{
	
private static MySQLClientDAO instance;
	
	private MySQLClientDAO() {}
	
	public static MySQLClientDAO getInstance() {
		if (instance == null) {
			instance = new MySQLClientDAO();
		}
		return instance;
	}

	@Override
	public boolean create(Client objet) {
		try {
			PreparedStatement query = ModeleSQL.getConnexion().prepareStatement("INSERT INTO CLient (nom, prenom, identifiant, mot_de_passe, adr_numero, adr_voie, adr_code_postal, adr_ville, adr_pays) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?))", Statement.RETURN_GENERATED_KEYS);
			query.setString(1, objet.getNom());
			query.setString(2, objet.getPrenom());
			query.setString(3, objet.getIdentifiant());
			query.setString(4, objet.getMotDePasse());
			query.setString(5, objet.getAdrNumero());
			query.setString(6, objet.getAdrVoie());
			query.setString(7, objet.getAdrCodePostal());
			query.setString(8, objet.getAdrVille());
			query.setString(9, objet.getAdrPays());
			
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
				System.out.println("Erreur lors de la requ�te \"ajouterClient\".");
				System.out.println("logs : " + sqle.getMessage());
			}		
		return false;
	}

	@Override
	public boolean update(Client objetModife, Client objetRemplacant) {
		try {
			PreparedStatement query = ModeleSQL.getConnexion().prepareStatement("UPDATE Client SET nom = ?, prenom = ? ,identifiant = ?, mot_de_passe = ?, adr_numero = ?, adr_voie = ?, adr_code_postal = ?, adr_ville = ?, adr_pays = ? , WHERE id_client = ?");
			query.setString(1, objetRemplacant.getNom());
			query.setString(2, objetRemplacant.getPrenom());
			query.setString(3, objetRemplacant.getIdentifiant());
			query.setString(4, objetRemplacant.getMotDePasse());
			query.setString(5, objetRemplacant.getAdrNumero());
			query.setString(6, objetRemplacant.getAdrVoie());
			query.setString(7, objetRemplacant.getAdrCodePostal());
			query.setString(8, objetRemplacant.getAdrVille());
			query.setString(9, objetRemplacant.getAdrPays());
			
			query.setInt(10, objetModife.getIdClient());
		
		int nbLigne = query.executeUpdate();
		System.out.println(nbLigne + " ligne(s) modifi�e(s)");
		
		if(query != null) query.close();
		
		return nbLigne == 1;
		
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requ�te \"modifierClient\".");
			System.out.println("logs : " + sqle.getMessage());
		}				return false;
	}

	@Override
	public boolean delete(Client objet) {
		try {
			PreparedStatement query = ModeleSQL.getConnexion().prepareStatement("DELETE FROM Client WHERE id_client = ?");
			query.setInt(1, objet.getIdClient());
			
			int nbLigne = query.executeUpdate();
			System.out.println(nbLigne + " ligne(s) supprim�e(s)");
			
			if(query != null) query.close();
			
			return nbLigne == 1;
			
			} catch (SQLException sqle) {
				System.out.println("Erreur lors de la requ�te \"supprimerCategorie\".");
				System.out.println("logs : " + sqle.getMessage());
			}			return false;
	}
	

}
