package controleur.entities;

import java.util.Optional;

import controleur.MainControleur;
import dao.DAOFactory;
import entities.Categorie;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
			Alert alert = new Alert(AlertType.CONFIRMATION, "La categorie va etre ajoutee.\netes-vous sur ?\n", ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> confirmation = alert.showAndWait();
			if(confirmation.get() == ButtonType.YES) {
				Categorie nouvelleCategorie = new Categorie(edtNom.getText().trim(), edtVisuel.getText().trim());
				DAOFactory.getDAOFactory(parent.getPersistance()).getCategorieDAO().create(nouvelleCategorie);
			}
		}
	}
	
	@Override
	public void update() {
		if(checkErrors()) {
			Alert alert = new Alert(AlertType.CONFIRMATION, "La categorie va etre modifiee.\netes-vous sur ?\n", ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> confirmation = alert.showAndWait();
			if(confirmation.get() == ButtonType.YES) {
				Categorie categorieModifiee = new Categorie(categorie.getIdCategorie(), edtNom.getText().trim(), edtVisuel.getText().trim());
				DAOFactory.getDAOFactory(parent.getPersistance()).getCategorieDAO().update(categorieModifiee);
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
		
		//TODO verification qu'un visuel soit bien de type xxx.ext
		String visuel = edtVisuel.getText().trim();
		if(visuel.equals("")) erreurs += "Le visuel de la Categorie est a renseigner.\n";
		
		if(!erreurs.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR, erreurs, ButtonType.OK);
			alert.showAndWait();
		}
		
		return erreurs.isEmpty();
	}
	
	public void retourPage() {
		parent.showManagementPane();
	}

	public void setParent(MainControleur parent) {
		this.parent = parent;
	}
}