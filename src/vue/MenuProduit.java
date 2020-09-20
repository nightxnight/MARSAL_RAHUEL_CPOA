package vue;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

import entities.produit.Produit;
import modele.dao.DAOFactory;

public class MenuProduit extends Menu{
	
	//Constantes de messages :
	
	//Accueil 
	private final String messageBienvenue = "\nGESTION DES PRODUITS";
	private final String messageOption = "\t1. Ajouter un produit\n\t2. Modifier un produit.\n\t3. Supprimer un produit."
			+ "\n\t4. Afficher tous les produits.\n\t5. Retour";
	private final String messageIndicAccueil = "Entrez le num�ro correspondant � l'option d�sir�e pour y accéder.";
	private final String messageNavig = "Pressez la touche \"entrée\" dès lors que vous désirez poursuivre la naviguation";
	
	//Ajouter un produit
	private final String messageIndicAjout = "Afin d'ajouter un nouveau produit entrez successivement nom, description, tarif, visuel et le numero de categorie."
			+ "\n\tTaper \"CANCEL\" pour revenir à l'accueil.";
	
	//Modifier un produit
	private final String messageIndicModif = "Afin de modifier un produit, entrez l'id du produit à modifié puis entrez successivement nom, description, tarif, visuel et le numero de categorie à changer."
			+ "\n\tTaper \"CANCEL\" pour revenir à l'accueil.";
	
	//Supprimer une produit
	private final String messageIndicSupp = "Afin de supprimer un produit entrez son id."
			+ "\n\tTaper \"CANCEL\" pour revenir à l'accueil.";
	
	
	//Obtenir toutes les produits
	private final String messageTousProduit = "Voici ci-dessous, tous les produits que vous pourrez retrouver au sein de notre boutique";

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
	
	//Ci-dessous toutes les m�thodes d'affichages dans la console
	private void afficherAccueil() {
		System.out.println(messageBienvenue + "\n");
		System.out.println(messageOption + "\n");
		System.out.println(messageIndicAccueil);
		System.out.println(messageNavig);
	}
	
	private void afficherAjout() {
		System.out.println(messageIndicAjout);
		
		String nom;
		String description;
		double tarif;
		String visuel;
		int idCategorie;
		Scanner sc = new Scanner(System.in).useLocale(Locale.FRANCE); //les chiffres de type s'écrivent comme en france avec une ,
		
		nom = sc.nextLine();
		if(nom.contentEquals("CANCEL")) return;
		
		description = sc.nextLine();
		if(description.equals("CANCEL")) return;
		
		try {
			tarif = sc.nextDouble();
		} catch(InputMismatchException ime) {
			return;
		}		
		
		sc = new Scanner(System.in);
		
		visuel = sc.nextLine();
		if(visuel.equals("CANCEL")) return;
		
		try {
			idCategorie = sc.nextInt();
		} catch(InputMismatchException ime) {
			return;
		}		
		
		DAOFactory.getDAOFactory(PERSISTANCE).getProduitDAO().create(new Produit(nom, description, tarif, visuel, idCategorie));
	}
	
	private void afficherModif() {
		System.out.println(messageIndicModif);
		
		int idProduitModifie = -1;
		String nom;
		String description;
		double tarif;
		String visuel;
		int idCategorie;
		Scanner sc = new Scanner(System.in);
		
		do {
			try {
				idProduitModifie = sc.nextInt();
			} catch (InputMismatchException nfe) {
				System.out.println("Entrez un nombre positif.");
				idProduitModifie = -1;
			} finally {
				sc.next();
			}
		} while(idProduitModifie < 0);
		
		nom = sc.nextLine();
		if(nom.contentEquals("CANCEL")) return;
		
		description = sc.nextLine();
		if(description.equals("CANCEL")) return;
		
		try {
			tarif = sc.nextDouble();
		} catch(InputMismatchException ime) {
			return;
		}		
		
		sc = new Scanner(System.in);
		
		visuel = sc.nextLine();
		if(visuel.equals("CANCEL")) return;
		
		try {
			idCategorie = sc.nextInt();
		} catch(InputMismatchException ime) {
			return;
		}		
		
		DAOFactory.getDAOFactory(PERSISTANCE).getProduitDAO().update(new Produit(idProduitModifie, "", "", 0, "", 0), new Produit(nom, description, tarif, visuel, idCategorie));
	}
	
	private void afficherSupp() {
		System.out.println(messageIndicSupp);
		
		int idProduitSupprime;
		Scanner sc = new Scanner(System.in);
		
		try {
			idProduitSupprime = sc.nextInt();
		} catch(InputMismatchException ime) {
			return;
		}	

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
