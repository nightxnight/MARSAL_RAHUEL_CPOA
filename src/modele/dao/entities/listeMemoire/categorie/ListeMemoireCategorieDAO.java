package modele.dao.entities.listeMemoire.categorie;

import java.util.ArrayList;

import entities.categorie.Categorie;
import modele.dao.DAO;
import modele.dao.entities.CategorieDAO;

public class ListeMemoireCategorieDAO implements CategorieDAO{
	
	private ArrayList<Categorie> listeCategorie;
	
	private static ListeMemoireCategorieDAO instance;
	
	private ListeMemoireCategorieDAO() { }
	
	public static ListeMemoireCategorieDAO getInstance() {
		if(instance==null) instance = new ListeMemoireCategorieDAO();
		return instance;
	}

	@Override
	public boolean create(Categorie objet) {
		return false;
	}

	@Override
	public boolean update(Categorie objetModife, Categorie objetRemplacant) {
		return false;
	}

	@Override
	public boolean delete(Categorie objet) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
