package modele.dao.entities.listeMemoire.produit;

import java.util.ArrayList;
import entities.produit.Produit;
import modele.dao.entities.ProduitDAO;

public class ListeMemoireProduitDAO  implements ProduitDAO{

	private ArrayList<Produit> listeProduit;
	private static int autoIncrementedId;
	
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
		return listeProduit.add(new Produit(++autoIncrementedId, objet.getNom(), objet.getDescription(), objet.getTarif(), objet.getVisuel(), objet.getIdCategorie()));
	}

	@Override
	public boolean update(int idObjetModifie, Produit objetRemplacant) {
		int idx = positionById(idObjetModifie);
		if(idx == -1) 
			return false;
		else {
			listeProduit.get(idx).setNom(objetRemplacant.getNom());
			listeProduit.get(idx).setDescription(objetRemplacant.getDescription());
			listeProduit.get(idx).setTarif(objetRemplacant.getTarif());
			listeProduit.get(idx).setVisuel(objetRemplacant.getVisuel());
			listeProduit.get(idx).setIdCategorie(objetRemplacant.getIdCategorie());
			return true;
		}
	}

	@Override
	public boolean delete(Produit objet) {
		int idx = positionById(objet.getId());
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
	
	private int positionById(int idProduit) {
		int position = -1;
		for(Produit produit : listeProduit) {
			if(produit.getId()==idProduit) {
				position = listeProduit.indexOf(produit);
			}
		}
		return position;
	}
}
