package controleur.entities;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import controleur.MainControleur;
import dao.DAOFactory;
import entities.Client;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import utils.regex.MailAddressFormat;
import utils.regex.TextFormat;
import vue.application.custom.alert.ConfirmationAlert;
import vue.application.custom.alert.ErrorAlert;
import vue.application.custom.alert.InfoAlert;
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
	private Button boutonRetour;
	
	@FXML
	private Label labelNomErreur;
	@FXML
	private Label labelPrenomErreur;
	@FXML
	private Label labelIdentErreur;
	@FXML
	private Label labelMdpErreur;
	@FXML
	private Label labelConfirmMdpErreur;
	
	@FXML
	private Label labelNumErreur;
	@FXML
	private Label labelVoieErreur;
	@FXML
	private Label labelCodePostalErreur;
	@FXML
	private Label labelVilleErreur;
	@FXML
	private Label labelPaysErreur;
	
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
		this.client = objet.clone();
	}

	public void create() {
		if(!checkErrors()) {
			ConfirmationAlert alert = new ConfirmationAlert("Creation d'un client", "Le client va etre cree, etes-vous sur ?", parent);
			Optional<ButtonType> confirmation = alert.showAndWait();
			if(confirmation.get() == alert.getValider()) {
				try {
					Client nouveauClient = new Client(edtNom.getText().trim(),
													  edtPrenom.getText().trim(),
													  edtIdent.getText().trim(),
													  passwMdpClient.getText().trim(),
													  edtNum.getText().trim(),
													  edtVoie.getText().trim(),
													  edtCodePostal.getText().trim(),
													  edtVille.getText().trim(),
													  edtPays.getText().trim());
					DAOFactory.getDAOFactory(parent.getPersistance()).getClientDAO().create(nouveauClient);
					InfoAlert infoAlert = new InfoAlert("Client cree!", "Le client : \n\"" + nouveauClient.getNom() + " " + nouveauClient.getPrenom() + "\"\n a ete ajoute."
							+ "\n\nVous allez retourner sur la liste des clients.", parent);
					infoAlert.showAndWait();
					retourPage();
					parent.getManagementControleur().getDatas().add(nouveauClient);
					parent.getManagementControleur().refresh(-1);
				} catch(IllegalArgumentException iae) {
					ErrorAlert errorAlert = new ErrorAlert("Erreur lors de la creation", iae.getMessage(), parent);
					errorAlert.showAndWait();
					labelIdentErreur.setText("deja utilisee.");
				}
			}
		}
	}
	
	@Override
	public void update() {
		if(!checkErrors()) {
			ConfirmationAlert alert = new ConfirmationAlert("Modification d'un client", "Le client va etre modifie, etes-vous sur ?", parent);
			Optional<ButtonType> confirmation = alert.showAndWait();
			if(confirmation.get() == alert.getValider()) {
				try {
					Client clientModifie = new Client(client.getIdClient(),
													  edtNom.getText().trim(),
													  edtPrenom.getText().trim(),
													  edtIdent.getText().trim(),
													  passwMdpClient.getText().trim(),
													  edtNum.getText().trim(),
													  edtVoie.getText().trim(),
													  edtCodePostal.getText().trim(),
													  edtVille.getText().trim(),
													  edtPays.getText().trim());
					DAOFactory.getDAOFactory(parent.getPersistance()).getClientDAO().update(clientModifie);
					InfoAlert infoAlert = new InfoAlert("Client modifie!", "Le client : \n\"" + client.getNom() + " " + client.getPrenom() + "\"\n a ete modifie pour : \n\"" + clientModifie.getNom() + " " + clientModifie.getPrenom() + "\"."
							+ "\n\nVous allez retourner sur la liste des clients.", parent);
					infoAlert.showAndWait();
					retourPage();
					parent.getManagementControleur().getDatas().set(parent.getManagementControleur().getDatas().indexOf(clientModifie), clientModifie);
					parent.getManagementControleur().refresh();
				} catch(IllegalArgumentException iae) {
					ErrorAlert errorAlert = new ErrorAlert("Erreur lors de la modification", iae.getMessage(), parent);
					errorAlert.showAndWait();
				}
			}
		}
	}
	
	@Override
	public boolean checkErrors() {
		boolean erreur = false;
		
		labelNomErreur.setText("");
		String nom = edtNom.getText().trim();
		if(!nom.equals("")) {
			if(!nom.matches("^[A-Z]*$")) {
				labelNomErreur.setText("pas de chiffres ou d'accents.");
				erreur = true;
			}
		} else {
			labelNomErreur.setText("a saisir.");
			erreur = true;
		}
		
		labelPrenomErreur.setText("");
		String prenom = edtPrenom.getText().trim();
		if(!prenom.equals("")) {
			if(!TextFormat.checkWord(prenom)) {
				labelPrenomErreur.setText("pas de chiffres.");
				erreur = true;
			}
		} else {
			labelPrenomErreur.setText("a saisir.");
			erreur = true;
		}
		
		labelIdentErreur.setText("");
		String identifiant = edtIdent.getText().trim();
		if(identifiant.equals("")) {
			labelIdentErreur.setText("a saisir");
			erreur = true;
		}
		else if(!MailAddressFormat.check(identifiant)) {
			labelIdentErreur.setText("mauvais format.");
			erreur = true;
		}

		labelMdpErreur.setText("");
		labelConfirmMdpErreur.setText("");
		if(passwMdpClient.getText().trim().equals("")) {
			labelMdpErreur.setText("a saisir");
			erreur = true;
		} else {
			if(passwConfirmationMdp.getText().trim().equals("")) {
				labelConfirmMdpErreur.setText("a confirmer.");
				erreur = true;
			}
			else if(!passwMdpClient.getText().trim().equals(passwConfirmationMdp.getText().trim())) {
				labelConfirmMdpErreur.setText("incorrect.");
				erreur = true;
			}
		}
		
		labelNumErreur.setText("");
		String num = edtNum.getText().trim();
		if(!num.equals("")) {
			try {
				if(Integer.parseInt(num) <= 0) {
					labelNumErreur.setText("nombre positif.");
					erreur = true;
				}
			} catch(Exception e) {
				labelNumErreur.setText("nombre entier.");
				erreur = true;
			}
		} else {
			labelNumErreur.setText("a saisir.");
			erreur = true;
		}
		
		labelVoieErreur.setText("");
		String voie = edtVoie.getText().trim();
		if(!voie.equals("")) {
			if(!TextFormat.checkSentence(voie)) {
				labelVoieErreur.setText("pas de chiffre.");
				erreur = true;
			}
		} else {
			labelVoieErreur.setText("a saisir.");
			erreur = true;
		}
		
		labelCodePostalErreur.setText("");
		String codePostal = edtCodePostal.getText().trim(); 
		if(!codePostal.equals("")) {
			try {
				if(Integer.parseInt(codePostal) <= 0) {
					labelCodePostalErreur.setText("uniquement positif.");
					erreur = true;
				}
			} catch(Exception e) {
				labelCodePostalErreur.setText("uniquement des chiffres.");
				erreur = true;
			}
		} else {
			labelCodePostalErreur.setText("a saisir");
			erreur = true;
		}
		
		labelVilleErreur.setText("");
		String ville = edtVille.getText().trim();
		if(!ville.equals("")) {
			if(!TextFormat.checkSentence(ville)) {
				labelVilleErreur.setText("pas de chiffres.");
				erreur = true;
			}
		} else {
			labelVilleErreur.setText("a saisir");
			erreur = true;
		}
		
		labelPaysErreur.setText("");
		String pays = edtPays.getText().trim();
		if(!pays.equals("")) {
			if (!TextFormat.checkWord(pays)) {
				labelPaysErreur.setText("pas de chiffres.");
				erreur = true;
			}
		} else {
			labelPaysErreur.setText("a saisir");
			erreur = true;
		}
		
		if(erreur) {
			ErrorAlert errorAlert = new ErrorAlert("Formulaire invalide", "Certains champs sont invalides,\nveuillez les corriger.", parent);
			errorAlert.showAndWait();
		}
		
		return erreur;
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
