package vue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import modele.Modele;

public class Menu {
	
	//Constantes de messages :
	
	//Accueil 
	private final String messageBienvenue = "\nBienvenue dans la boutique de pulls moche!";
	private final String messageOption = "\t1. Ajouter une catégorie.\n\t2. Modifier une catégorie.\n\t3. Supprimer une catégorie."
			+ "\n\t4. Afficher toutes les catégories.\n\t5. Quitter";
	private final String messageIndicAccueil = "Entrez le numéro correspondant à l'option désirée pour y accéder.";
	private final String messageNavig = "Pressez la touche \"entrée\" dès lors que vous désirez poursuivre la naviguation.";
	
	//Ajouter une catégorie
	private final String messageIndicAjout = "Afin d'ajouter une nouvelle catégorie entrez successivement, le titre ainsi que le fichier visuel de votre catégorie."
			+ "\n\tTaper \"CANCEL\" pour revenir à l'accueil.";
	
	//Modifier une catégorie
	private final String messageIndicModif = "Afin de modifier une catégorie entrez successivement, le nom de la catégorie à modifier, le nouveau nom de cette catégorie puis le fichier visuel correspondant."
			+ "\n\tTaper \"CANCEL\" pour revenir à l'accueil.";
	
	//Supprimer une catégorie
	private final String messageIndicSupp = "Afin de supprimer une catégorie tapez le titre de la catégorie que vous souhaitez faire disparaître."
			+ "\n\tTaper \"CANCEL\" pour revenir à l'accueil.";
	
	
	//Obtenir toutes les catégories
	private final String messageToutesCateg = "Voici ci-dessous, toutes les catégories que vous pourrez retrouver au sein de notre boutique";


	//Quitter
	private final String messageQuitter = "Au revoir, on espère vous revoir sur la boutique de pulls moche!";
	
	//Constantes
	public final int ETAT_ACCUEIL = 0;
	public final int ETAT_AJOUTER = 1;
	public final int ETAT_MODIFIER = 2;
	public final int ETAT_SUPPRIMER = 3;
	public final int ETAT_TOUTES_CATEG = 4;
	public final int ETAT_QUITTER = 5;
	
	//Attribut
	
	private int etat;
	private boolean running = false;
	
	//Constructeur
	
	public Menu() {
		etat = ETAT_ACCUEIL;
		if(Modele.creerConnexion()) {
			running = true;
		}
	}
	
	//Méthodes

	public void boucle() {
		afficherAccueil();
		changerEtat();
		switch(etat) {
			case ETAT_AJOUTER : afficherAjout(); break;
			case ETAT_MODIFIER : afficherModif(); break;
			case ETAT_SUPPRIMER : afficherSupp(); break;
			case ETAT_TOUTES_CATEG : afficherToutesCateg(); break;
			case ETAT_QUITTER : afficherQuitter(); break;
		}
		etat = ETAT_ACCUEIL;
	}
		
	private void changerEtat() {
		Scanner sc = new Scanner(System.in);
		int inputEtat;
		try {
			inputEtat = sc.nextInt();
		} catch(InputMismatchException ime) {
			inputEtat = ETAT_ACCUEIL;
		}		
		etat = inputEtat;
	}
	
	//Ci-dessous toutes les méthodes d'affichages dans la console
	private void afficherAccueil() {
		System.out.println(messageBienvenue + "\n");
		System.out.println(messageOption + "\n");
		System.out.println(messageIndicAccueil);
		System.out.println(messageNavig);
	}
	
	private void afficherAjout() {
		System.out.println(messageIndicAjout);
		
		String nomCategorie;
		String fichierVisuel;
		Scanner sc = new Scanner(System.in);
		
		nomCategorie = sc.nextLine();
		if(nomCategorie.contentEquals("CANCEL")) return;
		
		fichierVisuel = sc.nextLine();
		if(fichierVisuel.equals("CANCEL")) return;
		
		Modele.ajouterCategorie(nomCategorie, fichierVisuel);
	}
	
	private void afficherModif() {
		System.out.println(messageIndicModif);
		
		String  nomCategorieModifiee;
		String nouveauNomCateg;
		String nouveauFichierVisuel;
		Scanner sc = new Scanner(System.in);
		
		nomCategorieModifiee = sc.nextLine();
		if(nomCategorieModifiee.equals("CANCEL")) return;
		
		nouveauNomCateg = sc.nextLine();
		if(nouveauNomCateg.equals("CANCEL")) return;
		
		nouveauFichierVisuel = sc.nextLine();
		if(nouveauFichierVisuel.equals("CANCEL")) return;
		
		Modele.modifierCategorie(nomCategorieModifiee, nouveauNomCateg, nouveauFichierVisuel);
	}
	
	private void afficherSupp() {
		System.out.println(messageIndicSupp);
		
		String nomCategorieSupprimee;
		Scanner sc = new Scanner(System.in);
		
		nomCategorieSupprimee = sc.nextLine();
		if(nomCategorieSupprimee.equals("CANCEL")) return;
		
		Modele.supprimerCategorie(nomCategorieSupprimee);
	}
	
	private void afficherToutesCateg() {
		System.out.println(messageToutesCateg + "\n");
		
		ArrayList<String> listeCateg = Modele.obtenirToutesCategories();
		
		if(listeCateg==null) {
			System.out.println("\tEt bien il semblerait que la boutique ne dispose pas encore de catégorie.");
			return;
		}
		
		for (int i = 0; i < listeCateg.size(); i++) {
			System.out.println("\t- " + listeCateg.get(i));
		}
	}
	
	private void afficherQuitter() {
		System.out.println(messageQuitter);
		running = false;
		try {
			Modele.getConnexion().close();
		} catch (SQLException e) {
			System.out.println("Ce message n'est sensé jamais s'afficher, si c'est le cas, alors c'est vraiment pas cool");
		}
	}
	
	public boolean isRunning() {
		return running;
	}
}
