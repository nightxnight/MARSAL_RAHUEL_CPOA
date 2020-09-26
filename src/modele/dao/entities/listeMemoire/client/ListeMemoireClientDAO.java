package modele.dao.entities.listeMemoire.client;

import java.util.ArrayList;

import entities.client.Client;
import modele.dao.entities.ClientDAO;

public class ListeMemoireClientDAO implements ClientDAO{

	private ArrayList<Client> listeClient;
	private static int autoIncrementedId=0;
	
	private static ListeMemoireClientDAO instance;
	
	private ListeMemoireClientDAO() { 
		listeClient = new ArrayList<Client>();
	}
	
	public static ListeMemoireClientDAO getInstance() {
		if(instance==null) instance = new ListeMemoireClientDAO();
		return instance;
	}

	@Override
	public boolean create(Client objet) {
		++autoIncrementedId;
		objet.setIdClient(autoIncrementedId);
		listeClient.add(autoIncrementedId, objet);
		return true;
	}

	@Override
	public boolean update(Client objet) {
		int idx = listeClient.indexOf(objet);
		if(idx == -1) return false;
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
	public boolean delete(Client objet) {
		int idx = listeClient.indexOf(objet);
		if(idx == -1) return false;
		else {
			listeClient.remove(idx);
			return true;
		}
	}

	@Override
	public ArrayList<Client> getAll() {
		return listeClient;
	}
}
