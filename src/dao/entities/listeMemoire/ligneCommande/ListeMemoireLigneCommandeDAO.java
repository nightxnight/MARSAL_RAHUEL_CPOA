package dao.entities.listeMemoire.ligneCommande;

import java.util.ArrayList;
import java.util.HashMap;

import dao.entities.LigneCommandeDAO;
import entities.ligneCommande.LigneCommande;

public class ListeMemoireLigneCommandeDAO implements LigneCommandeDAO{
	
	private HashMap<Integer, ArrayList<LigneCommande>> mapLigneCommande;
	
	private static ListeMemoireLigneCommandeDAO instance;
	
	private ListeMemoireLigneCommandeDAO() { 
		mapLigneCommande = new HashMap<Integer,ArrayList<LigneCommande>>();
		mapLigneCommande.put(1, new ArrayList<LigneCommande>());
		mapLigneCommande.get(1).add(new LigneCommande(1, 2, 2, 41.5));
		mapLigneCommande.get(1).add(new LigneCommande(1, 6, 1, 15));
		
		mapLigneCommande.put(2, new ArrayList<LigneCommande>());
		mapLigneCommande.get(2).add(new LigneCommande(2, 12, 4, 35));
		
		mapLigneCommande.put(3,	new ArrayList<LigneCommande>());
		mapLigneCommande.get(3).add(new LigneCommande(3, 2, 1, 41.5));
		mapLigneCommande.get(3).add(new LigneCommande(3, 12, 8, 35));
	}
	
	public static ListeMemoireLigneCommandeDAO getInstance() {
		if(instance==null) instance = new ListeMemoireLigneCommandeDAO();
		return instance;
	}
	
	@Override
	public boolean create(LigneCommande objet) {
		int key = objet.getIdCommande();
		if(!mapLigneCommande.containsKey(key)) {
			mapLigneCommande.put(key, new ArrayList<LigneCommande>());
		}
		
		int idx = mapLigneCommande.get(key).indexOf(objet);
		if(idx != -1) return false;
		
		mapLigneCommande.get(key).add(objet);
		return true;
	}

	@Override
	public boolean update(LigneCommande objet) {
		int key = objet.getIdCommande();
		if(mapLigneCommande.containsKey(key)) {
			int idx = mapLigneCommande.get(key).indexOf(objet);
			if(idx == -1) return false;
			else {
				mapLigneCommande.get(key).get(idx).setQuantite(objet.getQuantite());
				mapLigneCommande.get(key).get(idx).setTarifUnitaire(objet.getTarifUnitaire());
				return true;
			}
		} else return false;
	}

	@Override
	public boolean delete(LigneCommande objet) {
		int key = objet.getIdCommande();
		if(mapLigneCommande.containsKey(key)) {
			int idx = mapLigneCommande.get(key).indexOf(objet);
			if(idx == -1) return false;
			else {
				mapLigneCommande.get(key).get(idx);
				return true;
			}
		} else return false;
	}
	
	@Override
	public ArrayList<LigneCommande> getById(int id) {
		int key = id;
		if(mapLigneCommande.containsKey(key)) return mapLigneCommande.get(key);
		else return null;
	}

	@Override
	public ArrayList<LigneCommande> getAll() {
		ArrayList<LigneCommande> listeToutesLignesCommande = new ArrayList<LigneCommande>();
		for(int key : mapLigneCommande.keySet()) {
			listeToutesLignesCommande.addAll(mapLigneCommande.get(key));
		}
		return listeToutesLignesCommande;
	}
}
