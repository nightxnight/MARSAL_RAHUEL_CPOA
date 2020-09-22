package modele.dao.entities.listeMemoire.categorie;

import java.util.ArrayList;

import entities.categorie.Categorie;
import entities.produit.Produit;
import modele.dao.DAO;
import modele.dao.entities.CategorieDAO;

public class ListeMemoireCategorieDAO implements CategorieDAO{
	
	private ArrayList<Categorie> listeCategorie;
	private static int autoIncrementedId = 0;
	
	private static ListeMemoireCategorieDAO instance;
	
	private ListeMemoireCategorieDAO() { 
		listeCategorie = new ArrayList<Categorie>();
	}
	
	public static ListeMemoireCategorieDAO getInstance() {
		if(instance==null) instance = new ListeMemoireCategorieDAO();
		return instance;
	}

	@Override
	public boolean create(Categorie objet) {
		return listeCategorie.add(new Categorie(++autoIncrementedId, objet.getTitre(), objet.getVisuel()));
	}

	//A am�liorer puisque peut devenir tr�s lent si la liste contient un �norme nombre d'�l�ment
	@Override
	public boolean update(int idObjetModifie, Categorie objetRemplacant) {
		int idx = positionById(idObjetModifie);
		if(idx == -1) return false;
		else {
			listeCategorie.get(idx).setTitre(objetRemplacant.getTitre());
			listeCategorie.get(idx).setVisuel(objetRemplacant.getVisuel());
			return true;
		}
	}

	@Override
	public boolean delete(Categorie objet) {
		if(listeCategorie.contains(objet)) {
			return listeCategorie.remove(objet);
		} else return false;
	}

	@Override
	public ArrayList<Categorie> getAll() {
		return listeCategorie;
	}
	
	private int positionById(int idCategorie) {
		int position = -1;
		for(Categorie categorie : listeCategorie) {
			if(categorie.getIdCategorie()==idCategorie) {
				position = listeCategorie.indexOf(categorie);
			}
		}
		return position;
	}
	
}
