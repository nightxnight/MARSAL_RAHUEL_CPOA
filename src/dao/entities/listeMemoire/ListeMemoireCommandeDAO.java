package dao.entities.listeMemoire;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import dao.entities.CommandeDAO;
import entities.Commande;

public class ListeMemoireCommandeDAO implements CommandeDAO{
	
	private ArrayList<Commande> listeCommande;
	private static int autoIncrementedId = 0;
	
	private static DateTimeFormatter formatage = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private static ListeMemoireCommandeDAO instance;
	
	private ListeMemoireCommandeDAO() {
		listeCommande = new ArrayList<Commande>();
	    listeCommande.add(new Commande(1, LocalDate.parse("02/09/2020", formatage), 1));
	    listeCommande.add(new Commande(2, LocalDate.parse("30/08/2020", formatage), 1));
	    listeCommande.add(new Commande(3, LocalDate.parse("05/10/2020", formatage), 4));
	    autoIncrementedId = 2;
	}
	
	public static ListeMemoireCommandeDAO getInstance() {
		if(instance==null) instance = new ListeMemoireCommandeDAO();
		return instance;
	}

	@Override
	public boolean create(Commande objet) {		
		++autoIncrementedId;
		while(listeCommande.indexOf(objet) != -1) {
			++autoIncrementedId;
		}
		
		objet.setIdCommande(autoIncrementedId);
		
		return listeCommande.add(objet);
	}

	@Override
	public boolean update(Commande objet) throws IllegalArgumentException {		
		int idx = listeCommande.indexOf(objet);
		
		if(idx == -1) throw new IllegalArgumentException("La commande que vous essayez de modifier est introuvable.");
		else {
			listeCommande.get(idx).setIdClient(objet.getIdClient());
			listeCommande.get(idx).setDateCommande(objet.getDateCommande());
			return true;
		}
	}

	@Override
	public boolean delete(Commande objet) throws IllegalArgumentException {
		int idx = listeCommande.indexOf(objet);
		
		if(idx == -1) throw new IllegalArgumentException("La commande que vous essayez de supprimer est introuvable.");
		else return listeCommande.remove(objet);
	}
	
	public Commande getById(int id) throws IllegalArgumentException {
		int idx = listeCommande.indexOf(new Commande(id, LocalDate.parse("01/01/0001", formatage), 0));
		if(idx == -1) throw new IllegalArgumentException("La commande recherchee est introuvable");
		else {
			return listeCommande.get(idx);
		}
	}

	@Override
	public ArrayList<Commande> getAll() {
		return listeCommande;
	}
}
