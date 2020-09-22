package vue;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import entities.categorie.Categorie;
import modele.dao.DAOFactory;

public class MenuCategorie extends Menu{
	
	//Constantes de messages :
	
	//Accueil 
	private final String messageBienvenue = "\nGESTION DES CATEGORIES";
	private final String messageOption = "\t1. Ajouter une catégorie.\n\t2. Modifier une catégorie.\n\t3. Supprimer une catégorie."
			+ "\n\t4. Afficher toutes les catégories.\n\t5. Retour";
	private final String messageIndicAccueil = "Entrez le numéro correspondant à l'option désirée pour y accéder.";
	private final String messageNavig = "Pressez la touche \"entrée\" dès lors que vous désirez continuer la naviguation";
	
	//Ajouter une catégorie
	private final String messageIndicAjout = "--AJOUT DE CATEGORIE--";
	
	//Modifier une catégorie
	private final String messageIndicModif = "--MODIFICATION D'UNE CATEGORIE--";
	//Supprimer une catégorie
	private final String messageIndicSupp = "--SUPPRESSION D'UN PRODUIT";
	
	
	//Obtenir toutes les catégories
	private final String messageToutesCateg = "--AFFICHAGE DE TOUTES LES CATEGORIES : --";

	
	//Constantes d'�tat
	private final int ETAT_AJOUTER = 1;
	private final int ETAT_MODIFIER = 2;
	private final int ETAT_SUPPRIMER = 3;
	private final int ETAT_TOUTES_CATEG = 4;
	private final int ETAT_RETOUR = 5;
		
	//Constructeur
	
	public MenuCategorie() {
		super();
	}
	
	//M�thodes

	public void boucle() {
		afficherAccueil();
		changerEtat();
		switch(etat) {
			case ETAT_AJOUTER : afficherAjout(); break;
			case ETAT_MODIFIER : afficherModif(); break;
			case ETAT_SUPPRIMER : afficherSupp(); break;
			case ETAT_TOUTES_CATEG : afficherToutesCateg(); break;
			case ETAT_RETOUR : quit(); break;
		}
		etat = ETAT_ACCUEIL;
	}
		
	//Ci-dessous toutes les m�thodes d'affichages dans la console
	private void afficherAccueil() {
		System.out.println(messageBienvenue + "\n");
		System.out.println(messageOption + "\n");
		System.out.println(messageIndicAccueil);
		System.out.println(messageNavig);
	}
	
	private void afficherAjout() {
		System.out.println(messageIndicAjout);
		Categorie categ = lireCategorie();
		
		DAOFactory.getDAOFactory(PERSISTANCE).getCategorieDAO().create(categ);
	}
	
	private void afficherModif() {
		System.out.println(messageIndicModif);
		
		int idCategorieModifie = -1;
		Categorie nouvelleCateg;
		Scanner sc = new Scanner(System.in);
		System.out.println("Entrez l'id de la categorie qui va etre modfiee");
		do {
			try {
				idCategorieModifie = sc.nextInt();
			} catch (InputMismatchException nfe) {
				System.out.println("Entrez un nombre positif.");
				idCategorieModifie = -1;
			} finally {
				sc.next();
			}
		} while(idCategorieModifie < 0);
		
		System.out.println("Entrez les nouveaux attributs de la categorie");
		nouvelleCateg = lireCategorie();
		
		DAOFactory.getDAOFactory(PERSISTANCE).getCategorieDAO().update(new Categorie(idCategorieModifie, "", ""), nouvelleCateg);
	
	}
	
	private Categorie lireCategorie(int idCategorie) {
		Categorie C = lireCategorie();
		C.setIdCategorie(idCategorie);
		return C;
	}
	
	private Categorie lireCategorie() {
		String titre;
		String visuel;
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Entrez le titre de la categorie");
		titre = sc.nextLine();
		
		System.out.println("Entrez le nom du visuel de la carégorie");
		visuel = sc.nextLine();
		
		return new Categorie(titre.trim(), visuel.trim());
	}
	
	private void afficherSupp() {
		System.out.println(messageIndicSupp);
		
		int idCategorie;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Entrez l'id de la categorie que vous voulez supprimer");
		
		try {
			idCategorie = sc.nextInt();
		} catch (InputMismatchException nfe) {
			return;
		}
		
		DAOFactory.getDAOFactory(PERSISTANCE).getCategorieDAO().delete(new Categorie(idCategorie, "", ""));
	}
	
	private void afficherToutesCateg() {
		System.out.println(messageToutesCateg + "\n");
		
		ArrayList<Categorie> listeCategorie = DAOFactory.getDAOFactory(PERSISTANCE).getCategorieDAO().getAll();
		
		if(listeCategorie==null || listeCategorie.size()==0) {
			System.out.println("\tEt bien il semblerait que la boutique ne dispose pas encore de categories.");
			return;
		}
		
		for (int i = 0; i < listeCategorie.size(); i++) {
			System.out.println("\t- " + listeCategorie.toString());
		}
	}	
}
