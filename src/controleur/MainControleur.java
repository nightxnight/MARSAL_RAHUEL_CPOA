package controleur;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import vue.application.management.Entities;

public class MainControleur {
	
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
	
	@FXML
	private Label labelFilAriane;
	
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
	        controller.setParent(this);
	        controller.render(entities);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mainPane.setCenter(managementPane);
	}
	
	public BorderPane getMainPane() {
		return mainPane;
	}

	
}
