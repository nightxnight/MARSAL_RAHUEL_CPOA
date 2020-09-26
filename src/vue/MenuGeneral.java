package vue;

import java.util.HashMap;

import modele.dao.DAOFactory;
import modele.dao.Persistance;

public class MenuGeneral extends Menu {
	
	
	private final String messageBienvenue = "\nLA BOUTIQUE DES PULLS MOCHES";
	private final String messageOption = "\t1. Gestion des catégories.\n\t2. Gestion des clients.\n\t3. Gestion des produits"
			+ "\n\t4. Quitter";
	private final String messageIndicAccueil = "Entrez le numéro correspondant à l'option désirée pour y accéder.";
	private final String messageNavig = "Pressez la touche \"entrée\" dès lors que vous désirez poursuivre la naviguation.";
	
	private HashMap<Integer, Menu> menuMap;
	
	//Constantes d'�tat
		private final int ETAT_CATEG = 1;
		private final int ETAT_CLIENT = 2;
		private final int ETAT_PRODUIT = 3;
		private final int ETAT_QUITTER = 4;
		
	public MenuGeneral() {
		super();
		PERSISTANCE = Persistance.LISTEMEMOIRE;
		//Permet d'appeller le constructeur lier pour créer l'instance.
		DAOFactory.getDAOFactory(PERSISTANCE);
		menuMap = new HashMap<Integer, Menu>();
		menuMap.put(ETAT_CATEG, new MenuCategorie());
		menuMap.put(ETAT_CLIENT, new MenuClient());
		menuMap.put(ETAT_PRODUIT, new MenuProduit());
		start();
	}
		
	@Override
	public void boucle() {
		if(etat == ETAT_ACCUEIL) {
			afficherAccueil();
			changerEtat();
		}
		else if(etat==ETAT_QUITTER)	quit();
		else {
			if(!menuMap.get(etat).isRunning()) etat = ETAT_ACCUEIL;
			else menuMap.get(etat).boucle();
		}
	}
	
	@Override
	public void changerEtat() {
		super.changerEtat();
		for(int i = 0; i < menuMap.size(); i++) {
			if(etat==i+1) menuMap.get(i+1).start();
			else menuMap.get(i+1).quit();
		}
		if(!(etat > ETAT_ACCUEIL && etat <= ETAT_QUITTER)) etat = ETAT_ACCUEIL;
	}
	
	//Ci-dessous toutes les m�thodes d'affichages dans la console
	private void afficherAccueil() {
		System.out.println(messageBienvenue + "\n");
		System.out.println(messageOption + "\n");
		System.out.println(messageIndicAccueil);
		System.out.println(messageNavig);
	}
	
	@Override
	public void quit() {
		super.quit();
		System.out.println("Merci de votre visite, à très bientôt!");
	}
}
