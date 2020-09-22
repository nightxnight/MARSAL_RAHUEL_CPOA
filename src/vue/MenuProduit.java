package vue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

import entities.produit.Produit;
import modele.dao.DAOFactory;

public class MenuProduit extends Menu{
	
	//Constantes de messages :
	
	//Accueil 
	private final String messageBienvenue = "\n--GESTION DES PRODUITS--";
	private final String messageOption = "\t1. Ajouter un produit\n\t2. Modifier un produit.\n\t3. Supprimer un produit."
			+ "\n\t4. Afficher tous les produits.\n\t5. Retour";
	private final String messageIndicAccueil = "Entrez le numero correspondant a l'option desiree pour y acceder.";
	private final String messageNavig = "Pressez la touche \"entree\" des lors que vous désirez poursuivre la naviguation";
	
	//Ajouter un produit
	private final String messageIndicAjout = "--AJOUT DE PRODUIT--";
	
	//Modifier un produit
	private final String messageIndicModif = "--MODIFICATION DE PRODUIT--";
	
	//Supprimer une produit
	private final String messageIndicSupp = "--SUPPRESSION DE PRODUIT--";
	
	
	//Obtenir toutes les produits
	private final String messageTousProduit = "--LISTE DES PRODUITS--";

	//Constantes d'�tat
	private final int ETAT_AJOUTER = 1;
	private final int ETAT_MODIFIER = 2;
	private final int ETAT_SUPPRIMER = 3;
	private final int ETAT_TOUTES_CATEG = 4;
	private final int ETAT_RETOUR = 5;
	
	@Override
	public void boucle() {
		afficherAccueil();
		changerEtat();
		switch(etat) {
			case ETAT_AJOUTER : afficherAjout(); break;
			case ETAT_MODIFIER : afficherModif(); break;
			case ETAT_SUPPRIMER : afficherSupp(); break;
			case ETAT_TOUTES_CATEG : afficherTousProduit(); break;
			case ETAT_RETOUR : quit(); break;
		}
		etat = ETAT_ACCUEIL;
	}
	
	private void afficherAccueil() {
		System.out.println(messageBienvenue + "\n");
		System.out.println(messageOption + "\n");
		System.out.println(messageIndicAccueil);
		System.out.println(messageNavig);
	}
	
	private void afficherAjout() {
		System.out.println(messageIndicAjout);
		
		System.out.println("Nous allons entrez les informations concernant le produit qui va etre ajouter : ");
		Produit produit;
		produit = lireProduit();
		if(confirmRequest())
			DAOFactory.getDAOFactory(PERSISTANCE).getProduitDAO().create(produit);
	}
	
	private void afficherModif() {
		System.out.println(messageIndicModif);
		
		int idProduitModifie = -1;
		Produit nouveauProduit;		
		
		System.out.println("id du produit qui va etre modifie : ");
		do {
			try {
				idProduitModifie = readId();
			} catch (IOException ioe) {
				System.out.println("Un id de produit est un entier positif");
				idProduitModifie = -1;
			}
		} while(idProduitModifie < 0);
		
		System.out.println("Desormais, entrez les nouveaux attributs de ce produit : ");
		nouveauProduit = lireProduit();
		if(confirmRequest())
			DAOFactory.getDAOFactory(PERSISTANCE).getProduitDAO().update(new Produit(idProduitModifie, "", "", 0, "", 0), nouveauProduit);
	}
	
	private Produit lireProduit(int idProduit) {
		Produit produit;
		produit = lireProduit();
		produit.setId(idProduit);
		return produit;
	}
	
	private Produit lireProduit() {
		String nom;
		String description;
		double tarif = -1;
		String visuel;
		int idCategorie = -1;
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in).useLocale(Locale.FRANCE); //les chiffres de type double s'écrivent comme en france avec une ,

		System.out.println("Entrez le nom du produit : ");
		nom = sc.nextLine();
		
		System.out.println("Entrez la description du produit : ");
		description = sc.nextLine();
		
		System.out.println("Entrez le tarif unitaire : ");
		do {
			try {
				tarif = sc.nextDouble();
			} catch(InputMismatchException ime) {
				System.out.println("Un prix est un réel positif.");
				tarif = -1;
			} finally {
				sc.next();
			}
		} while(tarif < 0); 
		
		System.out.println("Entrez le nom du fichier visuel associé : ");
		visuel = sc.nextLine();
		
		System.out.println("Entrez le numéro de la catégorie associé à votre produit : ");
		do {
			try {
				idCategorie = sc.nextInt();
			} catch(InputMismatchException ime) {
				System.out.println("Un numéro de catégorie est un entier positif.");
			}
		} while(idCategorie < 0);
		
		if(sc != null) sc.close();
		
		return new Produit(nom.trim(), description.trim(), tarif, visuel.trim(), idCategorie);
	}

	private void afficherSupp() {
		System.out.println(messageIndicSupp);
		
		int idProduitSupprime = -1;
		
		System.out.println("id du produit qui va etre supprime : ");
		do {
			try {
				idProduitSupprime = readId();
			} catch(IOException ioe) {
				idProduitSupprime = -1;
				System.out.println("Un id de produit est un entier positif.");
			}
		} while(idProduitSupprime < 0);
		
		if(confirmRequest())
			DAOFactory.getDAOFactory(PERSISTANCE).getProduitDAO().delete(new Produit(idProduitSupprime, "", "", 0, "", 0));
	}
	
	private void afficherTousProduit() {
		System.out.println(messageTousProduit + "\n");
		
		ArrayList<Produit> listeProduit = DAOFactory.getDAOFactory(PERSISTANCE).getProduitDAO().getAll();
		
		if(listeProduit==null || listeProduit.size()==0) {
			System.out.println("\tEt bien il semblerait que la boutique ne dispose pas encore de produit.");
			return;
		}
		
		for (int i = 0; i < listeProduit.size(); i++) {
			System.out.println("\t- " + listeProduit.get(i).toString());
		}
	}	
}
