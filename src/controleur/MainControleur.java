package controleur;

import dao.Persistance;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import vue.application.management.Entities;

public class MainControleur {
	
	private Persistance persistance = Persistance.LISTEMEMOIRE;
	
	//Conteneur principale
	@FXML
	private BorderPane mainPane; 
	
	//Conteneur enfant
	private Pane managementPane;
	private ManagementControleur managementControleur;
	private Entities entities;
	
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
		labelFilAriane.setText("> Gestion > Categories");
		loadManagementPane(Entities.CATEGORIE);
	}
	
	public void showProduits() {
		labelFilAriane.setText("> Gestion > Produit");
		loadManagementPane(Entities.PRODUIT);
	}
	
	public void showClients() {
		labelFilAriane.setText("> Gestion > Client");
		loadManagementPane(Entities.CLIENT);
	}
	
	public void showCommandes() {
		labelFilAriane.setText("> Gestion > Commande");
		loadManagementPane(Entities.COMMANDE);
	}
	
	public void loadManagementPane(Entities entities) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/management/managementPane.fxml"));
			managementPane = loader.load();
	        managementControleur = loader.getController();
	        managementControleur.setParent(this);
	        managementControleur.render(entities);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.entities = entities;
		showManagementPane();
	}
	
	public void showManagementPane() {
		if(managementPane == null) return;
		mainPane.setCenter(managementPane);
		labelFilAriane.setText("Gestion > " + entities.getLibelle());
	}
	
	public BorderPane getMainPane() {
		return mainPane;
	}	
	
	public Label getLabelFilAriane() {
		return labelFilAriane;
	}
	
	public ManagementControleur getManagementControleur() {
		return managementControleur;
	}
	
	public Persistance getPersistance() {
		return persistance;
	}
	
	public void setPersistance(Persistance persistance) {
		this.persistance = persistance;
	}
}
