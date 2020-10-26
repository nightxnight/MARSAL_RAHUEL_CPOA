package controleur.entities;

import java.util.Optional;

import controleur.MainControleur;
import dao.DAOFactory;
import entities.Categorie;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import vue.application.custom.alert.ConfirmationAlert;
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
		this.categorie = objet;
	}
	
	@Override
	public void create() {
		if(!checkErrors()) {
			ConfirmationAlert alert = new ConfirmationAlert("Creation d'une categorie", "La categorie va etre creee, etes-vous sur ?");
			Optional<ButtonType> confirmation = alert.showAndWait();
			if(confirmation.get() == alert.getValider()) {
				Categorie nouvelleCategorie = new Categorie(edtNom.getText().trim(), edtVisuel.getText().trim());
				DAOFactory.getDAOFactory(parent.getPersistance()).getCategorieDAO().create(nouvelleCategorie);
				retourPage();
				parent.getManagementControleur().getDatas().add(nouvelleCategorie);
				parent.getManagementControleur().refresh(-1);
			}
		}
	}
	
	@Override
	public void update() {
		if(!checkErrors()) {
			ConfirmationAlert alert = new ConfirmationAlert("Modification d'une categorie", "La categorie va etre modifiee, etes-vous sur ?");
			Optional<ButtonType> confirmation = alert.showAndWait();
			if(confirmation.get() == alert.getValider()) {
				Categorie categorieModifiee = new Categorie(categorie.getIdCategorie(), edtNom.getText().trim(), edtVisuel.getText().trim());
				DAOFactory.getDAOFactory(parent.getPersistance()).getCategorieDAO().update(categorieModifiee);
				retourPage();
				parent.getManagementControleur().getDatas().set(parent.getManagementControleur().getDatas().indexOf(categorieModifiee), categorieModifiee);
				parent.getManagementControleur().refresh();
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
			Alert alert = new Alert(AlertType.ERROR, "formulaire invalide\n.", ButtonType.OK);
			alert.showAndWait();
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
