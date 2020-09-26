package modele.dao.entities.mysql.client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import entities.client.Client;
import modele.dao.entities.ClientDAO;
import modele.dao.entities.mysql.MySQLDAOFactory; 

public class MySQLClientDAO implements ClientDAO{
	
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
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("INSERT INTO CLient (nom, prenom, identifiant, mot_de_passe, adr_numero, adr_voie, adr_code_postal, adr_ville, adr_pays) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?))", Statement.RETURN_GENERATED_KEYS);
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
	public boolean update(Client objet) {
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("UPDATE Client SET nom = ?, prenom = ? ,identifiant = ?, mot_de_passe = ?, adr_numero = ?, adr_voie = ?, adr_code_postal = ?, adr_ville = ?, adr_pays = ? , WHERE id_client = ?");
			query.setString(1, objet.getNom());
			query.setString(2, objet.getPrenom());
			query.setString(3, objet.getIdentifiant());
			query.setString(4, objet.getMotDePasse());
			query.setString(5, objet.getAdrNumero());
			query.setString(6, objet.getAdrVoie());
			query.setString(7, objet.getAdrCodePostal());
			query.setString(8, objet.getAdrVille());
			query.setString(9, objet.getAdrPays());
			
			query.setInt(10, objet.getIdClient());
		
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
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("DELETE FROM Client WHERE id_client = ?");
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
	
	@Override
	public ArrayList<Client> getAll() {
		ArrayList<Client> listeClient = null;
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("SELECT * FROM Client");
			ResultSet res = query.executeQuery();
			
			listeClient = new ArrayList<Client>();	
			while(res.next()) {
				listeClient.add(new Client(res.getInt(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6),
						res.getString(7), res.getString(8), res.getString(9), res.getString(10)));
			}
			return listeClient;
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requ�te \"MySQLDAOFactory_Client.getAll");
			System.out.println("logs : " + sqle.getMessage());
		}
		return listeClient;
	}

}
