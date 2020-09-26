package modele.dao.entities.listeMemoire.commande;

import java.util.ArrayList;
import entities.commande.Commande;
import modele.dao.entities.CommandeDAO;

public class ListeMemoireCommandeDAO implements CommandeDAO{
	
	private ArrayList<Commande> listeCommande;
	private static int autoIncrementedId = 0;
	
	private static ListeMemoireCommandeDAO instance;
	
	private ListeMemoireCommandeDAO() {
		listeCommande = new ArrayList<Commande>();
	}
	
	public static ListeMemoireCommandeDAO getInstance() {
		if(instance==null) instance = new ListeMemoireCommandeDAO();
		return instance;
	}

	@Override
	public boolean create(Commande objet) {
		++autoIncrementedId;
		objet.setIdCommande(autoIncrementedId);
		listeCommande.add(objet);
		return true;
	}

	@Override
	public boolean update(Commande objet) {
		int idx = listeCommande.indexOf(objet);
		if(idx == -1) {
			return false;
		} else {
			listeCommande.get(idx).setIdClient(objet.getIdClient());
			listeCommande.get(idx).setDateCommande(objet.getDateCommande());
			return true;
		}
	}

	@Override
	public boolean delete(Commande objet) {
		int idx = listeCommande.indexOf(objet);
		if(idx == -1) {
			return false;
		} else return listeCommande.remove(objet);
	}

	@Override
	public ArrayList<Commande> getAll() {
		return listeCommande;
	}
}
