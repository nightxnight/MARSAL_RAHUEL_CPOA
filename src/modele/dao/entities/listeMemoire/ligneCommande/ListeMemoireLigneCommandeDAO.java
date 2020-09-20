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
		return listeLigneCommande.add(new LigneCommande(objet.getIdCommande(), objet.getIdProduit(), objet.getQuantite(), objet.getTarifUnitaire()));
	}

	@Override
	public boolean update(LigneCommande objetModifie, LigneCommande objetRemplacant) {
		if(listeLigneCommande.contains(objetModifie)) {
			listeLigneCommande.get(listeLigneCommande.indexOf(objetModifie)).setIdProduit(objetRemplacant.getIdProduit());
			listeLigneCommande.get(listeLigneCommande.indexOf(objetModifie)).setQuantite(objetRemplacant.getQuantite());
			listeLigneCommande.get(listeLigneCommande.indexOf(objetModifie)).setTarifUnitaire(objetRemplacant.getTarifUnitaire());
			return true;
		} else return false;
	}

	@Override
	public boolean delete(LigneCommande objet) {
		if(listeLigneCommande.contains(objet)) {
			return listeLigneCommande.remove(objet);
		} else return false;
	}
}
