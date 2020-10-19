package vue.application.management;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import vue.application.management.entities.CategorieUIManagement;
import vue.application.management.entities.ClientUIManagement;
import vue.application.management.entities.CommandeUIManagement;
import vue.application.management.entities.ProduitUIManagement;

public abstract class UIManagement {

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
}
