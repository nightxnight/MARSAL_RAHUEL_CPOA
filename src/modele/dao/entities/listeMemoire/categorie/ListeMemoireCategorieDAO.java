package modele.dao.entities.listeMemoire.categorie;

import java.util.ArrayList;

import entities.categorie.Categorie;
import modele.dao.DAO;
import modele.dao.entities.CategorieDAO;

public class ListeMemoireCategorieDAO implements CategorieDAO{
	
	private ArrayList<Categorie> listeCategorie;
	private static int autoIncrementedId = 0;
	
	private static ListeMemoireCategorieDAO instance;
	
	private ListeMemoireCategorieDAO() { }
	
	public static ListeMemoireCategorieDAO getInstance() {
		if(instance==null) instance = new ListeMemoireCategorieDAO();
		return instance;
	}

	@Override
	public boolean create(Categorie objet) {
		return listeCategorie.add(new Categorie(++autoIncrementedId, objet.getTitre(), objet.getVisuel()));
	}

	//A améliorer puisque peut devenir très lent si la liste contient un énorme nombre d'élément
	@Override
	public boolean update(Categorie objetModifie, Categorie objetRemplacant) {
		if(listeCategorie.contains(objetModifie)) {
			listeCategorie.get(listeCategorie.indexOf(objetModifie)).setTitre(objetRemplacant.getTitre());
			listeCategorie.get(listeCategorie.indexOf(objetModifie)).setVisuel(objetRemplacant.getVisuel());
			return true;
		} else return false;
	}

	@Override
	public boolean delete(Categorie objet) {
		if(listeCategorie.contains(objet)) {
			return listeCategorie.remove(objet);
		} else return false;
	}
	
}
