package vue.console;

import dao.DAOFactory;
import dao.Persistance;

public class MenuPersistance extends Menu {
	
	//Accueil 
		private final String messageBienvenue = "\nMODE DE TRAVAIL";
		private final String messageOption = "\t1. Base de donn�es MYSQL.\n\t2. Mode hors-ligne.\n\t3. Retour";
		private final String messageIndicAccueil = "Entrez le numero correspondant � l'option d�siree pour y acceder.";
		private final String messageNavig = "Pressez la touche \"entree\" des lors que vous souhaitez poursuivre la naviguation";
	//Constante d'�tat
	private final int ETAT_SQL = 1;
	private final int ETAT_LISTEMEMOIRE = 2;
	private final int ETAT_RETOUR = 3;
	
	public MenuPersistance() {
		super();
	}
	
	@Override
	public void boucle() {
		afficherAccueil();
		changerEtat();
		
		switch(etat) {
		case ETAT_SQL : 
			afficherSQL(); break;
			
		case ETAT_LISTEMEMOIRE :
			afficherListeMemoire(); break;
			
		case ETAT_RETOUR : 
			quit();
			
		}
		etat = ETAT_ACCUEIL;
	}

	private void afficherAccueil() {
		System.out.println(messageBienvenue + "\n");
		System.out.println(messageOption + "\n");
		System.out.println(messageIndicAccueil);
		System.out.println(messageNavig);
	}
	
	private void afficherSQL() {
		if(closeCurrentMode()) {
			System.out.println("Vous travaillez desormais en base de donnees MYSQL : ");
			PERSISTANCE = Persistance.MYSQL;
			DAOFactory.getDAOFactory(PERSISTANCE);
		}
	}
	
	private void afficherListeMemoire() {
		if(closeCurrentMode()) {
			System.out.println("Vous etes desormais en mode hors-ligne");
			PERSISTANCE = Persistance.LISTEMEMOIRE;
			DAOFactory.getDAOFactory(PERSISTANCE);
		}
		
	}
	
	private boolean closeCurrentMode() {
		return DAOFactory.getDAOFactory(PERSISTANCE).closeDAO();
	}

}
