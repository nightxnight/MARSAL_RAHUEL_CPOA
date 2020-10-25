package controleur.entities;

import java.util.Optional;

import controleur.MainControleur;
import dao.DAOFactory;
import entities.Categorie;
import entities.Produit;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import vue.application.custom.alert.ConfirmationAlert;

public class ProduitManagementControleur implements ImplManagementControleur<Produit>{

	@FXML
	private TextField edtNom;
	@FXML
	private TextField edtTarif;
	@FXML
	private TextArea txtDescription;
	@FXML
	private ChoiceBox<Categorie> choicebCategorie;
	@FXML
	private Label lblResultat;
	
	@FXML
	private Button boutonCreer;
	@FXML
	private Button boutonModif;
	@FXML
	private Button boutonRetour;
	
	private Produit produit;
	private MainControleur parent;
	
	public void initializeComponents() {	
		choicebCategorie.getItems().addAll(DAOFactory.getDAOFactory(parent.getPersistance()).getCategorieDAO().getAll());
		choicebCategorie.setConverter(new StringConverter<Categorie>() {
			@Override
			public Categorie fromString(String nom) {
				return choicebCategorie.getItems().stream().filter(categ -> categ.getTitre().equals(nom)).findFirst().orElse(null);
			}

			@Override
			public String toString(Categorie objet) {
				return objet.getTitre();
			}
		});
		choicebCategorie.getSelectionModel().clearSelection();
	}
	
	@Override
	public void setFormMode(Produit produit, boolean modif) {
		
		if(produit == null) boutonModif.setVisible(false); 
		else {
			boutonCreer.setVisible(false);
			boutonModif.setVisible(modif);
			edtNom.setDisable(!modif);
			txtDescription.setDisable(!modif);
			edtTarif.setDisable(!modif);
			choicebCategorie.setDisable(!modif);
			fillForm(produit);
		}

	}
	
	@Override
	public void fillForm(Produit produit) {
		edtNom.setText(produit.getNom());
		txtDescription.setText(produit.getDescription());
		edtTarif.setText(String.valueOf(produit.getTarif()));
		Categorie categorieConcernee = new Categorie(produit.getIdCategorie(), "Introuvable", "");
		if(!choicebCategorie.getItems().stream().anyMatch(Categorie -> Categorie.getIdCategorie() == categorieConcernee.getIdCategorie())) {
			choicebCategorie.getItems().add(categorieConcernee);
		}
		choicebCategorie.getSelectionModel().select(categorieConcernee);
		this.produit = produit;
	}
	
	@Override
	public void create() {
		if(checkErrors()) {
			ConfirmationAlert alert = new ConfirmationAlert("Creation d'un produit", "Le produit va etre cree, etes-vous sur ?");
			Optional<ButtonType> confirmation = alert.showAndWait();
			if(confirmation.get() == alert.getValider()) {
				Produit nouveauProduit = new Produit(edtNom.getText(),
											 		 txtDescription.getText(),
											 		 Double.parseDouble(edtTarif.getText()),
											 		 "",
											 		 choicebCategorie.getSelectionModel().getSelectedItem().getIdCategorie());
				DAOFactory.getDAOFactory(parent.getPersistance()).getProduitDAO().create(nouveauProduit);
				retourPage();
			}
		}
	}
	
	@Override
	public void update() {
		if(checkErrors()) {
			ConfirmationAlert alert = new ConfirmationAlert("Modification d'un produit", "Le produit va etre modifie, etes-vous sur ?");
			Optional<ButtonType> confirmation = alert.showAndWait();
			if(confirmation.get() == alert.getValider()) {
				Produit produitModifie = new Produit(produit.getId(), 
											 edtNom.getText(),
											 txtDescription.getText(),
											 Double.parseDouble(edtTarif.getText()),
											 "",
											 choicebCategorie.getSelectionModel().getSelectedItem().getIdCategorie());
				DAOFactory.getDAOFactory(parent.getPersistance()).getProduitDAO().update(produitModifie);
				retourPage();
			}
		}
	}
	
	@Override
	public boolean checkErrors() {
		String erreurs = "";
		
		if(edtNom.getText().equals("")) {
			erreurs += "Le nom du produit est a renseigner.\n";
		}
		
		if(!edtTarif.getText().equals("")) {
			try {
				if(Double.parseDouble(edtTarif.getText()) <= 0) erreurs += "Le prix du produit est strictement superieur a 0.\n";
			} catch (Exception e) {
				erreurs += "Le prix du produit est un reel.\n";
			}
		} else {
			erreurs += "Le prix du produit est a renseigner.\n";
		}
		
		if(choicebCategorie.getSelectionModel().getSelectedIndex() == -1) {
			erreurs += "La categorie du produit doit etre selectionner.\n";
		}
		
		if(!erreurs.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR, erreurs, ButtonType.OK);
			alert.showAndWait();
		}
		
		return erreurs.isEmpty();
	}
	
	@Override
	public void retourPage() {
		parent.showManagementPane();
	}
	
	public void setParent(MainControleur parent) {
		this.parent = parent;
	}
}
