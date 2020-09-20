package modele.dao.entities.listeMemoire.client;

import java.util.ArrayList;

import entities.client.Client;
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
	public boolean update(Client objetModifie, Client objetRemplacant) {
		if(listeClient.contains(objetModifie)) {
			listeClient.get(listeClient.indexOf(objetModifie)).setNom(objetRemplacant.getNom());
			listeClient.get(listeClient.indexOf(objetModifie)).setPrenom(objetRemplacant.getPrenom());
			listeClient.get(listeClient.indexOf(objetModifie)).setIdentifiant(objetRemplacant.getIdentifiant());
			listeClient.get(listeClient.indexOf(objetModifie)).setMotDePasse(objetRemplacant.getMotDePasse());
			listeClient.get(listeClient.indexOf(objetModifie)).setAdrNumero(objetRemplacant.getAdrNumero());
			listeClient.get(listeClient.indexOf(objetModifie)).setAdrVoie(objetRemplacant.getAdrVoie());
			listeClient.get(listeClient.indexOf(objetModifie)).setAdrCodePostal(objetRemplacant.getAdrCodePostal());
			listeClient.get(listeClient.indexOf(objetModifie)).setAdrVille(objetRemplacant.getAdrVille());
			listeClient.get(listeClient.indexOf(objetModifie)).setAdrPays(objetRemplacant.getAdrPays());
			return true;
		} else return false;
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
}
