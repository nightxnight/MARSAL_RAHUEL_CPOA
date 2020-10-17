package vue.console;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import dao.DAOFactory;
import entities.Client;;

public class MenuClient extends Menu {
	
	//Constantes de messages :
	
	//Accueil 
	private final String messageBienvenue = "\nGESTION DES CLIENTS";
	private final String messageOption = "\t1. Ajouter un client.\n\t2. Modifier un client.\n\t3. Supprimer un client."
			+ "\n\t4. Afficher tous les clients\n\t5. Obtenir un client par id\n\t6. Retour";
	private final String messageIndicAccueil = "Entrez le numero correspondant à l'option désiree pour y acceder.";
	private final String messageNavig = "Pressez la touche \"entree\" des lors que vous souhaitez poursuivre la naviguation";
	
	//Ajouter un client
	private final String messageIndicAjout = "--AJOUT D'UN CLIENT--";
	
	//Modifier un client
	private final String messageIndicModif = "--MODIFICATION D'UN CLIENT--";
	
	//Supprimer un client
	private final String messageIndicSupp = "--SUPPRESSION D'UN CLIENT--";

	//Obtenir toutes les clients
	private final String messageTousClient = "--AFFICHAGE DE TOUS LES CLIENTS--";
	
	//Obtenir un client par id
	private final String messageClientById = "--RECHERCHE UN CLIENT PAR ID--";
	
	//Constantes d'�tat
	private final int ETAT_AJOUTER = 1;
	private final int ETAT_MODIFIER = 2;
	private final int ETAT_SUPPRIMER = 3;
	private final int ETAT_TOUT_CLIENT = 4;
	private final int ETAT_ID_CLIENT = 5;
	private final int ETAT_RETOUR = 6;

	@Override
	public void boucle() {
		afficherAccueil();
		changerEtat();
		switch(etat) {
			case ETAT_AJOUTER : afficherAjout(); break;
			case ETAT_MODIFIER : afficherModif(); break;
			case ETAT_SUPPRIMER : afficherSupp(); break;
			case ETAT_TOUT_CLIENT : afficherTousClient(); break;
			case ETAT_ID_CLIENT : afficherClientById(); break;
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
		
		System.out.println("Entrez les attributs du client qui va être ajoute : ");
		Client client= lireClient();
		
		if(confirmRequest()) {
			DAOFactory.getDAOFactory(PERSISTANCE).getClientDAO().create(client);
		}
	}
	
	private void afficherModif() {
		System.out.println(messageIndicModif);

		int idClientModifie = -1;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Entrez l'id du client qui va etre modifie : ");
		do {
			try {
				idClientModifie = sc.nextInt();
			} catch (InputMismatchException ime) {
				System.out.println("Un id est un nombre positif.");
				idClientModifie = -1;
			} finally {
				sc = new Scanner(System.in);
			}
		} while(idClientModifie < 0);
		
		
		System.out.println("Entrez les nouveaux attributs du client : ");
		Client client = lireClient();
		client.setIdClient(idClientModifie);
		
		if(confirmRequest()) {
			try {
				DAOFactory.getDAOFactory(PERSISTANCE).getClientDAO().update(client);
			} catch(IllegalArgumentException iae) {
				System.out.println(iae.getMessage());
			}
		}
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
		
		int idClientSupprime;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Entrez l'id du client qui va etre supprime : ");
		
		try {
			idClientSupprime = sc.nextInt();
		} catch (InputMismatchException nfe) {
			System.out.println("Un id est un nombre positif.");
			idClientSupprime = -1;
		} finally {
			sc = new Scanner(System.in);
		}
		
		if(confirmRequest()) {
			try {
				DAOFactory.getDAOFactory(PERSISTANCE).getClientDAO().delete(new Client(idClientSupprime, "", "", "", "", "", "", "", "", ""));
			} catch(IllegalArgumentException iae) {
				System.out.println(iae.getMessage());
			}
		}
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
	
	private void afficherClientById() {
		System.out.println(messageClientById + "\n");
		
		Client client;
		int idClient = -1;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Entrez l'id du client que vous voulez consultez");
		
		do {
			try {
				idClient = sc.nextInt();
			} catch(InputMismatchException ime) {
				System.out.println("Un id est un nombre strictement positif.");
				idClient = -1;
			} finally {
				sc = new Scanner(System.in);
			}
		} while(idClient < 0);
		
		if(confirmRequest()) {
			try {
				client = DAOFactory.getDAOFactory(PERSISTANCE).getClientDAO().getById(idClient);
				System.out.println(client.toString());
			} catch(IllegalArgumentException iae) {
				System.out.println(iae.getMessage());
			}
		}
	}
}
