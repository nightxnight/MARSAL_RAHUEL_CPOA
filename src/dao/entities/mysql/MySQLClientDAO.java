package dao.entities.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dao.entities.ClientDAO;
import entities.Client; 

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
	public boolean create(Client objet) throws IllegalArgumentException{		
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("INSERT INTO Client (nom, prenom, identifiant, mot_de_passe, adr_numero, adr_voie, adr_code_postal, adr_ville, adr_pays) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
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
			System.out.println(nbLigne + " ligne(s) ajoutee(s)");
			
			ResultSet res = query.getGeneratedKeys();
			while(res.next()) {
				System.out.println("Cle generee(s) : " + res.getInt(1));
			}
			
			if(res != null) res.close();
			if(query != null) query.close();
			
			return nbLigne ==1;
			
			} catch (SQLException sqle) {
				System.out.println("Erreur lors de la requete \"ajouterClient\".");
				System.out.println("logs : " + sqle.getMessage());
			}		
		return false;
	}

	@Override
	public boolean update(Client objet) throws IllegalArgumentException {
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("UPDATE Client SET nom = ?, prenom = ? ,identifiant = ?, mot_de_passe = ?, adr_numero = ?, adr_voie = ?, adr_code_postal = ?, adr_ville = ?, adr_pays = ? WHERE id_client = ?");
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
			System.out.println(nbLigne + " ligne(s) modifiee(s)");
			
			if(query != null) query.close();
			
			if(nbLigne == 1) return true;
			else throw new IllegalArgumentException("Le client que vous essayez de modifier est introuvable");
		
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requete \"modifierClient\".");
			System.out.println("logs : " + sqle.getMessage());
		}				
		return false;
	}

	@Override
	public boolean delete(Client objet) {
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("DELETE FROM Client WHERE id_client = ?");
			query.setInt(1, objet.getIdClient());
			
			int nbLigne = query.executeUpdate();
			System.out.println(nbLigne + " ligne(s) supprimee(s)");
			
			if(query != null) query.close();
			
			if(nbLigne == 1) return true;
			else throw new IllegalArgumentException("Le client que vous essayez de modifier est introuvable.");
			
			} catch (SQLException sqle) {
				System.out.println("Erreur lors de la requete \"supprimerCategorie\".");
				System.out.println("logs : " + sqle.getMessage());
			}		
		return false;
	}
	
	@Override
	public Client getById(int id) throws IllegalArgumentException {
		Client client = null;
		try {
			PreparedStatement query = MySQLDAOFactory.getConnexion().prepareStatement("SELECT * FROM Client WHERE id_client = ?");
			query.setInt(1, id);
			
			ResultSet res = query.executeQuery();
			
			if(res.next()) client = new Client(res.getInt(1), res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6), res.getString(7), res.getString(8), res.getString(9), res.getString(10));
			else throw new IllegalArgumentException("Le client que vous cherchez est introuvable.");
			
			return client;
		} catch (SQLException sqle) {
			System.out.println("Erreur lors de la requete \"MYSQLDAOFactory_client.getById\".");
		}
		return client;
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

	@Override
	public ArrayList<Client> research(Client objet) {
		// TODO Auto-generated method stub
		return null;
	}

}
