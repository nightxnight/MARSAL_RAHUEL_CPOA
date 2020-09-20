package modele.dao.entities.listeMemoire.commande;

import java.sql.Date;
import java.util.ArrayList;
import entities.commande.Commande;
import modele.dao.entities.CommandeDAO;

public class ListeMemoireCommandeDAO implements CommandeDAO{
	
	private ArrayList<Commande> listeCommande;
	private static int autoIncrementedId;
	
	private static ListeMemoireCommandeDAO instance;
	
	private ListeMemoireCommandeDAO() { }
	
	public static ListeMemoireCommandeDAO getInstance() {
		if(instance==null) instance = new ListeMemoireCommandeDAO();
		return instance;
	}

	@Override
	public boolean create(Commande objet) {
		return listeCommande.add(new Commande(++autoIncrementedId, Date.valueOf(objet.getDateCommande()), objet.getIdClient()));
	}

	@Override
	public boolean update(Commande objetModifie, Commande objetRemplacant) {
		if(listeCommande.contains(objetModifie)) {
			listeCommande.get(listeCommande.indexOf(objetModifie)).setDateCommande(objetRemplacant.getDateCommande());
			listeCommande.get(listeCommande.indexOf(objetModifie)).setIdClient(objetRemplacant.getIdClient());
			return true;
		} return false;
	}

	@Override
	public boolean delete(Commande objet) {
		if(listeCommande.contains(objet)) {
			return listeCommande.remove(objet);
		} return false;
	}
}
