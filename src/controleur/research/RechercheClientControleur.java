package controleur.research;

import dao.Persistance;
import entities.Client;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RechercheClientControleur implements RechercheControleur<Client>{
	
	private Persistance persistance;
	
	@FXML
	private TextField edtPrenom;
	
	@FXML
	private TextField edtNom;
	
	@Override
	public Client getResearchParameters() {
		String prenomClient = edtPrenom.getText().trim();
		String nomClient = edtNom.getText().trim();
		
		return new Client(nomClient, prenomClient, "", "", "", "", "", "", "");
	}

	@Override
	public void setPersistance(Persistance persistance) {
		this.persistance = persistance;		
	}
}
