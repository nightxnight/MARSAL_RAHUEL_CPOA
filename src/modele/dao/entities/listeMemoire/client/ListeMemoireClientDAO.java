package modele.dao.entities.listeMemoire.client;

import java.util.ArrayList;

import entities.client.Client;
import entities.produit.Produit;
import modele.dao.DAO;
import modele.dao.entities.ClientDAO;

public class ListeMemoireClientDAO implements ClientDAO{

	private ArrayList<Client> listeClient;
	private static int autoIncrementedId;
	
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
		return listeClient.add(new Client(++autoIncrementedId, objet.getNom(), objet.getPrenom(), objet.getIdentifiant(), objet.getMotDePasse(),
				objet.getAdrNumero(), objet.getAdrVoie(), objet.getAdrCodePostal(), objet.getAdrVille(), objet.getAdrPays()));
	}

	@Override
	public boolean update(int idObjetModifie, Client objetRemplacant) {
		int idx = positionById(idObjetModifie);
		if(idx == -1) return false;
		else {
			listeClient.get(idx).setNom(objetRemplacant.getNom());
			listeClient.get(idx).setPrenom(objetRemplacant.getPrenom());
			listeClient.get(idx).setIdentifiant(objetRemplacant.getIdentifiant());
			listeClient.get(idx).setMotDePasse(objetRemplacant.getMotDePasse());
			listeClient.get(idx).setAdrNumero(objetRemplacant.getAdrNumero());
			listeClient.get(idx).setAdrVoie(objetRemplacant.getAdrVoie());
			listeClient.get(idx).setAdrCodePostal(objetRemplacant.getAdrCodePostal());
			listeClient.get(idx).setAdrVille(objetRemplacant.getAdrVille());
			listeClient.get(idx).setAdrPays(objetRemplacant.getAdrPays());
			return true;
		}
	}

	@Override
	public boolean delete(Client objet) {
		if(listeClient.contains(objet)) {
			return listeClient.remove(objet);
		} return false;
	}

	@Override
	public ArrayList<Client> getAll() {
		return listeClient;
	}
	
	private int positionById(int idClient) {
		int position = -1;
		for(Client client : listeClient) {
			if(client.getIdClient()==idClient) {
				position = listeClient.indexOf(client);
			}
		}
		return position;
	}
}
