package controleur.entities;

import java.util.Optional;

import controleur.MainControleur;
import dao.DAOFactory;
import entities.Categorie;
import entities.Produit;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import utils.regex.ImageFileFormat;
import vue.application.custom.alert.ConfirmationAlert;
import vue.application.custom.alert.ErrorAlert;

public class ProduitManagementControleur implements ImplManagementControleur<Produit>{

	@FXML
	private TextField edtNom;
	@FXML
	private TextArea txtDescription;
	@FXML
	private TextField edtTarif;
	@FXML
	private TextField edtVisuel;
	@FXML
	private ChoiceBox<Categorie> choicebCategorie;
	
	@FXML
	private Button boutonCreer;
	@FXML
	private Button boutonModif;
	@FXML
	private Button boutonRetour;
	
	@FXML
	private Label labelNomErreur;
	@FXML
	private Label labelPrixErreur;
	@FXML
	private Label labelVisuelErreur;
	@FXML
	private Label labelCategErreur;
	
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
			edtVisuel.setDisable(!modif);
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
		if(!checkErrors()) {
			ConfirmationAlert alert = new ConfirmationAlert("Creation d'un produit", "Le produit va etre cree, etes-vous sur ?", parent);
			Optional<ButtonType> confirmation = alert.showAndWait();
			if(confirmation.get() == alert.getValider()) {
				try {
					Produit nouveauProduit = new Produit(edtNom.getText().trim(),
												 		 txtDescription.getText().trim(),
												 		 Double.parseDouble(edtTarif.getText().trim()),
												 		 edtVisuel.getText().trim(),
												 		 choicebCategorie.getSelectionModel().getSelectedItem().getIdCategorie());
					DAOFactory.getDAOFactory(parent.getPersistance()).getProduitDAO().create(nouveauProduit);
					retourPage();
					//XXX Si on a effectuer une recherche le produit s'affichera malgré les contraintes, mais du coup comment on sait que le prod a ete creer ?
					parent.getManagementControleur().getDatas().add(nouveauProduit);
					parent.getManagementControleur().refresh(-1);
				} catch(IllegalArgumentException iae) {
					ErrorAlert errorAlert = new ErrorAlert("Erreur lors de la creation", iae.getMessage(), parent);
					errorAlert.showAndWait();
				}
			}
		}
	}
	
	@Override
	public void update() {
		if(!checkErrors()) {
			ConfirmationAlert alert = new ConfirmationAlert("Modification d'un produit", "Le produit va etre modifie, etes-vous sur ?", parent);
			Optional<ButtonType> confirmation = alert.showAndWait();
			if(confirmation.get() == alert.getValider()) {
				try {
					Produit produitModifie = new Produit(produit.getId(), 
												 edtNom.getText().trim(),
												 txtDescription.getText().trim(),
												 Double.parseDouble(edtTarif.getText().trim()),
												 edtVisuel.getText().trim(),
												 choicebCategorie.getSelectionModel().getSelectedItem().getIdCategorie());
					DAOFactory.getDAOFactory(parent.getPersistance()).getProduitDAO().update(produitModifie);
					retourPage();
					parent.getManagementControleur().getDatas().set(parent.getManagementControleur().getDatas().indexOf(produitModifie), produitModifie);
					parent.getManagementControleur().refresh();
				} catch(IllegalArgumentException iae) {
					ErrorAlert errorAlert = new ErrorAlert("Erreur lors de la modification", iae.getMessage(), parent);
					errorAlert.showAndWait();
				}
			}
		}
	}
	
	@Override
	public boolean checkErrors() {
		boolean erreur = false;
		
		labelNomErreur.setText("");
		String nom = edtNom.getText().trim();
		if(nom.equals("")) {
			labelNomErreur.setText("a saisir.");
			erreur = true;
		}
		
		labelPrixErreur.setText("");
		String tarif = edtTarif.getText().trim();
		if(!tarif.equals("")) {
			try {
				if(Double.parseDouble(tarif) <= 0) {
					labelPrixErreur.setText("un prix est positif.");
					erreur = true;
				}
			} catch (Exception e) {
				labelPrixErreur.setText("un prix est un reels.");
				erreur = true;
			}
		} else {
			labelPrixErreur.setText("a saisir.");
			erreur = true;
		}
		
		labelVisuelErreur.setText("");
		String visuel = edtVisuel.getText().trim();
		if(!visuel.equals("")) {
			if(!ImageFileFormat.check(visuel)) {
				labelVisuelErreur.setText("Le visuel est un fichier .png, .gif, .jpg ou .bmp.");
				erreur = true;
			}
		} else {
			labelVisuelErreur.setText("a saisir.");
			erreur = true;
		}
		
		labelCategErreur.setText("");
		if(choicebCategorie.getSelectionModel().getSelectedIndex() == -1) {
			labelCategErreur.setText("a selectionner.");
			erreur = true;
		}
		
		if(erreur) {
			ErrorAlert errorAlert = new ErrorAlert("Formulaire invalide", "Certains champs sont invalides,\nveuillez les corriger.", parent);
			errorAlert.showAndWait();
		}
		
		return erreur;
	}
	
	@Override
	public void retourPage() {
		parent.showManagementPane();
	}
	
	public void setParent(MainControleur parent) {
		this.parent = parent;
	}
}
