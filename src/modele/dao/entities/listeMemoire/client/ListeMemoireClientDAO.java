package modele.dao.entities.listeMemoire.client;

import java.util.ArrayList;

import entities.client.Client;
import modele.dao.DAO;
import modele.dao.entities.ClientDAO;

public class ListeMemoireClientDAO implements ClientDAO{

private ArrayList<Client> listeClient;
	
	private static ListeMemoireClientDAO instance;
	
	private ListeMemoireClientDAO() { }
	
	public static ListeMemoireClientDAO getInstance() {
		if(instance==null) instance = new ListeMemoireClientDAO();
		return instance;
	}

	@Override
	public boolean create(Client objet) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Client objetModife, Client objetRemplacant) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Client objet) {
		// TODO Auto-generated method stub
		return false;
	}
}
