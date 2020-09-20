package vue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import entities.client.Client;;

public class MenuClient extends Menu {
	
	//Constantes de messages :
	
	//Accueil 
	private final String messageBienvenue = "\nGESTION DES CLIENTS";
	private final String messageOption = "\t1. Ajouter un client.\n\t2. Modifier un client.\n\t3. Supprimer un client."
			+ "\n\t4. Afficher tous les clients\n\t5. Retour";
	private final String messageIndicAccueil = "Entrez le numéro correspondant à l'option désir�e pour y accéder.";
	private final String messageNavig = "Pressez la touche \"entrée\" dès lors que vous souhaitez poursuivre la naviguation";
	
	//Ajouter un client
	private final String messageIndicAjout = "Afin d'ajouter un nouveau client entrez successivement son nom puis son prénom."
			+ "\n\tTaper \"CANCEL\" pour revenir à l'accueil.";
	
	//Modifier un client
	private final String messageIndicModif = "Afin de modifier un client, entrez successivement le nom puis le prenom du client modifie, apres entrez son nouveau nom et prénom."
			+ "\n\tTaper \"CANCEL\" pour revenir à l'accueil.";
	
	//Supprimer un client
	private final String messageIndicSupp = "Afin de supprimer un client entrez successivement son nom puis son prénom."
			+ "\n\tTaper \"CANCEL\" pour revenir à l'accueil.";
	
	
	//Obtenir toutes les clients
	private final String messageTousClient = "Ci-dessous se trouve la liste de tous les clients utilisant la boutique :";
	
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
			case ETAT_TOUTES_CATEG : afficherTousClient(); break;
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
		
		String nomClient;
		String prenomClient;
		Scanner sc = new Scanner(System.in);
		
		nomClient = sc.nextLine();
		if(nomClient.contentEquals("CANCEL")) return;
		
		prenomClient = sc.nextLine();
		if(prenomClient.equals("CANCEL")) return;
		
		Modele.ajouterClient(nomClient, prenomClient);
	}
	
	private void afficherModif() {
		System.out.println(messageIndicModif);

		String  nomClientModifie;
		String prenomClientModifie;
		String nouveauNomClient;
		String nouveauPrenomClient;
		Scanner sc = new Scanner(System.in);
		
		nomClientModifie = sc.nextLine();
		if(nomClientModifie.equals("CANCEL")) return;
		
		prenomClientModifie = sc.nextLine();
		if(prenomClientModifie.equals("CANCEL")) return;
		
		nouveauNomClient = sc.nextLine();
		if(nouveauNomClient.equals("CANCEL")) return;
		
		nouveauPrenomClient = sc.nextLine();
		if(nouveauPrenomClient.equals("CANCEL")) return;
		
		Modele.modifierClient(new Client(nomClientModifie, prenomClientModifie), new Client(nouveauNomClient, nouveauPrenomClient));
	}
	
	private void afficherSupp() {
		System.out.println(messageIndicSupp);
		
		String nomClient;
		String prenomClient;
		Scanner sc = new Scanner(System.in);
		
		nomClient = sc.nextLine();
		if(nomClient.equals("CANCEL")) return;
		
		prenomClient = sc.nextLine();
		if(prenomClient.equals("CANCEL")) return;
		
		Modele.supprimerClient(nomClient, prenomClient);
	}
	
	private void afficherTousClient() {
		System.out.println(messageTousClient + "\n");
		
		ArrayList<Client> listeClient = Modele.obtenirTousClients();
		
		if(listeClient==null || listeClient.size()==0) {
			System.out.println("\tEt bien il semblerait qu'il n'y ait pas de client sur la boutique.");
			return;
		}
		
		for (int i = 0; i < listeClient.size(); i++) {
			System.out.println("\t- " + listeClient.get(i).getNom() + " " + listeClient.get(i).getPrenom());
		}
	}
}
