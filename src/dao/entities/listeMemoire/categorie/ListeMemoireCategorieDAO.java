package dao.entities.listeMemoire.categorie;

import java.util.ArrayList;

import entities.categorie.Categorie;
import dao.entities.CategorieDAO;

public class ListeMemoireCategorieDAO implements CategorieDAO{
	
	private ArrayList<Categorie> listeCategorie;
	private static int autoIncrementedId = 0;
	
	private static ListeMemoireCategorieDAO instance;
	
	private ListeMemoireCategorieDAO() { 
		listeCategorie = new ArrayList<Categorie>();
		listeCategorie.add(new Categorie(1, "Pulls", "lespulls.png"));
		listeCategorie.add(new Categorie(2, "Bonnets", "lesbonnets.png"));
		listeCategorie.add(new Categorie(3, "Chaussettes", "leschaussettes.png"));
		
		autoIncrementedId = 3;
	}
	
	public static ListeMemoireCategorieDAO getInstance() {
		if(instance==null) instance = new ListeMemoireCategorieDAO();
		return instance;
	}

	@Override
	public boolean create(Categorie objet) {
		++autoIncrementedId;
		
		while (listeCategorie.indexOf(objet) != -1){
			++autoIncrementedId;
		}
		
		objet.setIdCategorie(autoIncrementedId);
		
		listeCategorie.add(objet);
		return true;
	}

	@Override
	public boolean update(Categorie objet) {
		int idx = listeCategorie.indexOf(objet);
		if(idx == -1) return false;
		else {
			listeCategorie.get(idx).setTitre(objet.getTitre());
			listeCategorie.get(idx).setVisuel(objet.getVisuel());
			return true;
		}
	}

	@Override
	public boolean delete(Categorie objet) {
		int idx = listeCategorie.indexOf(objet);
		if(idx == -1) return false;
		else {
			listeCategorie.remove(idx);
			return true;
		}
	}
	
	public Categorie getById(int id) {
		int idx = listeCategorie.indexOf(new Categorie(id, "", ""));
		if(idx == -1) return null;
		else {
			return listeCategorie.get(idx);
		}
	}

	@Override
	public ArrayList<Categorie> getAll() {
		return listeCategorie;
	}	
}
