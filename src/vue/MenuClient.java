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
		String identifiant;
		String motDePasse;
		String adrNumero;
		String adrVoie;
		String adrCodePostal;
		String adrVille;
		String adrPays;
		
		Scanner sc = new Scanner(System.in);
		
		nomClient = sc.nextLine();
		if(nomClient.equals("CANCEL")) return;
		
		prenomClient = sc.nextLine();
		if(prenomClient.equals("CANCEL")) return;
		
		identifiant = sc.nextLine();
		if(identifiant.equals("CANCEL")) return;
		
		motDePasse = sc.nextLine();
		if(motDePasse.equals("CANCEL")) return;
		
		adrNumero = sc.nextLine();
		if(adrNumero.equals("CANCEL")) return;
		
		adrVoie = sc.nextLine();
		if(adrVoie.equals("CANCEL")) return;
		
		adrCodePostal = sc.nextLine();
		if(adrCodePostal.equals("CANCEL")) return;
		
		adrVille = sc.nextLine();
		if(adrVille.equals("CANCEL")) return;
		
		adrPays = sc.nextLine();
		if(adrPays.equals("CANCEL")) return;
		
		DAOFactory.getDAOFactory(PERSISTANCE).getClientDAO().create(new Client(nomClient, prenomClient, identifiant, motDePasse, adrNumero, adrVoie, adrCodePostal, adrVille, adrPays));
	}
	
	private void afficherModif() {
		System.out.println(messageIndicModif);

		int idClientModifie = -1;
		String nouveauNomClient;
		String nouveauPrenomClient;
		String nouveauIdentifiant;
		String nouveauMotDePasse;
		String nouveauAdrNumero;
		String nouveauAdrVoie;
		String nouveauAdrCodePostal;
		String nouveauAdrVille;
		String nouveauAdrPays;
		
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
		
		
		nouveauNomClient = sc.nextLine();
		if(nouveauNomClient.equals("CANCEL")) return;
		
		nouveauPrenomClient = sc.nextLine();
		if(nouveauPrenomClient.equals("CANCEL")) return;
		
		nouveauIdentifiant = sc.nextLine();
		if(nouveauIdentifiant.equals("CANCEL")) return;
		
		nouveauMotDePasse = sc.nextLine();
		if(nouveauMotDePasse.equals("CANCEL")) return;
		
		nouveauAdrNumero = sc.nextLine();
		if(nouveauAdrNumero.equals("CANCEL")) return;
		
		nouveauAdrVoie = sc.nextLine();
		if(nouveauAdrVoie.equals("CANCEL")) return;
		
		nouveauAdrCodePostal = sc.nextLine();
		if(nouveauAdrCodePostal.equals("CANCEL")) return;
		
		nouveauAdrVille = sc.nextLine();
		if(nouveauAdrVille.equals("CANCEL")) return;
		
		nouveauAdrPays = sc.nextLine();
		if(nouveauAdrPays.equals("CANCEL")) return;
		
		DAOFactory.getDAOFactory(PERSISTANCE).getClientDAO().update(new Client(idClientModifie, "", "", "", "", "", "", "", "", "")
				, new Client(nouveauNomClient, nouveauPrenomClient, nouveauIdentifiant, nouveauMotDePasse, nouveauAdrNumero, nouveauAdrVoie, nouveauAdrCodePostal, nouveauAdrVille, nouveauAdrPays));
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
