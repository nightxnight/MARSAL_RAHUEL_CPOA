package controleur;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.xml.XMLBuilder;
import utils.xml.XMLLoader;
import vue.application.custom.alert.ConfirmationAlert;
import vue.application.management.Entities;

public class MainControleur implements Initializable{
	
	private Scene scene;
	
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
	@FXML
	private Label lblMessage;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		groupPersistanceMenu.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> listeRadioButton, Toggle oldToggle, Toggle newToggle) {
				
				XMLLoader xmlLoader = new XMLLoader("config");
				Document document = xmlLoader.getDocument();
				
				RadioMenuItem selectedRadioButton = (RadioMenuItem) newToggle;
				if(selectedRadioButton.equals(radioMenuListeMemoire)) {
					persistance = Persistance.LISTEMEMOIRE;
					labelPersistance.setText("Mode liste memoire (hors ligne)");
					xmlLoader.updateElement("persistance", "listeMemoire");
				} else if(selectedRadioButton.equals(radioMenuMySQL)) {
					persistance = Persistance.MYSQL;
					labelPersistance.setText("Mode base de donnees MySQL");
					xmlLoader.updateElement("persistance", "mySQL");
				}
				xmlLoader.saveChanges(document, "config");
				reloadManagementPane();
			}
		});
		
		groupTheme.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> listeRadioButton, Toggle oldToggle, Toggle newToggle) {
				
				scene.getStylesheets().clear();
				
				XMLLoader xmlLoader = new XMLLoader("config");
				Document document = xmlLoader.getDocument();
				
				RadioMenuItem selectedRadioButton = (RadioMenuItem) newToggle;
				if(selectedRadioButton.equals(radioThemeClair)) {
					scene.getStylesheets().add(getClass().getResource("/css/themeClair.css").toExternalForm());
					xmlLoader.updateElement("theme", "clair");
				} else if (selectedRadioButton.equals(radioThemeSombre)) {
					scene.getStylesheets().add(getClass().getResource("/css/themeSombre.css").toExternalForm());
					xmlLoader.updateElement("theme", "sombre");
				}
				
				xmlLoader.saveChanges(document, "config");
			}
		});
		
	}
	
	public void loadUserConfig() {
		XMLLoader xmlLoader = new XMLLoader("config");
		Document document = null;
		
		try {
			document = xmlLoader.getDocument();
		} catch(Exception e) {
			System.out.println("Config introuvable");
		} finally {
			if(document == null)  {
				createNewUserConfig();
				return;
			}
		}
		
		Element confTheme = (Element) document.getElementsByTagName("theme").item(0);
		switch(confTheme.getTextContent()) {
		case "clair" : groupTheme.selectToggle(radioThemeClair); break;
		case "sombre" : groupTheme.selectToggle(radioThemeSombre); break;
		default : groupTheme.selectToggle(radioThemeClair);
		}
		
		Element confPersistance = (Element) document.getElementsByTagName("persistance").item(0);
		switch(confPersistance.getTextContent()) {
		case "listeMemoire" : groupPersistanceMenu.selectToggle(radioMenuListeMemoire); break;
		case "mySQL" : groupPersistanceMenu.selectToggle(radioMenuMySQL); break;
		default : groupPersistanceMenu.selectToggle(radioMenuListeMemoire);
		}
	}
	
	private void createNewUserConfig() {
		System.out.println("Creation d'une nouvelle configuration utilisateur");
		XMLBuilder xmlBuilder = new XMLBuilder();
		xmlBuilder.setRoot("config");
		xmlBuilder.createElement("theme", "clair");
		xmlBuilder.createElement("persistance", "listeMemoire");
		xmlBuilder.createXMLFile("config");
		loadUserConfig();
	}
	
	public void showCategories() {
		loadManagementPane(Entities.CATEGORIE);
	}
	
	public void showProduits() {
		loadManagementPane(Entities.PRODUIT);
	}
	
	public void showClients() {
		loadManagementPane(Entities.CLIENT);
	}
	
	public void showCommandes() {
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
	
	public void reloadManagementPane() {
		if(managementPane == null) return;
		loadManagementPane(entities);
	}
	
	public void showManagementPane() {
		if(managementPane == null) return;
		mainPane.setCenter(managementPane);
		labelFilAriane.setText("Gestion > " + entities.getLibelle());
	}
	
	public void showAbout() {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/fxml/aPropos.fxml"));
	        Scene sceneAbout = new Scene(root, 300, 150);
	     
	        sceneAbout.getStylesheets().add(scene.getStylesheets().get(0)); 

	        Stage stage = new Stage();

	        stage.setTitle("A propos");
	        stage.getIcons().add(new Image("file:resources/images/about.png"));
	        stage.setScene(sceneAbout);
	        stage.setResizable(false);
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.centerOnScreen();
	        stage.show();
		} catch (Exception ioe) {
			System.out.println("Erreur d'ouverture");
		}
	}
	
	public void afficheAccueil() {
		managementPane = null;
		mainPane.setCenter(lblMessage);
		labelFilAriane.setText("Accueil");
	}
	
	public void quitterAppli() {
		ConfirmationAlert alert = new ConfirmationAlert("Quitter l'application", "Voulez vous quitter l'application ?", this);
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

	public Scene getScene() {
		return scene;
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;		
	}
}
