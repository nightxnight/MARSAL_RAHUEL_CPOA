package controleur;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dao.Persistance;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vue.application.custom.alert.ConfirmationAlert;
import vue.application.management.Entities;

public class MainControleur implements Initializable{
	
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
	private RadioMenuItem radioThemeClair;
	@FXML
	private RadioMenuItem radioThemeSombre;
	@FXML
	private ToggleGroup groupTheme;
	
	@FXML
	private RadioMenuItem radioMenuListeMemoire;
	@FXML 
	private RadioMenuItem radioMenuMySQL;
	@FXML
	private ToggleGroup groupPersistanceMenu;
	
	@FXML
	private Label labelFilAriane;
	@FXML
	private Label labelPersistance;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		groupPersistanceMenu.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> listeRadioButton, Toggle oldToggle, Toggle newToggle) {
				RadioMenuItem selectedRadioButton = (RadioMenuItem) newToggle;
				if(selectedRadioButton.equals(radioMenuListeMemoire)) {
					persistance = Persistance.LISTEMEMOIRE;
					labelPersistance.setText("Mode liste memoire (hors ligne)");
				} else if(selectedRadioButton.equals(radioMenuMySQL)) {
					persistance = Persistance.MYSQL;
					labelPersistance.setText("Mode base de donnees MySQL");
				}
				reloadManagementPane();
			}
		});
		
		groupTheme.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> listeRadioButton, Toggle oldToggle, Toggle newToggle) {
				RadioMenuItem selectedRadioButton = (RadioMenuItem) newToggle;
				if(selectedRadioButton.equals(radioThemeClair)) {
					//THEME CLAIR
				} else if (selectedRadioButton.equals(radioThemeSombre)) {
					//THEME SOMBRE;
				}
			}
		});
		
		groupTheme.selectToggle(radioThemeClair);
		groupPersistanceMenu.selectToggle(radioMenuMySQL);
	}
	
	public void reloadManagementPane() {
		if(managementPane == null) return;
		loadManagementPane(entities);
	}
	
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
	
	public void showAbout() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/aPropos.fxml"));
	        Scene scene = new Scene(root, 300, 150);
	        Stage stage = new Stage();

	        stage.setTitle("A propos");
	        stage.setScene(scene);
	        stage.setResizable(false);
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.show();
		} catch (Exception ioe) {
			System.out.println("Erreur d'ouverture");
		}
	}
	
	public void quitterAppli() {
		ConfirmationAlert alert = new ConfirmationAlert("Quitter l'application", "Voulez vous quitter l'application ?");
		Optional<ButtonType> confirmation = alert.showAndWait();
		if(confirmation.get() == alert.getValider()) System.exit(0);
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
