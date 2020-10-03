package dao.entities.listeMemoire.client;

import java.util.ArrayList;

import entities.client.Client;
import dao.entities.ClientDAO;

public class ListeMemoireClientDAO implements ClientDAO{

	private ArrayList<Client> listeClient;
	private static int autoIncrementedId=0;
	
	private static ListeMemoireClientDAO instance;
	
	private ListeMemoireClientDAO() { 
		listeClient = new ArrayList<Client>();
		listeClient.add(new Client(1, "LAROCHE", "Pierre", "pl@ul.fr", "toto", "12", "rue des etudiants", "57990", "Metz", "France"));
		listeClient.add(new Client(2, "CHAMPION", "Nico", "nico@lechampion.fr", "niconico78", "17", "rue de la muscu", "68952", "Epinal", "France"));
		listeClient.add(new Client(4, "LEVIKING", "Victor", "victorleM@gmail.com", "carole", "51", "rue des berserk", "41256", "Vent sur flamme", "Valhalla"));
		autoIncrementedId = 1;
	}
	
	public static ListeMemoireClientDAO getInstance() {
		if(instance==null) instance = new ListeMemoireClientDAO();
		return instance;
	}

	@Override
	public boolean create(Client objet) {		
		++autoIncrementedId;
		while(listeClient.indexOf(objet) != -1) {
			++autoIncrementedId;
		}
		
		objet.setIdClient(autoIncrementedId);
		
		listeClient.add(objet);
		return true;
	}

	@Override
	public boolean update(Client objet) throws IllegalArgumentException {		
		int idx = listeClient.indexOf(objet);
		if(idx == -1) throw new IllegalArgumentException("Le client que vous essayez de modifier est introuvable.");
		else {
			listeClient.get(idx).setNom(objet.getNom());
			listeClient.get(idx).setPrenom(objet.getPrenom());
			listeClient.get(idx).setIdentifiant(objet.getIdentifiant());
			listeClient.get(idx).setMotDePasse(objet.getMotDePasse());
			listeClient.get(idx).setAdrNumero(objet.getAdrNumero());
			listeClient.get(idx).setAdrVoie(objet.getAdrVoie());
			listeClient.get(idx).setAdrCodePostal(objet.getAdrCodePostal());
			listeClient.get(idx).setAdrVille(objet.getAdrVille());
			listeClient.get(idx).setAdrPays(objet.getAdrPays());
			return true;
		}
	}

	@Override
	public boolean delete(Client objet) throws IllegalArgumentException {
		int idx = listeClient.indexOf(objet);
		if(idx == -1) throw new IllegalArgumentException("Le client que vous essayez de supprimer est introuvable.");
		else {
			listeClient.remove(idx);
			return true;
		}
	}
	
	public Client getById(int id) throws IllegalArgumentException {
		int idx = listeClient.indexOf(new Client(id, "", "", "", "", "", "", "", "", ""));
		if(idx == -1) throw new IllegalArgumentException("Le client recherché est introuvable.");
		else {
			return listeClient.get(idx);
		}
	}

	@Override
	public ArrayList<Client> getAll() {
		return listeClient;
	}
}
