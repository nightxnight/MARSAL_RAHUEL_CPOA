package modele.dao.entities.listeMemoire.produit;

import java.util.ArrayList;
import entities.produit.Produit;
import modele.dao.entities.ProduitDAO;

public class ListeMemoireProduitDAO  implements ProduitDAO{

	private ArrayList<Produit> listeProduit;
	private static int autoIncrementedId = 0;
	
	private static ListeMemoireProduitDAO instance;
	
	private ListeMemoireProduitDAO() { 
		listeProduit = new ArrayList<Produit>();
		listeProduit.add(new Produit(2, "Sonic te kiffe", 
				"Inspiré par la saga Séga (c'est plus fort que toi !), un pull 100% gamer qui te permettra de faire baver d'envie tes petits camarades de jeu.",
				41.5, "pull1.png", 1));
		listeProduit.add(new Produit(6, "La chaleur des rennes",
				"Classique mais efficace, un bonnet dont l'élégance n'est pas à souligner, il vous grattera comme il faut !",
				15, "bonnet0.png", 2));
		listeProduit.add(new Produit(12, "Dall", "Joyeux Noël avec nos petits lutins dansants !",
				35, "bonnet1.png", 2));
		autoIncrementedId = 12;
	}
	
	public static ListeMemoireProduitDAO getInstance() {
		if(instance==null) instance = new ListeMemoireProduitDAO();
		return instance;
	}

	@Override
	public boolean create(Produit objet) {
		++autoIncrementedId;
		objet.setId(autoIncrementedId);
		
		int idx = listeProduit.indexOf(objet);
		if(idx != -1) return false;
		
		listeProduit.add(objet);
		return true;
	}

	@Override
	public boolean update(Produit objet) {
		int idx = listeProduit.indexOf(objet);
		if(idx == -1) 
			return false;
		else {
			listeProduit.get(idx).setNom(objet.getNom());
			listeProduit.get(idx).setDescription(objet.getDescription());
			listeProduit.get(idx).setTarif(objet.getTarif());
			listeProduit.get(idx).setVisuel(objet.getVisuel());
			listeProduit.get(idx).setIdCategorie(objet.getIdCategorie());
			return true;
		}
	}

	@Override
	public boolean delete(Produit objet) {
		int idx = listeProduit.indexOf(objet);
		if(idx == -1) 
			return false;
		else {
			listeProduit.remove(idx);
			return true;
		}
	}
	
	@Override
	public Produit getById(int id) {
		int idx = listeProduit.indexOf(new Produit(id, "", "", 0, "", 0));
		if(idx == -1) return null;
		else {
			return listeProduit.get(idx);
		}
	}

	@Override
	public ArrayList<Produit> getAll() {
		return listeProduit;
	}
}
