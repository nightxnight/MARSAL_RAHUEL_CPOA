package modele.dao.entities.listeMemoire.commande;

import java.sql.Date;
import java.util.ArrayList;
import entities.commande.Commande;
import entities.produit.Produit;
import modele.dao.entities.CommandeDAO;

public class ListeMemoireCommandeDAO implements CommandeDAO{
	
	private ArrayList<Commande> listeCommande;
	private static int autoIncrementedId;
	
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
		return listeCommande.add(new Commande(++autoIncrementedId, Date.valueOf(objet.getDateCommande()), objet.getIdClient()));
	}

	@Override
	public boolean update(int idObjetModifie, Commande objetRemplacant) {
		int idx = positionById(idObjetModifie);
		if(idx == -1) {
			return false;
		} else {
			listeCommande.get(idx).setIdClient(objetRemplacant.getIdClient());
			listeCommande.get(idx).setDateCommande(objetRemplacant.getDateCommande());
			return true;
		}
	}

	@Override
	public boolean delete(Commande objet) {
		int idx = positionById(objet.getIdCommande());
		if(idx == -1) {
			return false;
		} else return listeCommande.remove(objet);
	}

	@Override
	public ArrayList<Commande> getAll() {
		return listeCommande;
	}
	
	private int positionById(int idCommande) {
		int position = -1;
		for(Commande commande : listeCommande) {
			if(commande.getIdCommande()==idCommande) {
				position = listeCommande.indexOf(commande);
			}
		}
		return position;
	}
}
