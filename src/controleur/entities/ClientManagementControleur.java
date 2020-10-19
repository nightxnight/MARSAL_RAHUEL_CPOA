package controleur.entities;

import dao.DAOFactory;
import entities.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import vue.application.HomePage;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ClientManagementControleur {

	@FXML
	private TextField edtNom;
	@FXML
	private TextField edtPrenom;
	@FXML
	private TextField edtIdent;
	@FXML
	private TextField edtMdp;
	@FXML
	private TextField edtNum;
	@FXML
	private TextField edtVoie;
	@FXML
	private TextField edtCP;
	@FXML
	private TextField edtVille;
	@FXML
	private TextField edtPays;
	@FXML
	private Button boutonCreer;
	@FXML
	private Button boutonModif;
	@FXML
	private Label lblResultat;
	
	private Client client;
	private boolean modif;
	
	
	public void setFormMode(Client client, boolean modif) {
		this.client = client;
		this.modif = modif;
		
		if(this.client == null) boutonModif.setVisible(false);
		else {
			boutonCreer.setVisible(false);
			boutonModif.setVisible(this.modif);
			edtNom.setDisable(!modif);
			edtPrenom.setDisable(!modif);
			edtIdent.setDisable(!modif);
			edtMdp.setDisable(!modif);
			edtNum.setDisable(!modif);
			edtVoie.setDisable(!modif);
			edtPays.setDisable(!modif);
			loadDatas();
		}

	}
	
	private void loadDatas() {
		edtNom.setText(client.getNom());
		edtPrenom.setText(client.getPrenom());
		edtIdent.setText(client.getIdentifiant());
		edtMdp.setText(client.getMotDePasse());
		edtNum.setText(client.getAdrNumero());
		edtVoie.setText(client.getAdrVoie());
		edtCP.setText(client.getAdrCodePostal());
		edtVille.setText(client.getAdrVille());
		edtPays.setText(client.getAdrPays());
	}
	
	public void creerClient() {
		lblResultat.setText("");
		String erreurs = controleErreur();
		if(erreurs.equals("")) {
			Client client = new Client(edtNom.getText(), edtPrenom.getText(), edtIdent.getText(), edtMdp.getText(), edtNum.getText(),edtVoie.getText(), edtVille.getText(), edtCP.getText(), edtPays.getText());
			DAOFactory.getDAOFactory(HomePage.PERSISTANCE).getClientDAO().create(client);
			
		} else {
			Alert alert = new Alert(AlertType.ERROR, erreurs, ButtonType.OK);
			alert.showAndWait();
		}
	}
	
	public void modifierClient() {
		
	}
	
	private String controleErreur() {
		String erreurs = "";
		
		if(!edtNom.getText().equals("")) {
			if(!edtPrenom.getText().matches("[a-z]")) {
				erreurs += "Le nom du client ne peut pas être composé de chiffres";
			}
		}
		else	erreurs += "Le nom du client est a renseigner.\n";
		
		
		if(!edtPrenom.getText().equals("")) {
			if(!edtPrenom.getText().matches("[a-z]")) {
				erreurs += "Le prénom du client ne peut pas être composé de chiffres";
			}
		}
		else erreurs += "Le prénom du client est a renseigner.\n";
		
		
		//TODO Ajout verification format adresse mail
		if(!edtIdent.getText().equals("")) {
			erreurs += "L'identifiant du client est a renseigner.\n";
		}
		
		if(!edtMdp.getText().equals("")) {
			
		}
		else erreurs += "Le mot de passe du client est a renseigner.\n";
		if(!edtNum.getText().equals("")) {
				if(Integer.parseInt(edtNum.getText()) <= 0) {
			}
		}
		else erreurs += "Le numéro d'habitation du client est a renseigner.\n";
		
		if(!edtVoie.getText().equals("")) {
			if(!edtVoie.getText().matches("[a-z]")) {
				erreurs += "La voie d'habitation du client ne peut pas être composée de chiffres";
			}
			erreurs += "La voie d'habitation du client est a renseigner.\n";
		}
		if(!edtCP.getText().equals("")) {
				if(Integer.parseInt(edtCP.getText()) <= 0) {
					erreurs += "Le code postal doit être composé uniquement de chiffres";
				}
			}
		else erreurs += "Le code postal du client est a renseigner.\n";
		
		if(!edtVille.getText().equals("")) {
			if(!edtVille.getText().matches("[a-z]")) {
				erreurs += "La ville du client ne peut pas être composée de chiffres";
			}
		}
		else erreurs += "La ville du client est a renseigner.\n";
		
		
		if(!edtPays.getText().equals("")) {
			if (!edtPays.getText().matches("[a-z]")) {
				erreurs += "Le pays ne peut pas être composé de chiffres";
			}
			erreurs += "Le pays du client est a renseigner.\n";
		}
		
		
		return erreurs;
	}
	
	public void setClient(Client client) {
		this.client = client;
	}
	
	public void setModif(boolean modif) {
		this.modif = modif;
	}
}
