package modele.dao.entities.listeMemoire.produit;

import java.util.ArrayList;
import entities.produit.Produit;
import modele.dao.entities.ProduitDAO;

public class ListeMemoireProduitDAO  implements ProduitDAO{

	private ArrayList<Produit> listeProduit;
	private static int autoIncrementedId = 0;
	
	private static ListeMemoireProduitDAO instance;
	
	private ListeMemoireProduitDAO() { 
		listeProduit = new ArrayList<Produit>();
	}
	
	public static ListeMemoireProduitDAO getInstance() {
		if(instance==null) instance = new ListeMemoireProduitDAO();
		return instance;
	}

	@Override
	public boolean create(Produit objet) {
		++autoIncrementedId;
		objet.setId(autoIncrementedId);
		listeProduit.add(objet);
		return true;
	}

	@Override
	public boolean update(Produit objet) {
		int idx = listeProduit.indexOf(objet);
		if(idx == -1) 
			return false;
		else {
			listeProduit.get(idx).setNom(objet.getNom());
			listeProduit.get(idx).setDescription(objet.getDescription());
			listeProduit.get(idx).setTarif(objet.getTarif());
			listeProduit.get(idx).setVisuel(objet.getVisuel());
			listeProduit.get(idx).setIdCategorie(objet.getIdCategorie());
			return true;
		}
	}

	@Override
	public boolean delete(Produit objet) {
		int idx = listeProduit.indexOf(objet);
		if(idx == -1) 
			return false;
		else {
			listeProduit.remove(idx);
			return true;
		}
	}

	@Override
	public ArrayList<Produit> getAll() {
		return listeProduit;
	}
}
