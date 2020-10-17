package dao.entities.listeMemoire;

import java.util.ArrayList;

import dao.entities.CategorieDAO;
import entities.Categorie;

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
	public boolean create(Categorie objet)  {	
		++autoIncrementedId;
		while (listeCategorie.indexOf(objet) != -1){
			++autoIncrementedId;
		}
		
		objet.setIdCategorie(autoIncrementedId);
		
		return listeCategorie.add(objet);
	}

	@Override
	public boolean update(Categorie objet)  throws IllegalArgumentException {
		int idx = listeCategorie.indexOf(objet);
		if(idx == -1) throw new IllegalArgumentException("La catégorie que vous voulez modifié est introuvable");
		else {
			listeCategorie.get(idx).setTitre(objet.getTitre());
			listeCategorie.get(idx).setVisuel(objet.getVisuel());
			return true;
		}
	}

	@Override
	public boolean delete(Categorie objet) throws IllegalArgumentException {
		int idx = listeCategorie.indexOf(objet);
		if(idx == -1) throw new IllegalArgumentException("La catégorie que vous voulez supprimé est introuvable");
		else {
			listeCategorie.remove(idx);
			return true;
		}
	}
	
	public Categorie getById(int id) throws IllegalArgumentException {
		int idx = listeCategorie.indexOf(new Categorie(id, "", ""));
		if(idx == -1) throw new IllegalArgumentException("La catégorie cherchée est introuvable.");
		else {
			return listeCategorie.get(idx);
		}
	}

	@Override
	public ArrayList<Categorie> getAll() {
		return listeCategorie;
	}	
}
