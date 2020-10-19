package controleur;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import vue.application.management.Entities;
public class MainControleur implements Initializable{
	
	//Conteneur principale
	@FXML
	private BorderPane mainPane; 
	
	//Conteneur enfant
	private BorderPane managementPane;	
	
	@FXML
	private MenuItem menuItemCategorie;
	@FXML
	private MenuItem menuItemProduit;
	@FXML
	private MenuItem menuItemClient;
	@FXML
	private MenuItem menuItemCommande;
	
	public void showCategories() {
		showManagementPane(Entities.CATEGORIE);
	}
	
	public void showProduits() {
		showManagementPane(Entities.PRODUIT);
	}
	
	public void showClients() {
		showManagementPane(Entities.CLIENT);
	}
	
	public void showCommandes() {
		showManagementPane(Entities.COMMANDE);
	}
	
	public void showManagementPane(Entities entities) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vue/application/ListePane.fxml"));
			managementPane = loader.load();
	        ManagementControleur controller = loader.getController();
	        controller.setParentPane(mainPane);
	        controller.render(entities);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mainPane.setCenter(managementPane);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	public Pane loadFxml(String name) throws IOException {
		Pane loadedPane = FXMLLoader.load(getClass().getResource("/vue/application/" + name + ".fxml"));
		return loadedPane;
	}	
}
