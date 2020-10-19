package dao.entities.listeMemoire;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import dao.entities.ProduitDAO;
import entities.Produit;

public class ListeMemoireProduitDAO  implements ProduitDAO{

	private ArrayList<Produit> listeProduit;
	private static int autoIncrementedId = 0;
	
	private static ListeMemoireProduitDAO instance;
	
	private ListeMemoireProduitDAO() { 
		listeProduit = new ArrayList<Produit>();
		listeProduit.add(new Produit(2, "Sonic te kiffe", 
				"Inspire par la saga Sega (c'est plus fort que toi !), un pull 100% gamer qui te permettra de faire baver d'envie tes petits camarades de jeu.",
				41.5, "pull1.png", 1));
		listeProduit.add(new Produit(6, "La chaleur des rennes",
				"Classique mais efficace, un bonnet dont l'elegance n'est pas a souligner, il vous grattera comme il faut !",
				15, "bonnet0.png", 2));
		listeProduit.add(new Produit(11, "Des chaussettes", "Pour avoir les pieds bien chaud tout l'hiver!",
				22, "chaussettes1.png", 3));
		listeProduit.add(new Produit(12, "Dall", "Joyeux Noel avec nos petits lutins dansants !",
				35, "bonnet1.png", 2));
		autoIncrementedId = 12;
		
		for(int i = 0; i < 20; i++) {
			create(new Produit("test", "desText", 10, "visuel.png", 1));
		}
		create(new Produit("testSpeciale", "desText", 10, "visuel.png", 1));
	}
	
	public static ListeMemoireProduitDAO getInstance() {
		if(instance==null) instance = new ListeMemoireProduitDAO();
		return instance;
	}

	@Override
	public boolean create(Produit objet) {		
		++autoIncrementedId;
		while(listeProduit.indexOf(objet) != -1) {
			++autoIncrementedId;
		}
		
		objet.setId(autoIncrementedId);
		return listeProduit.add(objet);
	}

	@Override
	public boolean update(Produit objet) throws IllegalArgumentException {		
		int idx = listeProduit.indexOf(objet);
		if(idx == -1) throw new IllegalArgumentException("Le produit que vous essayez de modifier est introuvable.");
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
	public boolean delete(Produit objet) throws IllegalArgumentException {
		int idx = listeProduit.indexOf(objet);
		if(idx == -1) throw new IllegalArgumentException("Le produit que vous essayez de supprimer est introuvable.");
		else {
			listeProduit.remove(idx);
			return true;
		}
	}
	
	@Override
	public Produit getById(int id) throws IllegalArgumentException {
		int idx = listeProduit.indexOf(new Produit(id, "", "", 0, "", 0));
		if(idx == -1) throw new IllegalArgumentException("Le produit recherche est introuvable.");
		else {
			return listeProduit.get(idx);
		}
	}

	@Override
	public ArrayList<Produit> getAll() {
		return listeProduit;
	}

	@Override
	public ArrayList<Produit> research(Produit produitRecherche) {
		List<Produit> result = listeProduit.stream()
								.filter(produit -> 
								produit.getNom().contains(produitRecherche.getNom())
								&& produit.getTarif() <= produitRecherche.getTarif()
								&& (produit.getIdCategorie() == produitRecherche.getIdCategorie() || (produitRecherche.getIdCategorie() == -1)) 
								)
								.collect(Collectors.toList());
		return new ArrayList<Produit>(result);
	}
}
