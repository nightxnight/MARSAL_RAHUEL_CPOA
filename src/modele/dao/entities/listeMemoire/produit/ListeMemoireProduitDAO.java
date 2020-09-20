package modele.dao.entities.listeMemoire.produit;

import java.util.ArrayList;
import entities.produit.Produit;
import modele.dao.entities.ProduitDAO;

public class ListeMemoireProduitDAO  implements ProduitDAO{

	private ArrayList<Produit> listeProduit;
	
	private static ListeMemoireProduitDAO instance;
	
	private ListeMemoireProduitDAO() { }
	
	public static ListeMemoireProduitDAO getInstance() {
		if(instance==null) instance = new ListeMemoireProduitDAO();
		return instance;
	}

	@Override
	public boolean create(Produit objet) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Produit objetModife, Produit objetRemplacant) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Produit objet) {
		// TODO Auto-generated method stub
		return false;
	}
}
