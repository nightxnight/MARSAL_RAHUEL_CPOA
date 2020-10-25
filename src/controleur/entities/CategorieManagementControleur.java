package controleur.entities;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controleur.MainControleur;
import dao.DAOFactory;
import entities.Categorie;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import vue.application.custom.alert.ConfirmationAlert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

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
	
	private static final Pattern VALID_FILE_NAME_REGEX = Pattern.compile("([^\\s])+(\\.(?i)(png|gif|jpg|bmp)$)", Pattern.CASE_INSENSITIVE);
	
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
		if(checkErrors()) {
			ConfirmationAlert alert = new ConfirmationAlert("Creation d'une categorie", "La categorie va etre creee, etes-vous sur ?");
			Optional<ButtonType> confirmation = alert.showAndWait();
			if(confirmation.get() == alert.getValider()) {
				Categorie nouvelleCategorie = new Categorie(edtNom.getText().trim(), edtVisuel.getText().trim());
				DAOFactory.getDAOFactory(parent.getPersistance()).getCategorieDAO().create(nouvelleCategorie);
				retourPage();
			}
		}
	}
	
	@Override
	public void update() {
		if(checkErrors()) {
			ConfirmationAlert alert = new ConfirmationAlert("Modification d'une categorie", "La categorie va etre modifiee, etes-vous sur ?");
			Optional<ButtonType> confirmation = alert.showAndWait();
			if(confirmation.get() == alert.getValider()) {
				Categorie categorieModifiee = new Categorie(categorie.getIdCategorie(), edtNom.getText().trim(), edtVisuel.getText().trim());
				DAOFactory.getDAOFactory(parent.getPersistance()).getCategorieDAO().update(categorieModifiee);
				retourPage();
			}
		}
	}
	
	@Override
	public boolean checkErrors() {
		String erreurs = "";
		
		String nom = edtNom.getText().trim();
		if(!nom.equals("")) {
			if(nom.matches("^[A-Z]*$")) erreurs += "Le nom de la categorie ne peut pas etre compose de chiffres \n";
		} else erreurs += "Le nom de la categorie est a renseigner.\n";
		
		String visuel = edtVisuel.getText().trim();
		if(visuel.equals("")) erreurs += "Le visuel de la Categorie est a renseigner.\n";
		else if(!isFileName(visuel)) erreurs += "Le visuel est un fichier .png, .gif, .jpg ou .bmp";
		
		if(!erreurs.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR, erreurs, ButtonType.OK);
			alert.showAndWait();
		}
		
		return erreurs.isEmpty();
	}
	
	public static boolean isFileName(String fileName) {
        Matcher matcher = VALID_FILE_NAME_REGEX.matcher(fileName);
        return matcher.find();
	}
	
	public void retourPage() {
		parent.showManagementPane();
	}

	public void setParent(MainControleur parent) {
		this.parent = parent;
	}
}
