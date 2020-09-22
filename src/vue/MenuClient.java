package vue;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import entities.client.Client;
import modele.dao.DAOFactory;;

public class MenuClient extends Menu {
	
	//Constantes de messages :
	
	//Accueil 
	private final String messageBienvenue = "\nGESTION DES CLIENTS";
	private final String messageOption = "\t1. Ajouter un client.\n\t2. Modifier un client.\n\t3. Supprimer un client."
			+ "\n\t4. Afficher tous les clients\n\t5. Retour";
	private final String messageIndicAccueil = "Entrez le numéro correspondant à l'option désiree pour y accéder.";
	private final String messageNavig = "Pressez la touche \"entrée\" dès lors que vous souhaitez poursuivre la naviguation";
	
	//Ajouter un client
	private final String messageIndicAjout = "--AJOUT D'UN CLIENT--";
	
	//Modifier un client
	private final String messageIndicModif = "--MODIFICATION D'UN CLIENT--";
	
	//Supprimer un client
	private final String messageIndicSupp = "--SUPPRESSION D'UN CLIENT--";
	
	
	//Obtenir toutes les clients
	private final String messageTousClient = "--AFFICHAGE DE TOUS LES CLIENTS--";
	
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
		
		Client c = lireClient();
		
		DAOFactory.getDAOFactory(PERSISTANCE).getClientDAO().create(c);
	}
	
	private void afficherModif() {
		System.out.println(messageIndicModif);

		int idClientModifie = -1;
		Client nouveauClient;
		
		Scanner sc = new Scanner(System.in);
		
		do {
			try {
				idClientModifie = sc.nextInt();
			} catch (InputMismatchException nfe) {
				System.out.println("Entrez un nombre positif.");
				idClientModifie = -1;
			} finally {
				sc.next();
			}
		} while(idClientModifie < 0);
		
		
		System.out.println("Entrez les nouveaux attributs du client");
		nouveauClient  = lireClient();
		
		DAOFactory.getDAOFactory(PERSISTANCE).getClientDAO().update(idClientModifie, nouveauClient);
	}
	
	private Client lireClient(int idClient) {
		Client C = lireClient();
		C.setIdClient(idClient);
		return C;
	}
	
	private Client lireClient() {
		String nom ;
		String prenom;
		String identifiant;
		String mdp;
		String adrNumero;
		String adrVoie;
		String adrCodePostal;
		String adrVille;
		String adrPays;
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Entrez le nom");
		nom = sc.nextLine();
		
		System.out.println("Entrez le prenom");
		prenom = sc.nextLine();
		
		System.out.println("Entrez l'identifiant du client");
		identifiant = sc.nextLine();
		
		System.out.println("Entrez le mot de passe");
		mdp = sc.nextLine();
		
		System.out.println("Entrez le numéro de l'adresse");
		adrNumero = sc.nextLine();
		
		System.out.println("Entrez la voie de l'adresse");
		adrVoie = sc.nextLine();
		
		System.out.println("Entrez le code postal");
		adrCodePostal = sc.nextLine();
		
		System.out.println("Entrez la ville");
		adrVille = sc.nextLine();
		
		System.out.println("Entrez le pays");
		adrPays = sc.nextLine();
		
		return new Client(nom.trim(), prenom.trim(), identifiant.trim(), mdp.trim(), adrNumero.trim(), adrVoie.trim(), adrCodePostal.trim(), adrVille.trim(), adrPays.trim());
	}
	
	private void afficherSupp() {
		System.out.println(messageIndicSupp);
		
		int id;
		Scanner sc = new Scanner(System.in);
		
		try {
			id = sc.nextInt();
		} catch (InputMismatchException nfe) {
			return;
		}
		
		DAOFactory.getDAOFactory(PERSISTANCE).getClientDAO().delete(new Client(1, "", "", "", "", "", "", "", "", ""));
	}
	
	private void afficherTousClient() {
		System.out.println(messageTousClient + "\n");
		
		ArrayList<Client> listeClient = DAOFactory.getDAOFactory(PERSISTANCE).getClientDAO().getAll();
		
		if(listeClient==null || listeClient.size()==0) {
			System.out.println("\tEt bien il semblerait qu'il n'y ait pas de client sur la boutique.");
			return;
		}
		
		for (int i = 0; i < listeClient.size(); i++) {
			System.out.println("\t- " + listeClient.get(i).toString());
		}
	}
}
