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
		int idCom = Integer.valueOf(edtIdCom.getText());
		int idClient = Integer.valueOf(edtIdClient.getText());
		LocalDate dateCom = datepickCom.getValue();
		
		return new Commande(idCom, dateCom, idClient);
	}
}
