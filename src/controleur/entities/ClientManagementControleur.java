package controleur.entities;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import controleur.MainControleur;
import dao.DAOFactory;
import entities.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ClientManagementControleur implements ImplManagementControleur<Client>, Initializable{

	private MainControleur parent;
	private Client client;
	
	@FXML
	private TextField edtNom;
	@FXML
	private TextField edtPrenom;
	@FXML
	private TextField edtIdent;
	@FXML
	private PasswordField passwMdpClient;
	@FXML
	private PasswordField passwConfirmationMdp;
	@FXML
	private TextField edtNum;
	@FXML
	private TextField edtVoie;
	@FXML
	private TextField edtCodePostal;
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
	
	public void setFormMode(Client client, boolean modif) {
		
		if(client == null) boutonModif.setVisible(false);
		else {
			boutonCreer.setVisible(false);
			boutonModif.setVisible(modif);
			edtNom.setDisable(!modif);
			edtPrenom.setDisable(!modif);
			edtIdent.setDisable(!modif);
			passwMdpClient.setDisable(!modif);
			passwConfirmationMdp.setDisable(!modif);
			edtNum.setDisable(!modif);
			edtVoie.setDisable(!modif);
			edtCodePostal.setDisable(!modif);
			edtVille.setDisable(!modif);
			edtPays.setDisable(!modif);
			fillForm(client);
		}
	}
	
	@Override
	public void fillForm(Client objet) {
		edtNom.setText(objet.getNom());
		edtPrenom.setText(objet.getPrenom());
		edtIdent.setText(objet.getIdentifiant());
		passwMdpClient.setText(objet.getMotDePasse());
		passwConfirmationMdp.setText(objet.getMotDePasse());
		edtNum.setText(objet.getAdrNumero());
		edtVoie.setText(objet.getAdrVoie());
		edtCodePostal.setText(objet.getAdrCodePostal());
		edtVille.setText(objet.getAdrVille());
		edtPays.setText(objet.getAdrPays());
		this.client = objet;
	}

	public void create() {
		if(checkErrors()) {
			Alert alert = new Alert(AlertType.CONFIRMATION, "Le client va etre ajoute.\nEtes-vous sur ?\n", ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> confirmation = alert.showAndWait();
			if(confirmation.get() == ButtonType.YES) {
				Client nouveauClient = new Client(edtNom.getText().trim(),
												  edtPrenom.getText().trim(),
												  edtIdent.getText().trim(),
												  passwMdpClient.getText().trim(),
												  edtNum.getText().trim(),
												  edtVoie.getText().trim(),
												  edtVille.getText().trim(),
												  edtCodePostal.getText().trim(),
												  edtPays.getText().trim());
				DAOFactory.getDAOFactory(parent.getPersistance()).getClientDAO().create(nouveauClient);
			}
		}
	}
	
	@Override
	public void update() {
		if(checkErrors()) {
			Alert alert = new Alert(AlertType.CONFIRMATION, "Le client va etre ajoute.\nEtes-vous sur ?\n", ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> confirmation = alert.showAndWait();
			if(confirmation.get() == ButtonType.YES) {
				Client clientModifie = new Client(client.getIdClient(),
												  edtNom.getText().trim(),
												  edtPrenom.getText().trim(),
												  edtIdent.getText().trim(),
												  passwMdpClient.getText().trim(),
												  edtNum.getText().trim(),
												  edtVoie.getText().trim(),
												  edtVille.getText().trim(),
												  edtCodePostal.getText().trim(),
												  edtPays.getText().trim());
				DAOFactory.getDAOFactory(parent.getPersistance()).getClientDAO().update(clientModifie);
			}
		}
	}
	
	@Override
	public boolean checkErrors() {
		String erreurs = "";
		
		String nom = edtNom.getText().trim();
		if(!nom.equals("")) {
			if(!nom.matches("^[A-Z]*$")) erreurs += "Le nom du client ne peut pas être composé de chiffres\n";
		} else erreurs += "Le nom du client est a renseigner.\n";
		
		String prenom = edtPrenom.getText().trim();
		if(!prenom.equals("")) {
			if(!prenom.matches("^[a-zA-Z]*$")) erreurs += "Le prénom du client ne peut pas être composé de chiffres\n";
		} else erreurs += "Le prénom du client est a renseigner.\n";
		
		//TODO Ajouter verification de l'adresse au bon format : xxx@xxx.xx
		String identifiant = edtIdent.getText().trim();
		if(identifiant.equals("")) erreurs += "L'identifiant du client est a renseigner.\n";
		else {
			if(identifiant.matches("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$")) erreurs += "Mauvais format de l'addresse mail.\n";
		}
		
		if(passwMdpClient.getText().trim().equals("")) erreurs += "Le mot de passe du client est a renseigner.\n";
		else {
			if(passwConfirmationMdp.getText().trim().equals("")) erreurs += "Le mot de passe est a confirmer.\n";
			else if(!passwMdpClient.getText().trim().equals(passwConfirmationMdp.getText().trim())) erreurs += "La confirmation doit correspondre au mot de passe.\n";
		}
		
		String num = edtNum.getText().trim();
		if(!num.equals("")) {
			try {
				if(Integer.parseInt(num) <= 0) erreurs += "Un numero d'habitation est positif.\n";
			} catch(Exception e) {
				erreurs += "Un numero est un entier.\n";
			}
		} else erreurs += "Le numéro d'habitation du client est a renseigner.\n";
		
		String voie = edtVoie.getText().trim();
		if(!voie.equals("")) {
			if(!voie.matches("^[a-zA-Z][a-zA-Z ]*$")) erreurs += "La voie d'habitation du client ne peut pas être composée de chiffres";
		} else erreurs += "La voie d'habitation du client est a renseigner.\n";
		
		String codePostal = edtCodePostal.getText().trim(); 
		if(!codePostal.equals("")) {
				if(Integer.parseInt(codePostal) <= 0) erreurs += "Le code postal doit être composé uniquement de chiffres";
		} else erreurs += "Le code postal du client est a renseigner.\n";
		
		String ville = edtVille.getText().trim();
		if(!ville.equals("")) {
			if(!ville.matches("^[a-zA-Z]*$")) erreurs += "La ville du client ne peut pas être composée de chiffres";
		} else erreurs += "La ville du client est a renseigner.\n";
		
		String pays = edtPays.getText().trim();
		if(!pays.equals("")) {
			if (!pays.matches("^[a-zA-Z]*$")) erreurs += "Le pays ne peut pas être composé de chiffres";
		} else erreurs += "Le pays du client est a renseigner.\n";
		
		if(!erreurs.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR, erreurs, ButtonType.OK);
			alert.showAndWait();
		}
		
		return erreurs.isEmpty();
	}
	
	@Override
	public void retourPage() {
		parent.showManagementPane();	
	}
	
	public void setParent(MainControleur parent) {
		this.parent = parent;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		edtNom.textProperty().addListener((ov, oldValue, newValue) -> {
		     edtNom.setText(newValue.toUpperCase());
		});
	}
	
}
