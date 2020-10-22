package vue.application.management;

import controleur.MainControleur;
import controleur.research.RechercheControleur;
import vue.application.management.entities.CategorieUIManagement;
import vue.application.management.entities.ClientUIManagement;
import vue.application.management.entities.CommandeUIManagement;
import vue.application.management.entities.ProduitUIManagement;

public abstract class UIManagement {

	protected MainControleur parent;
	protected RechercheControleur researchControler;

	public static Management getUIManagement (Entities entities) {
		Management uiM = null;
		
		switch(entities) {
		case CATEGORIE:
			uiM = CategorieUIManagement.getInstance(); break;
		
		case PRODUIT:
			uiM = ProduitUIManagement.getInstance(); break;
		
		case CLIENT:
			uiM = ClientUIManagement.getInstance(); break;
		
		case COMMANDE:
			uiM = CommandeUIManagement.getInstance(); break;
		}		
		return uiM;
	}
	
	public void setParent(MainControleur parent) {
		this.parent = parent;
	}
	
	public RechercheControleur getResearch() {
		return researchControler;
	}
}
