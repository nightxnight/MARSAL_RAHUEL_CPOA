package controleur.research;

import java.time.LocalDate;

import entities.Commande;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class RechercheCommandeControleur implements RechercheControleur<Commande>{
	
	@FXML
	private TextField edtIdCom;
	
	@FXML
	private TextField edtIdClient;
	
	@FXML
	private DatePicker datepickCom;
	
	public Commande getResearchParameters() {
		int idCom = -1;
		try {
			idCom = Integer.parseInt(edtIdCom.getText());
		} catch(Exception e) {
			edtIdCom.clear();
		}
		
		LocalDate dateCom = datepickCom.getValue();
		if(dateCom == null) dateCom = LocalDate.of(1, 1, 1);
		int idClient = -1;
		try {
			idClient = Integer.parseInt(edtIdClient.getText());
		} catch (Exception e) {
			edtIdClient.clear();
		}
		
		
		return new Commande(idCom, dateCom, idClient);
	}

	public TextField getEdtIdClient() {
		return edtIdClient;
	}
}
