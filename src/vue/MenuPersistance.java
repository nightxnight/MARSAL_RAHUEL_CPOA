package vue;

import modele.dao.DAOFactory;
import modele.dao.Persistance;

public class MenuPersistance extends Menu {
	
	//Accueil 
		private final String messageBienvenue = "\nMODE DE TRAVAIL";
		private final String messageOption = "\t1. Base de données MYSQL.\n\t2. Mode hors-ligne.\n\t3. Retour";
		private final String messageIndicAccueil = "Entrez le numero correspondant à l'option désiree pour y acceder.";
		private final String messageNavig = "Pressez la touche \"entree\" des lors que vous souhaitez poursuivre la naviguation";
	//Constante d'état
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
		if(closeAllDAO()) {
			System.out.println("Vous travaillez désormais en base de donnée MYSQL : ");
			PERSISTANCE = Persistance.MYSQL;
			DAOFactory.getDAOFactory(PERSISTANCE);
		}
	}
	
	private void afficherListeMemoire() {
		if(closeAllDAO()) {
			System.out.println("Vous êtes désormais en mode hors-ligne");
			PERSISTANCE = Persistance.LISTEMEMOIRE;
			DAOFactory.getDAOFactory(PERSISTANCE);
		}
		
	}
	
	private boolean closeAllDAO() {
		boolean closed = true;
		for(Persistance cible : Persistance.values()) {
			System.out.println("Closing");
			closed = DAOFactory.getDAOFactory(cible).closeDAO();
		}	
		return closed;
	}

}
