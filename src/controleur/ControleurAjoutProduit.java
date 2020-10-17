package controleur;

import java.net.URL;
import java.util.ResourceBundle;

import dao.DAOFactory;
import entities.Categorie;
import entities.Produit;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import vue.application.HomePage;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

public class ControleurAjoutProduit implements Initializable{

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
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		choicebCategorie.getItems().addAll(DAOFactory.getDAOFactory(HomePage.PERSISTANCE).getCategorieDAO().getAll());
		
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
	}
	
	public void creerProduit() {
		lblResultat.setText("");
		String erreurs = controleErreur();
		if(erreurs.equals("")) {
			Produit produit = new Produit(edtNom.getText(), txtDescription.getText(), Double.parseDouble(edtTarif.getText()), "", choicebCategorie.getSelectionModel().getSelectedItem().getIdCategorie());
			DAOFactory.getDAOFactory(HomePage.PERSISTANCE).getProduitDAO().create(produit);
			lblResultat.setText(edtNom.getText() + " (" + choicebCategorie.getSelectionModel().getSelectedItem().getTitre() + ") " + ", " + edtTarif.getText() + " euros");
		} else {
			Alert alert = new Alert(AlertType.ERROR, erreurs, ButtonType.OK);
			alert.showAndWait();
		}
	}
	
	private String controleErreur() {
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
		
		return erreurs;
	}
}
