package controleur.research;

import dao.Persistance;
import entities.Categorie;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RechercheCategorieControleur implements RechercheControleur<Categorie>{

	private Persistance persistance;
	
	@FXML
	private TextField edtNomCateg;
	
	@Override
	public Categorie getResearchParameters() {
		String nomCateg = edtNomCateg.getText().trim();
		return new Categorie(nomCateg, "");
	}

	@Override
	public void setPersistance(Persistance persistance) {
		this.persistance = persistance;		
	}

}
