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
	public boolean update(Produit objetModifie, Produit objetRemplacant) {
		if(listeProduit.contains(objetModifie)) {
			listeProduit.get(listeProduit.indexOf(objetModifie)).setNom(objetRemplacant.getNom());
			listeProduit.get(listeProduit.indexOf(objetModifie)).setDescription(objetRemplacant.getDescription());
			listeProduit.get(listeProduit.indexOf(objetModifie)).setTarif(objetRemplacant.getTarif());
			listeProduit.get(listeProduit.indexOf(objetModifie)).setVisuel(objetRemplacant.getVisuel());
			listeProduit.get(listeProduit.indexOf(objetModifie)).setIdCategorie(objetRemplacant.getIdCategorie());
			return true;
		} return false;
	}

	@Override
	public boolean delete(Produit objet) {
		if(listeProduit.contains(objet)) {
			return listeProduit.remove(objet);
		} else return false;
	}

	@Override
	public ArrayList<Produit> getAll() {
		return listeProduit;
	}
}
