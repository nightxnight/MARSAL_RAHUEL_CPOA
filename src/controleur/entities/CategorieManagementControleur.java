package controleur.entities;

import java.util.Optional;

import controleur.MainControleur;
import dao.DAOFactory;
import entities.Categorie;
import javafx.fxml.FXML;
import vue.application.custom.alert.ConfirmationAlert;
import vue.application.custom.alert.ErrorAlert;
import vue.application.custom.alert.InfoAlert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utils.regex.ImageFileFormat;

public class CategorieManagementControleur implements ImplManagementControleur<Categorie>{

	private MainControleur parent;	
	private Categorie categorie;
	
	@FXML
	private TextField edtNom;
	@FXML
	private TextField edtVisuel;
	@FXML
	private Button boutonCreer;
	@FXML
	private Button boutonModif;
	@FXML
	private Button boutonRetour;
	
	@FXML
	private Label labelNomErreur;
	@FXML
	private Label labelVisuelErreur;
		
	public void setFormMode(Categorie categorie, boolean modif) {		
		
		if(categorie == null)  {
			boutonModif.setVisible(false);
		}
		else {
			boutonCreer.setVisible(false);
			boutonModif.setVisible(modif);
			edtNom.setDisable(!modif);
			edtVisuel.setDisable(!modif);
			fillForm(categorie);
		}
	}
	
	@Override
	public void fillForm(Categorie objet) {
		edtNom.setText(objet.getTitre());
		edtVisuel.setText(objet.getVisuel());
		this.categorie = objet.clone();
	}
	
	@Override
	public void create() {
		if(!checkErrors()) {
			ConfirmationAlert alert = new ConfirmationAlert("Creation d'une categorie", "La categorie va etre creee, etes-vous sur ?", parent);
			Optional<ButtonType> confirmation = alert.showAndWait();
			if(confirmation.get() == alert.getValider()) {
				try {
					Categorie nouvelleCategorie = new Categorie(edtNom.getText().trim(), edtVisuel.getText().trim());
					DAOFactory.getDAOFactory(parent.getPersistance()).getCategorieDAO().create(nouvelleCategorie);
					InfoAlert infoAlert = new InfoAlert("Categorie creee!", "La categorie : \n\"" + nouvelleCategorie.getTitre() + "\"\na ete ajoutee."
							+ "\n\nVous allez retourner sur la liste des categories.", parent);
					infoAlert.showAndWait();
					retourPage();
					parent.getManagementControleur().getDatas().add(nouvelleCategorie);
					parent.getManagementControleur().refresh(-1);
				} catch (IllegalArgumentException iae) {
					ErrorAlert errorAlert = new ErrorAlert("Erreur lors de la creation", iae.getMessage(), parent);
					errorAlert.showAndWait();
					labelNomErreur.setText("existe deja.");
				}
			}
		}
	}
	
	@Override
	public void update() {
		if(!checkErrors()) {
			ConfirmationAlert alert = new ConfirmationAlert("Modification d'une categorie", "La categorie va etre modifiee, etes-vous sur ?", parent);
			Optional<ButtonType> confirmation = alert.showAndWait();
			if(confirmation.get() == alert.getValider()) {
				try {
					Categorie categorieModifiee = new Categorie(categorie.getIdCategorie(), edtNom.getText().trim(), edtVisuel.getText().trim());
					DAOFactory.getDAOFactory(parent.getPersistance()).getCategorieDAO().update(categorieModifiee);
					InfoAlert infoAlert = new InfoAlert("Categorie modifiee!", "La categorie : \n\"" + categorie.getTitre() + "\"\na ete modifiee pour : \n\"" + categorieModifiee.getTitre() + "\""
							+ ".\n\nVous allez retourner sur la liste des categories.", parent);
					infoAlert.showAndWait();
					retourPage();
					parent.getManagementControleur().getDatas().set(parent.getManagementControleur().getDatas().indexOf(categorieModifiee), categorieModifiee);
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
		if(!nom.equals("")) {
			if(nom.matches("^[A-Z]*$")) {
				labelNomErreur.setText("Le nom de la categorie ne peut pas etre compose de chiffres.");
				erreur = true;
			}
		} else {
			labelNomErreur.setText("Le nom de la categorie est a renseigner.");
			erreur = true;
		}
		
		labelVisuelErreur.setText("");
		String visuel = edtVisuel.getText().trim();
		if(visuel.equals("")) {
			labelVisuelErreur.setText("Le visuel de la Categorie est a renseigner.");
			erreur = true;
		}
		else if(!ImageFileFormat.check(visuel)) {
			labelVisuelErreur.setText("Le visuel est un fichier .png, .gif, .jpg ou .bmp.");
			erreur = true;
		}
		
		if(erreur) {
			ErrorAlert errorAlert = new ErrorAlert("Formulaire invalide", "Certains champs sont invalides,\nveuillez les corriger.", parent);
			errorAlert.showAndWait();
		}
		
		return erreur;
	}
	
	public void retourPage() {
		parent.showManagementPane();
	}

	public void setParent(MainControleur parent) {
		this.parent = parent;
	}
}
