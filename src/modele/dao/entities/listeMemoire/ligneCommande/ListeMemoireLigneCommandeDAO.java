package modele.dao.entities.listeMemoire.ligneCommande;

import java.util.ArrayList;

import entities.ligneCommande.LigneCommande;
import modele.dao.entities.LigneCommandeDAO;

public class ListeMemoireLigneCommandeDAO implements LigneCommandeDAO{
	
private ArrayList<LigneCommande> listeLigneCommande;
	
	private static ListeMemoireLigneCommandeDAO instance;
	
	private ListeMemoireLigneCommandeDAO() { }
	
	public static ListeMemoireLigneCommandeDAO getInstance() {
		if(instance==null) instance = new ListeMemoireLigneCommandeDAO();
		return instance;
	}

	@Override
	public boolean create(LigneCommande objet) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(LigneCommande objetModife, LigneCommande objetRemplacant) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(LigneCommande objet) {
		// TODO Auto-generated method stub
		return false;
	}
}
