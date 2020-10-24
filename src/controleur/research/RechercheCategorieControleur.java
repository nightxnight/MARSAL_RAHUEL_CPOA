package controleur.research;

import entities.Categorie;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RechercheCategorieControleur implements RechercheControleur<Categorie>{
	
	@FXML
	private TextField edtNomCateg;
	
	@Override
	public Categorie getResearchParameters() {
		String nomCateg = edtNomCateg.getText().trim();
		return new Categorie(nomCateg, "");
	}
}
