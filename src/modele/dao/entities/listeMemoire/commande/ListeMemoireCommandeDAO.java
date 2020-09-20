package modele.dao.entities.listeMemoire.commande;

import java.util.ArrayList;
import entities.commande.Commande;
import modele.dao.entities.CommandeDAO;

public class ListeMemoireCommandeDAO implements CommandeDAO{
	
private ArrayList<Commande> listeCommande;
	
	private static ListeMemoireCommandeDAO instance;
	
	private ListeMemoireCommandeDAO() { }
	
	public static ListeMemoireCommandeDAO getInstance() {
		if(instance==null) instance = new ListeMemoireCommandeDAO();
		return instance;
	}

	@Override
	public boolean create(Commande objet) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Commande objetModife, Commande objetRemplacant) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Commande objet) {
		// TODO Auto-generated method stub
		return false;
	}
}
